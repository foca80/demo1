package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Customer;
import pe.edu.upc.repository.ICustomerRepository;
import pe.edu.upc.service.ICustomerService;

@Service
public class CustomerService implements ICustomerService {

	@Autowired
	private ICustomerRepository cR;

	@Override
	public Optional<Customer> fetchByCustomerWithOrders(Long id) {
		return cR.fetchByCustomerWithOrders(id);
	}

	@Override
	public Integer insert(Customer customer) {
		int rpta = cR.buscarDniCustomer(customer.getDni());
		if (rpta == 0) {
			cR.save(customer);
		}
		return rpta;
	}

	@Override
	public void delete(long idCustomer) {
		cR.deleteById(idCustomer);
	}

	@Override
	public List<Customer> list() {
		// TODO Auto-generated method stub
		return cR.findAll(Sort.by(Sort.Direction.DESC, "firstName"));
	}

	@Override
	public Optional<Customer> findById(Long idCustomer) {
		// TODO Auto-generated method stub
		return cR.findById(idCustomer);
	}

}
