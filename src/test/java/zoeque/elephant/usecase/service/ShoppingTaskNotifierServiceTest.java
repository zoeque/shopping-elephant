package zoeque.elephant.usecase.service;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zoeque.elephant.domain.entity.ShoppingTask;
import zoeque.elephant.domain.model.NotificationStatusModel;
import zoeque.elephant.domain.repository.IShoppingTaskRepository;
import zoeque.elephant.domain.specification.ShoppingTaskSpecification;
import zoeque.elephant.domain.valueobject.ItemToBuy;
import zoeque.elephant.domain.valueobject.TaskExecutionDate;
import zoeque.elephant.testtool.ShoppingTaskDropForTestService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ShoppingTaskNotifierServiceTest {
  @Mock
  ApplicationEventPublisher publisher;
  @Autowired
  IShoppingTaskRepository repository;
  @Autowired
  ShoppingTaskSpecification specification;
  @Autowired
  ShoppingTaskDropForTestService dropForTestService;

  @BeforeEach
  public void cleanUp() {
    dropForTestService.deleteAllData();
  }

  @Test
  public void givenTheTargetData_attemptToNotify_theTargetHasReportedState() {
    ShoppingTask test
            = new ShoppingTask(new ItemToBuy("test"),
            new TaskExecutionDate(LocalDateTime.now()),
            NotificationStatusModel.PLANNED);

    // check the data is in database
    repository.save(test);
    List<ShoppingTask> all = repository.findAll(specification.itemToNotify());
    Assertions.assertEquals(1, all.size());

    ShoppingTaskNotifierService service = new ShoppingTaskNotifierService(repository, specification, publisher);
    Mockito.doNothing().when(publisher).publishEvent(Mockito.any());

    service.notifyToUser(); // found the data and update with reported state

    List<ShoppingTask> reportedData = repository.findAll();
    Assertions.assertEquals(reportedData.get(0).getStatus().getStatus(),
            NotificationStatusModel.REPORTED.getStatus());
  }
}
