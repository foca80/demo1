package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
	@Query("select count(p.name) from Product p where p.name =:name")
	public int buscarNombreProducto(@Param("name") String nombreProduct);

	@Query("select p from Product p where p.name like %?1%")
	List<Product> fetchProductByName(String nombreProduct);

	@Query("select p from Product p where p.category.name like %?1%")
	public List<Product> findProductByNameCategory(String nameCategory);

	public List<Product> findByNameLikeIgnoreCase(String nameProduct);

}
