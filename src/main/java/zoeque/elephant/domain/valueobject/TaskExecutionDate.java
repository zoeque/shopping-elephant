package zoeque.elephant.domain.valueobject;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The date definition that is to notify.
 */
@Embeddable
@Getter
@NoArgsConstructor
public class TaskExecutionDate {
  LocalDateTime dateTime;

  public TaskExecutionDate(LocalDateTime dateTime) {
    setDateTime(dateTime);
  }

  private void setDateTime(LocalDateTime dateTime) {
    if (dateTime == null) {
      throw new IllegalArgumentException("Execution date must not be null");
    } else if (dateTime.isBefore(LocalDateTime.now().minusDays(1))) {
      throw new IllegalArgumentException("Execution date must not be set before yesterday : "
              + dateTime);
    }
    this.dateTime = dateTime;
  }
}
