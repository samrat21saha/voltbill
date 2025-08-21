package in.voltmart.billingsoftware.repository;

import in.voltmart.billingsoftware.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
