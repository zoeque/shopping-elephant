package zoeque.elephant.usecase.service;

import io.vavr.control.Try;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zoeque.elephant.domain.entity.ShoppingTask;
import zoeque.elephant.domain.factory.ShoppingTaskFactory;
import zoeque.elephant.domain.repository.IShoppingTaskRepository;
import zoeque.elephant.usecase.dto.ShoppingTaskDto;

/**
 * The implementation to perform usecase.
 * This service class handle, create and delete, the {@link ShoppingTask} in DB.
 * The base of the entity is given from an adapter class
 * by {@link ShoppingTaskDto} instance.
 */
@Slf4j
@Service
public class ShoppingTaskHandleService {
  ShoppingTaskFactory factory;
  IShoppingTaskRepository repository;

  public ShoppingTaskHandleService(ShoppingTaskFactory factory,
                                   IShoppingTaskRepository repository) {
    this.factory = factory;
    this.repository = repository;
  }

  /**
   * The creation process for {@link ShoppingTask}.
   *
   * @param dto The full specified {@link ShoppingTaskDto}.
   * @return Result of saving {@link ShoppingTask} instance in DB with {@link Try}.
   */
  public Try<ShoppingTaskDto> create(ShoppingTaskDto dto) {
    try {
      Try<ShoppingTask> shoppingTaskTry = factory.create(dto);
      if (shoppingTaskTry.isFailure()) {
        // failed to create new shopping task instance.
        return Try.failure(shoppingTaskTry.getCause());
      }
      repository.save(shoppingTaskTry.get());
      return Try.success(dto);
    } catch (Exception e) {
      log.warn("Cannot save the shopping task instance in DB : {}", e.getCause());
      return Try.failure(e);
    }
  }

  /**
   * Find all tasks in DB.
   * @return List of {@link ShoppingTaskDto} with {@link Try}.
   */
  public Try<List<ShoppingTaskDto>> findAll() {
    try {
      List<ShoppingTask> allEntity = repository.findAll();
      List<ShoppingTaskDto> dtoList = new ArrayList<>();
      allEntity.stream().forEach(entity -> {
        ShoppingTaskDto dto = new ShoppingTaskDto(
                entity.getItemToBuy().getName(),
                convertLocalDateTimeToString(entity.getExecutionDate().getDateTime())
        );
        dtoList.add(dto);
      });
      return Try.success(dtoList);
    } catch (Exception e) {
      log.warn("Cannot find all tasks in DB");
      return Try.failure(e);
    }
  }

  /**
   * Convert LocalDateTime to String.
   * @param localDateTime LocalDateTime instance.
   * @return String formatted date.
   */
  private String convertLocalDateTimeToString(LocalDateTime localDateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    return localDateTime.format(formatter);
  }
}
