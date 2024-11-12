package zoeque.elephant.domain.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import zoeque.elephant.domain.entity.ShoppingTask;
import zoeque.elephant.domain.entity.ShoppingTask_;
import zoeque.elephant.domain.model.NotificationStatusModel;
import zoeque.elephant.domain.valueobject.TaskExecutionDate_;

/**
 * The entity specification for {@link ShoppingTask}.
 */
@Component
public class ShoppingTaskSpecification {

  /**
   * The specification that is not reported and
   * task must be executed today.
   *
   * @return The specification with the above status.
   */
  public Specification<ShoppingTask> itemToNotify() {
    return new Specification<ShoppingTask>() {
      @Override
      public Predicate toPredicate(Root<ShoppingTask> root, CriteriaQuery<?> query,
                                   CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.lessThanOrEqualTo(
                root.get(ShoppingTask_.EXECUTION_DATE)
                        .get(TaskExecutionDate_.DATE_TIME),
                LocalDateTime.now()
        ));
        predicates.add(criteriaBuilder.equal(root.get(ShoppingTask_.STATUS),
                NotificationStatusModel.PLANNED));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      }
    };
  }

  /**
   * The specification that is already reported and need to delete.
   *
   * @return The specification with the above status.
   */
  public Specification<ShoppingTask> itemToDelete() {
    return new Specification<ShoppingTask>() {
      @Override
      public Predicate toPredicate(Root<ShoppingTask> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.lessThan(root.get(ShoppingTask_.EXECUTION_DATE)
                .get(TaskExecutionDate_.DATE_TIME), LocalDateTime.now()));

        predicates.add(criteriaBuilder.equal(root.get(ShoppingTask_.STATUS),
                NotificationStatusModel.REPORTED));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      }
    };
  }
}
