package zoeque.elephant.adapter;

import io.vavr.control.Try;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zoeque.elephant.usecase.dto.ShoppingTaskDto;
import zoeque.elephant.usecase.service.ShoppingTaskDeleteService;
import zoeque.elephant.usecase.service.ShoppingTaskHandleService;

/**
 * The gateway performing as the endpoint of REST API.
 * HTTP request is sent with the JSON body that is deserialized
 * {@link ShoppingTaskDto}.
 */
@RestController
@CrossOrigin(origins = "*")
@Component
@Slf4j
public class ShoppingTaskController {

  ShoppingTaskHandleService handleService;
  ShoppingTaskDeleteService deleteService;

  public ShoppingTaskController(ShoppingTaskHandleService handleService,
                                ShoppingTaskDeleteService deleteService) {
    this.handleService = handleService;
    this.deleteService = deleteService;
  }

  /**
   * The creation process gateway of REST request.
   * Received value is passed to the {@link ShoppingTaskHandleService}.
   *
   * @param json Received JSON with deserialized {@link ShoppingTaskDto}.
   * @return {@link ResponseEntity} with the created instance with JSON style.
   */
  @PostMapping("/create")
  public ResponseEntity create(@RequestBody
                               ShoppingTaskDto json) {
    try {
      // attempt to create new shopping task instance.
      Try<ShoppingTaskDto> creationTry = handleService.create(json);

      if (creationTry.isFailure()) {
        throw new IllegalStateException(creationTry.getCause());
      }
      return ResponseEntity.ok().body(json);
    } catch (Exception e) {
      log.warn("Cannot save the shopping task : {}", json);
      return ResponseEntity.badRequest().body(e);
    }
  }

  /**
   * The REST endpoint to find all items in DB
   *
   * @return The {@link zoeque.elephant.domain.entity.ShoppingTask} in Database
   */
  @GetMapping("/find")
  public ResponseEntity findAll() {
    try {
      // attempt to find all shopping tasks.
      Try<List<ShoppingTaskDto>> all = handleService.findAll();
      return ResponseEntity.ok().body(all.get());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e);
    }
  }

  /**
   * The REST endpoint to delete the item
   *
   * @param json The item to delete
   * @return The result to delete item with {@link ShoppingTaskDto}
   */
  @PostMapping("/drop")
  public ResponseEntity delete(@RequestBody
                               ShoppingTaskDto json) {
    try {
      // attempt to delete the item
      var deleteItem = deleteService.delete(json);
      return ResponseEntity.ok().body(json);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e);
    }
  }
}
