package zoeque.elephant.domain.specification;

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
public class ShoppingTaskSpecificationTest {
  @Autowired
  IShoppingTaskRepository repository;
  @Autowired
  ShoppingTaskSpecification specification;
  @Autowired
  ShoppingTaskDropForTestService dropService;

  @BeforeEach
  public void setup() {
    dropService.deleteAllData();
  }

  @Test
  public void ifSpecificationIsFullySpecified_repositoryCanGetEntity() {
    ShoppingTask test
            = new ShoppingTask(new ItemToBuy("test"),
            new TaskExecutionDate(LocalDateTime.now()),
            NotificationStatusModel.PLANNED);

    repository.save(test);
    List<ShoppingTask> all = repository.findAll(specification.itemToNotify());
    Assertions.assertEquals(1, all.size());
  }
}
