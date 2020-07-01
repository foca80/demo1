package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Product;

public interface IProductService {
	public Integer insert(Product product);

	public void delete(long idProduct);

	List<Product> list();

	Optional<Product> findById(Long idProduct);

	List<Product> fetchProductByName(String nameProduct);

	public List<Product> fetchProductByCategoryName(String nameCategory);

	public List<Product> findByNameProductLikeIgnoreCase(String nameProduct);

}
