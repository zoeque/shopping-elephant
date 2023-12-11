package zoeque.elephant.domain.model;

import java.util.List;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zoeque.elephant.configuration.ShoppingElephantConstants;
import zoeque.elephant.usecase.dto.ShoppingTaskDto;

/**
 * The message with the notification message
 * and the target items.
 */
@Slf4j
@Service
public class MessageModel {

  /**
   * The message builder based on given list of task
   */
  public static final Function<List<? extends ShoppingTaskDto>, String>
          messageBuilder = list -> {
    StringBuilder sb = new StringBuilder();
    sb.append(ShoppingElephantConstants.MESSAGE_TEMPLATE);
    list.stream().forEach(task -> {
      sb.append(task.itemName());
      sb.append("\r\n");
    });
    return sb.toString();
  };

  public static final String SUBJECT = "今日の買い物リスト";
}
