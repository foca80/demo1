package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Order;
import pe.edu.upc.repository.IOrderRepository;
import pe.edu.upc.service.IOrderService;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private IOrderRepository oR;

	@Transactional
	@Override
	public Order insert(Order order) {
		return oR.save(order);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Order> list() {
		return oR.findAll();
	}

	@Override
	public void delete(long idOrder) {
		oR.deleteById(idOrder);
	}

	@Override
	public Optional<Order> findById(long id) {
		return oR.findById(id);

	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Order> fetchByOrderIdWithCustomerWhithOrderDetailWithProduct(Long id) throws Exception {
		return oR.fetchByOrderIdWithCustomerWhithOrderDetailWithProduct(id);

	}

}
