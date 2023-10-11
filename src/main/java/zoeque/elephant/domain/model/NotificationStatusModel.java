package zoeque.elephant.domain.model;

import lombok.Getter;

/**
 * The model class to define the status for notification.
 */
public enum NotificationStatusModel {
  PLANNED("planned"),
  REPORTED("reported");
  @Getter
  String status;

  NotificationStatusModel(String status) {
    this.status = status;
  }
}
