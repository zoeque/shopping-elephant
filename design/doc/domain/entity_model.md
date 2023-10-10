@startuml
class ShoppingTask {
  ItemToBuy item
  TaskExecutionDate date
  NotificationStatus status
}
class NotificationStatus {
  NotificationStatusModel model
}
enum NotificationStatusModel { 
  PLANNED
  REPORTED
}
class ItemToBuy {
  String name
}
class TaskExecutionDate {
  LocalDateTime date
}

ShoppingTask o-- ItemToBuy
ShoppingTask o-- TaskExecutionDate
ShoppingTask o-- NotificationStatus
NotificationStatus --> NotificationStatusModel
@enduml
