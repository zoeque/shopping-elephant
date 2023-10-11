package zoeque.elephant.domain.valueobject;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The item name that is scheduled to buy.
 */
@Embeddable
@Getter
@NoArgsConstructor
public class ItemToBuy {
  String name;

  public ItemToBuy(String name) {
    setName(name);
  }

  private void setName(String name) {
    if (StringUtils.isEmpty(name)) {
      throw new IllegalArgumentException("The name must not be null");
    }
    this.name = name;
  }
}
