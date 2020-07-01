package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Product;
import pe.edu.upc.repository.IProductRepository;
import pe.edu.upc.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {
	@Autowired
	private IProductRepository pR;

	@Override
	@Transactional
	public Integer insert(Product product) {
		int rpta = pR.buscarNombreProducto(product.getName());
		if (rpta == 0) {
			pR.save(product);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void delete(long idProduct) {
		pR.deleteById(idProduct);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> list() {
		// TODO Auto-generated method stub
		return pR.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

	@Override
	public Optional<Product> findById(Long idProduct) {
		// TODO Auto-generated method stub
		return pR.findById(idProduct);
	}

	@Override
	public List<Product> fetchProductByName(String name) {
		return pR.fetchProductByName(name);

	}

	@Override
	public List<Product> fetchProductByCategoryName(String nameCategory) {
		// TODO Auto-generated method stub
		return pR.findProductByNameCategory(nameCategory);
	}

	@Override
	public List<Product> findByNameProductLikeIgnoreCase(String nameProduct) {
		// TODO Auto-generated method stub
		return pR.findByNameLikeIgnoreCase(nameProduct);
	}
}
