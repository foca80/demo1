package pe.edu.upc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

	@Query("select o from Order o join fetch o.customerId c join fetch o.orderDetails od join fetch od.productId where o.id=?1")
	Optional<Order> fetchByOrderIdWithCustomerWhithOrderDetailWithProduct(Long id);

}
