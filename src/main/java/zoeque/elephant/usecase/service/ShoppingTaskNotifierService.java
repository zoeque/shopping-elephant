package zoeque.elephant.usecase.service;

import io.vavr.control.Try;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import zoeque.elephant.configuration.SettingConfiguration;
import zoeque.elephant.domain.entity.ShoppingTask;
import zoeque.elephant.domain.repository.IShoppingTaskRepository;
import zoeque.elephant.domain.specification.ShoppingTaskSpecification;

/**
 * The service class to report the task and update its status.
 */
@Slf4j
@Service
public class ShoppingTaskNotifierService
        extends AbstractShoppingTaskNotifierService {
  IShoppingTaskRepository repository;

  public ShoppingTaskNotifierService(IShoppingTaskRepository repository,
                                     ShoppingTaskSpecification specification,
                                     ApplicationEventPublisher publisher) {
    super(repository, specification, publisher);
    this.repository = repository;
  }

  @Scheduled(cron = SettingConfiguration.CRON_FOR_NOTIFICATION)
  @Override
  public Try<List<ShoppingTask>> notifyToUser() {
    try {
      List<ShoppingTask> taskList = super.notifyToUser().get();
      // update the status with reported state.
      taskList.stream()
              .forEach(task -> {
                task.updateStatus();
                repository.save(task);
              });
      return Try.success(taskList);
    } catch (Exception e) {
      return Try.failure(e);
    }
  }
}
