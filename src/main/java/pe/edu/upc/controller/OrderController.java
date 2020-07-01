package pe.edu.upc.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.Customer;
import pe.edu.upc.entity.Order;
import pe.edu.upc.entity.OrderDetail;
import pe.edu.upc.entity.Product;
import pe.edu.upc.service.ICustomerService;
import pe.edu.upc.service.IOrderService;
import pe.edu.upc.service.IProductService;

@Controller
@SessionAttributes("order")
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private IOrderService oS;

	@Autowired
	private ICustomerService cS;

	@Autowired
	private IProductService pS;

	@Secured("ROLE_ADMIN")
	@GetMapping("/form/{id}")
	public String formOrder(@PathVariable(value = "id") Long id, Model model) {
		try {
			Optional<Customer> customer = cS.findById(id);
			if (!customer.isPresent()) {
				model.addAttribute("info", "Cliente no existe");
				return "redirect:/customers/list";
			} else {
				Order a = new Order();
				a.setCustomerId(customer.get());
				model.addAttribute("order", a);
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "order/form";
	}

	@PostMapping("/save")
	public String saveOrder(Order order, Model model, @RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "quantity[]", required = false) Integer[] quantity, SessionStatus status) {
		try {

			if (itemId == null || itemId.length == 0) {
				model.addAttribute("info", "Orden no tiene productos");
				return "order/form";
			}

			for (int i = 0; i < itemId.length; i++) {
				Optional<Product> product = pS.findById(itemId[i]);
				if (product.isPresent()) {
					OrderDetail line = new OrderDetail();
					line.setQuantity(quantity[i]);
					line.setProductId(product.get());
					order.addDetailOrder(line);
				}
			}
			oS.insert(order);
			status.setComplete();
			model.addAttribute("success", "Orden Generada");
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "redirect:/customers/detail/" + order.getCustomerId().getId();
	}

	@GetMapping("/detail/{id}")
	public String detailOrder(@PathVariable(value = "id") Long id, Model model) {

		try {
			Optional<Order> order = oS.fetchByOrderIdWithCustomerWhithOrderDetailWithProduct(id);

			if (!order.isPresent()) {
				model.addAttribute("error", "Orden no existe");
				return "redirect:/customers/list";
			}

			model.addAttribute("order", order.get());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "order/detail";
	}

}
