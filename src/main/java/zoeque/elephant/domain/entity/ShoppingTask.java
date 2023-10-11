package zoeque.elephant.domain.entity;

import jakarta.persistence.Entity;
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
  ItemToBuy itemToBuy;
  TaskExecutionDate executionDate;
  NotificationStatusModel status;

  public ShoppingTask(ItemToBuy itemToBuy,
                      TaskExecutionDate executionDate,
                      NotificationStatusModel status) {
    this.itemToBuy = itemToBuy;
    this.executionDate = executionDate;
    this.status = status;
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
