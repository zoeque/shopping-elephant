package zoeque.elephant.usecase.service;

import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import zoeque.elephant.domain.entity.ShoppingTask;
import zoeque.elephant.domain.model.NotificationMessageBuildService;
import zoeque.elephant.domain.repository.IShoppingTaskRepository;
import zoeque.elephant.domain.specification.ShoppingTaskSpecification;
import zoeque.elephant.usecase.dto.ShoppingTaskDto;

@Slf4j
public abstract class AbstractShoppingTaskNotifierService
        implements IShoppingTaskHandleService {
  IShoppingTaskRepository repository;
  ShoppingTaskSpecification specification;
  NotificationMessageBuildService builder;
  ApplicationEventPublisher publisher;

  public AbstractShoppingTaskNotifierService(IShoppingTaskRepository repository,
                                             ShoppingTaskSpecification specification,
                                             NotificationMessageBuildService builder,
                                             ApplicationEventPublisher publisher) {
    this.repository = repository;
    this.specification = specification;
    this.builder = builder;
    this.publisher = publisher;
  }

  @Override
  public void notifyToUser() {
    try {
      List<ShoppingTask> itemsToNotify
              = repository.findAll(specification.itemToNotify());
      if (!itemsToNotify.isEmpty()) {
        List<ShoppingTaskDto> dtoList = new ArrayList<>();
        itemsToNotify.stream().forEach(item -> {
          dtoList.add(convertEntityToDto(item).get());
        });
        Try<String> message = builder.buildMessage(dtoList);
      }
    } catch (Exception e) {
      log.warn("Cannot notify to the user : {}", e.toString());
      throw new IllegalStateException(e);
    }
  }

  private Try<ShoppingTaskDto> convertEntityToDto(ShoppingTask taskEntity) {
    return Try.success(
            new ShoppingTaskDto(taskEntity.getItemToBuy().getName(),
                    taskEntity.getExecutionDate().toString())
    );
  }
}
