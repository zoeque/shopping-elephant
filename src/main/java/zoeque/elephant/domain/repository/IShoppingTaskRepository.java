package zoeque.elephant.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zoeque.elephant.domain.entity.ShoppingTask;

/**
 * The interface for repository to handle {@link ShoppingTask}.
 */
@Repository
public interface IShoppingTaskRepository extends JpaRepository<ShoppingTask, Long>,
        JpaSpecificationExecutor<ShoppingTask> {
}
