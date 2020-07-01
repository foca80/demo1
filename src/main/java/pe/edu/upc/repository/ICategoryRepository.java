package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
	@Query("select count(c.name) from Category c where c.name =:name")
	public int buscarNombreCategoria(@Param("name") String nombreCategoria);

	@Query("select c from Category c where c.name like %:name%")
	List<Category> findByName(String name);

	List<Category> findByNameLikeIgnoreCase(String name);
}
