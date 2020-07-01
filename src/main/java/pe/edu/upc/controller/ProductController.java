package pe.edu.upc.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Product;
import pe.edu.upc.service.ICategoryService;
import pe.edu.upc.service.IProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private IProductService pService;
	@Autowired
	private ICategoryService cService;

	@GetMapping("/new")
	public String newProduct(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("listCategories", cService.list());
		return "product/product";
	}

	@PostMapping("/save")
	public String saveProduct(@Valid Product product, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listCategories", cService.list());
			return "product/product";
		} else {
			int rpta = pService.insert(product);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				model.addAttribute("listCategories", cService.list());
				return "/product/product";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listProducts", pService.list());

		return "/product/listProducts";
	}

	@GetMapping("/list")
	public String listProduct(Model model) {
		try {
			model.addAttribute("product", new Product());
			model.addAttribute("listProducts", pService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/product/listProducts";
	}

	@GetMapping("/listFind")
	public String listProductFind(Model model) {
		try {
			model.addAttribute("product", new Product());
			model.addAttribute("listProducts", pService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/product/find";
	}

	@RequestMapping("/find")
	public String find(Map<String, Object> model, @ModelAttribute Product product) throws ParseException {

		List<Product> listProducts;

		product.setName(product.getName());
		listProducts = pService.fetchProductByName(product.getName());

		if (listProducts.isEmpty()) {
			listProducts = pService.fetchProductByCategoryName(product.getName());
		}

		if (listProducts.isEmpty()) {
			listProducts = pService.findByNameProductLikeIgnoreCase(product.getName());
		}

		if (listProducts.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listProducts", listProducts);
		return "product/find";

	}

	@RequestMapping("/delete")
	public String deleteProduct(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				pService.delete(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un producto");
		}
		model.put("listProducts", pService.list());

//		return "redirect:/categories/list";
		return "/product/listProducts";
	}

	@GetMapping(value = "/list/{name}", produces = { "application/json" })
	public @ResponseBody List<Product> fetchProducts(@PathVariable String name, Model model) {
		List<Product> products = null;
		try {
			products = pService.fetchProductByName(name);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return products;
	}

	@GetMapping(value = "/view/{id}")
	public String ver(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Product> product = pService.findById(id);
		if (product == null) {
			flash.addFlashAttribute("error", "El Producto no existe en la base de datos");
			return "redirect:/products/listar";
		}

		model.put("product", product.get());

		return "product/view";
	}
}
