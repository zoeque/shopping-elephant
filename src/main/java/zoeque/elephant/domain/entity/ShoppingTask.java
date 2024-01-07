package zoeque.elephant.domain.entity;

import io.vavr.control.Try;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zoeque.elephant.domain.model.NotificationStatusModel;
import zoeque.elephant.domain.valueobject.ItemToBuy;
import zoeque.elephant.domain.valueobject.TaskExecutionDate;

/**
 * The entity of the task to notify the item to buy.
 */
@Entity
@Getter
@NoArgsConstructor
public class ShoppingTask {

  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  ItemToBuy itemToBuy;
  TaskExecutionDate executionDate;
  NotificationStatusModel status;

  public ShoppingTask(ItemToBuy itemToBuy,
                      TaskExecutionDate executionDate,
                      NotificationStatusModel status) {
    setItemToBuy(itemToBuy);
    setExecutionDate(executionDate);
    setStatus(status);
  }

  public Try<ShoppingTask> updateStatus() {
    this.status = NotificationStatusModel.REPORTED;
    return Try.success(this);
  }

  private void setItemToBuy(ItemToBuy itemToBuy) {
    if (itemToBuy == null) {
      throw new IllegalArgumentException("Item must not be null");
    }
    this.itemToBuy = itemToBuy;
  }

  private void setExecutionDate(TaskExecutionDate executionDate) {
    if (executionDate == null) {
      throw new IllegalArgumentException("Execution date must not be null");
    }
    this.executionDate = executionDate;
  }

  private void setStatus(NotificationStatusModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Status must not be null");
    }
    this.status = model;
  }
}
