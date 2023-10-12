package zoeque.elephant.testtool;

import org.springframework.stereotype.Service;
import zoeque.elephant.domain.repository.IShoppingTaskRepository;

/**
 * The service class to delete all table in DB for JUnit test.
 */
@Service
public class ShoppingTaskDropForTestService {
  IShoppingTaskRepository repository;

  public ShoppingTaskDropForTestService(IShoppingTaskRepository repository) {
    this.repository = repository;
  }

  /**
   * Delete all task data in DB.
   */
  public void deleteAllData() {
    repository.deleteAll();
  }
}
