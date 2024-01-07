package zoeque.elephant.usecase.service;

import io.vavr.control.Try;
import java.util.List;
import zoeque.elephant.domain.entity.ShoppingTask;

/**
 * The interface for the shopping task handling.
 */
public interface IShoppingTaskHandleService {
  default Try<List<ShoppingTask>> notifyToUser() {
    return null;
  }
}
