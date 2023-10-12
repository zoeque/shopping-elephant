package zoeque.elephant.domain.factory;

import io.vavr.control.Try;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import zoeque.elephant.domain.entity.ShoppingTask;
import zoeque.elephant.domain.model.NotificationStatusModel;
import zoeque.elephant.domain.valueobject.ItemToBuy;
import zoeque.elephant.domain.valueobject.TaskExecutionDate;
import zoeque.elephant.usecase.dto.ShoppingTaskDto;

/**
 * The factory class for create {@link ShoppingTask}.
 */
@Slf4j
@Component
public class ShoppingTaskFactory {

  /**
   * Create new {@link ShoppingTask} entity instance from {@link ShoppingTaskDto}.
   * Note that {@link TaskExecutionDate} must be given with yyyy/MM/dd HH:mm:ss format.
   * All fields in {@link ShoppingTaskDto} must be given, not allowed null in any fields.
   *
   * @param dto The full specified Dto instance of {@link ShoppingTaskDto}.
   * @return Result {@link Try} with the instance of entity, {@link ShoppingTask}.
   */
  public Try<ShoppingTask> create(ShoppingTaskDto dto) {
    try {
      log.info("Create new shopping task : {}", dto);
      return Try.success(new ShoppingTask(
              new ItemToBuy(dto.itemName()),
              new TaskExecutionDate(
                      convertStringDateToLocalDateTime(dto.executionDate()).get()
              ),
              NotificationStatusModel.PLANNED
      ));
    } catch (Exception e) {
      return Try.failure(e);
    }
  }

  private Try<LocalDateTime> convertStringDateToLocalDateTime(String dateTime) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      Date formatDate = sdf.parse(dateTime);
      return Try.success(LocalDateTime.ofInstant(formatDate.toInstant(), ZoneId.systemDefault()));
    } catch (Exception e) {
      log.warn("Cannot convert the date to the LocalDateTime type : {}", dateTime);
      return Try.failure(e);
    }
  }
}
