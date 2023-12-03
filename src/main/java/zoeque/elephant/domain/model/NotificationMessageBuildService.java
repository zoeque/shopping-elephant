package zoeque.elephant.domain.model;

import io.vavr.control.Try;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zoeque.elephant.configuration.ShoppingElephantConstants;
import zoeque.elephant.domain.entity.ShoppingTask;
import zoeque.elephant.usecase.dto.ShoppingTaskDto;

/**
 * The message with the notification message
 * and the target items.
 */
@Slf4j
@Service
public class NotificationMessageBuildService {

  /**
   * The message builder with the template message.
   *
   * @return String type message with the result {@link Try}.
   */
  public Try<String> buildMessage(List<? extends ShoppingTaskDto> tasks) {
    StringBuilder sb = new StringBuilder();
    sb.append(ShoppingElephantConstants.MESSAGE_TEMPLATE);
    tasks.stream().forEach(task -> {
      sb.append(task.itemName());
      sb.append("\r\n");
    });
    return Try.success(sb.toString());
  }
}
