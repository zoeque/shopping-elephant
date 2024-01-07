package zoeque.elephant.usecase.service;

import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import zoeque.elephant.domain.entity.ShoppingTask;
import zoeque.elephant.domain.model.MessageModel;
import zoeque.elephant.domain.repository.IShoppingTaskRepository;
import zoeque.elephant.domain.specification.ShoppingTaskSpecification;
import zoeque.elephant.usecase.dto.ShoppingTaskDto;
import zoeque.mailer.application.event.MailRequestEvent;

@Slf4j
public abstract class AbstractShoppingTaskNotifierService
        implements IShoppingTaskHandleService {
  @Value("${zoeque.elephant.mail.address.to}")
  String mailTo;
  @Value("${zoeque.elephant.mail.address.from}")
  String mailFrom;
  IShoppingTaskRepository repository;
  ShoppingTaskSpecification specification;
  ApplicationEventPublisher publisher;

  public AbstractShoppingTaskNotifierService(IShoppingTaskRepository repository,
                                             ShoppingTaskSpecification specification,
                                             ApplicationEventPublisher publisher) {
    this.repository = repository;
    this.specification = specification;
    this.publisher = publisher;
  }

  @Override
  public Try<List<ShoppingTask>> notifyToUser() {
    try {
      List<ShoppingTask> itemsToNotify
              = repository.findAll(specification.itemToNotify());
      if (!itemsToNotify.isEmpty()) {
        List<ShoppingTaskDto> dtoList = new ArrayList<>();
        itemsToNotify.stream().forEach(item -> {
          dtoList.add(convertEntityToDto(item).get());
        });
        String message
                = MessageModel.messageBuilder.apply(dtoList);
        publisher.publishEvent(new MailRequestEvent(
                MessageModel.SUBJECT,
                message,
                mailFrom,
                mailTo
        ));
      }
      return Try.success(itemsToNotify);
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
