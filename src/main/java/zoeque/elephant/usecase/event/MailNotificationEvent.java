package zoeque.elephant.usecase.event;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import zoeque.elephant.usecase.dto.ShoppingTaskDto;

/**
 * The event to send the information of e-mail
 */
@Getter
@AllArgsConstructor
public class MailNotificationEvent {
  List<ShoppingTaskDto> taskList;
}
