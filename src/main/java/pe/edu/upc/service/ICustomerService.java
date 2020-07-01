package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Customer;

public interface ICustomerService {

	Optional<Customer> fetchByCustomerWithOrders(Long id);

	public Integer insert(Customer customer);

	public void delete(long idCustomer);

	List<Customer> list();

	Optional<Customer> findById(Long idCustomer);

}
