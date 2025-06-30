package zoeque.elephant.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zoeque.elephant.domain.entity.ShoppingTask;
import zoeque.elephant.domain.specification.ShoppingTaskSpecification;

/**
 * The interface for repository to handle {@link ShoppingTask}.
 */
@Repository
public interface IShoppingTaskRepository extends JpaRepository<ShoppingTask, Long>,
        JpaSpecificationExecutor<ShoppingTask> {
  List<ShoppingTask> findAll(Specification<ShoppingTask> specification);

  List<ShoppingTask> findShoppingTaskById(Long id);
}
