package zoeque.elephant.domain.model;

import io.vavr.control.Try;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import zoeque.elephant.configuration.ShoppingElephantConstants;
import zoeque.elephant.usecase.dto.ShoppingTaskDto;

/**
 * The message with the notification message
 * and the target items.
 */
@Slf4j
public class NotificationMessageModel {
  List<ShoppingTaskDto> tasks;

  public NotificationMessageModel(List<ShoppingTaskDto> taskList) {
    setTasks(taskList);
  }

  private void setTasks(List<ShoppingTaskDto> tasks) {
    if (tasks == null) {
      log.warn("Task list must not be null!");
      throw new IllegalArgumentException();
    } else if (tasks.isEmpty()) {
      log.warn("Task list must be set more than one task!");
      throw new IllegalStateException("Task must contains more than one task");
    }
    this.tasks = tasks;
  }

  /**
   * The message builder with the template message.
   *
   * @return String type message with the result {@link Try}.
   */
  public Try<String> buildMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append(ShoppingElephantConstants.MESSAGE_TEMPLATE);
    tasks.stream().forEach(task -> {
      sb.append(task.itemName());
      sb.append("\r\n");
    });
    return Try.success(sb.toString());
  }
}
