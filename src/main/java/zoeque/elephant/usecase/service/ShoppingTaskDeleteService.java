package zoeque.elephant.usecase.service;

import io.vavr.control.Try;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import zoeque.elephant.domain.entity.ShoppingTask;
import zoeque.elephant.domain.repository.IShoppingTaskRepository;
import zoeque.elephant.domain.specification.ShoppingTaskSpecification;
import zoeque.elephant.usecase.dto.ShoppingTaskDto;

/**
 * The class to delete shopping task that date is expired.
 */
@Slf4j
@Service
public class ShoppingTaskDeleteService {

  IShoppingTaskRepository repository;

  ShoppingTaskSpecification<ShoppingTask> specification;

  public ShoppingTaskDeleteService(IShoppingTaskRepository repository,
                                   ShoppingTaskSpecification specification) {
    this.repository = repository;
    this.specification = specification;
  }

  /**
   * Delete process scheduled every 1:00 AM.
   */
  @Scheduled(cron = "0 0 1 * * ?")
  public void delete() {
    List<ShoppingTask> itemToDelete = repository.findAll(specification.itemToDelete());
    if (!itemToDelete.isEmpty()) {
      itemToDelete.stream().forEach(item -> {
        repository.delete(item);
      });
    }
  }

  /**
   * Delete the shopping task with given {@link ShoppingTaskDto}.
   *
   * @param dto The {@link ShoppingTaskDto} instance to delete.
   * @return {@link Try} with the deleted {@link ShoppingTaskDto} instance.
   */
  public Try<ShoppingTaskDto> delete(ShoppingTaskDto dto) {
    // Find item to delete
    var itemToDelete = repository.findShoppingTaskById(dto.identifier());
    if (itemToDelete.isEmpty()) {
      log.warn("Cannot find the shopping task with id : {}", dto.identifier());
      return Try.failure(new IllegalStateException("Cannot find the shopping task with id : " + dto.identifier()));
    }
    repository.delete(itemToDelete.get());

    return Try.success(dto);
  }
}
