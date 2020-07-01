package pe.edu.upc.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.Customer;
import pe.edu.upc.service.impl.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService cS;

	@GetMapping("/new")
	public String newCustomer(Model model) {
		model.addAttribute("customer", new Customer());
		return "customer/customer";
	}

	@PostMapping("/save")
	public String saveCustomer(@Valid Customer customer, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "customer/customer";
		} else {
			int rpta = cS.insert(customer);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/customer/customer";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listCustomers", cS.list());

		return "/customer/customer";
	}

	@GetMapping("/list")
	public String listCustomers(Model model) {
		try {
			model.addAttribute("customer", new Customer());
			model.addAttribute("listCustomers", cS.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/customer/listCustomer";
	}

	@RequestMapping("/delete")
	public String delete(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				cS.delete(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar una categoria");
		}
		model.put("listCustomers", cS.list());

//		return "redirect:/categories/list";
		return "/customer/listCustomer";
	}
	@Secured("ROLE_USER")
	@GetMapping("/detail/{id}")
	public String detailsCustomer(@PathVariable(value = "id") Long id, Model model) {
		try {
			Optional<Customer> customer = cS.findById(id);

			if (!customer.isPresent()) {
				model.addAttribute("info", "Cliente no existe");
				return "redirect:/customers/list";
			} else {
				model.addAttribute("customer", customer.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/customer/detail";
	}
}
