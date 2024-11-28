package zoeque.elephant.usecase.service;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import zoeque.elephant.domain.entity.ShoppingTask;
import zoeque.elephant.domain.repository.IShoppingTaskRepository;
import zoeque.elephant.domain.specification.ShoppingTaskSpecification;

/**
 * The class to delete shopping task that date is expired.
 */
@Slf4j
@Service
public class ShoppingTaskDeleteService {

  IShoppingTaskRepository repository;

  ShoppingTaskSpecification specification;

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
    Optional<ShoppingTask> itemToDelete = repository.findAll(specification);
    if (itemToDelete.isPresent()) {
      itemToDelete.stream().forEach(item -> {
        repository.delete(item);
      });
    }
  }
}
