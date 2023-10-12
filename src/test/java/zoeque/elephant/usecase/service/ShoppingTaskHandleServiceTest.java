package zoeque.elephant.usecase.service;

import io.vavr.control.Try;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zoeque.elephant.domain.entity.ShoppingTask;
import zoeque.elephant.domain.repository.IShoppingTaskRepository;
import zoeque.elephant.testtool.ShoppingTaskDropForTestService;
import zoeque.elephant.usecase.dto.ShoppingTaskDto;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ShoppingTaskHandleServiceTest {
  @Autowired
  ShoppingTaskDropForTestService dropForTestService;
  @Autowired
  ShoppingTaskHandleService service;
  @Autowired
  IShoppingTaskRepository repository;

  @BeforeEach
  public void cleanTable() {
    dropForTestService.deleteAllData();
  }

  @Test
  public void givenFullSpecifiedDto_creationProcessReturnsSuccess() {
    ShoppingTaskDto dto = new ShoppingTaskDto("test", "2999/12/31 23:59:59");
    Try<ShoppingTaskDto> createTry = service.create(dto);
    Assertions.assertTrue(createTry.isSuccess());

    List<ShoppingTask> all = repository.findAll();
    Assertions.assertEquals(1, all.size());
  }

  @ParameterizedTest
  @ValueSource(strings = {"1995/12/31 23:59:59",
          "1995/12/3123:59:59",
          "1995/12/31"})
  public void givenInvalidDateSpecifiedDto_creationProcessReturnsFailure(String dateTime) {
    ShoppingTaskDto dto = new ShoppingTaskDto("test", dateTime);
    Try<ShoppingTaskDto> createTry = service.create(dto);
    Assertions.assertTrue(createTry.isFailure());

    List<ShoppingTask> all = repository.findAll();
    Assertions.assertEquals(0, all.size());
  }
}
