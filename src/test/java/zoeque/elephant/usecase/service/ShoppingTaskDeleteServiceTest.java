package zoeque.elephant.usecase.service;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zoeque.elephant.domain.entity.ShoppingTask;
import zoeque.elephant.domain.model.NotificationStatusModel;
import zoeque.elephant.domain.repository.IShoppingTaskRepository;
import zoeque.elephant.domain.valueobject.ItemToBuy;
import zoeque.elephant.domain.valueobject.TaskExecutionDate;
import zoeque.elephant.testtool.ShoppingTaskDropForTestService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ShoppingTaskDeleteServiceTest {
  @Autowired
  ShoppingTaskDeleteService service;
  @Autowired
  IShoppingTaskRepository repository;

  @Autowired
  ShoppingTaskDropForTestService dropForTestService;

  @BeforeEach
  public void cleanTable() {
    dropForTestService.deleteAllData();
  }

  @Test
  public void givenExpiredItem_whenDelete_noItemsInDb() {
    // save test data
    repository.save(new ShoppingTask(new ItemToBuy("test"), new TaskExecutionDate(LocalDateTime.now().minusDays(1)), NotificationStatusModel.REPORTED));
    Assertions.assertEquals(1, repository.findAll().size());

    // attempt to delete
    service.delete();

    List<ShoppingTask> all = repository.findAll();

    Assertions.assertEquals(0, all.size());
  }
}
