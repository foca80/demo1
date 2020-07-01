package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Category;
import pe.edu.upc.repository.ICategoryRepository;
import pe.edu.upc.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {
	@Autowired
	private ICategoryRepository caR;

	@Override
	@Transactional
	public Integer insert(Category categoria) {
		int rpta = caR.buscarNombreCategoria(categoria.getName());
		if (rpta == 0) {
			caR.save(categoria);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void delete(long idCategoria) {
		caR.deleteById(idCategoria);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> list() {
		// TODO Auto-generated method stub
		return caR.findAll(Sort.by(Sort.Direction.DESC, "name"));
	}

	@Override
	public Optional<Category> listarId(long idCategory) {
		// TODO Auto-generated method stub
		return caR.findById(idCategory);
	}

	@Override
	public List<Category> findByName(String name) {
		// TODO Auto-generated method stub
		return caR.findByName(name);
	}

	@Override
	public List<Category> findByNameLikeIgnoreCase(String name) {
		// TODO Auto-generated method stub
		return caR.findByNameLikeIgnoreCase(name);
	}

}
