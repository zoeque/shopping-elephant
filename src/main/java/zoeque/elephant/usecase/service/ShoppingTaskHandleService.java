package zoeque.elephant.usecase.service;

import io.vavr.control.Try;
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
}
