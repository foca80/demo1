package pe.edu.upc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Customer;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

	@Query("SELECT c FROM Customer c LEFT JOIN FETCH c.orders o where c.id=?1")
	Optional<Customer> fetchByCustomerWithOrders(Long id);

	@Query("select count(a.dni) from Customer a where a.dni =:dni")
	public int buscarDniCustomer(@Param("dni") String dni);

}
