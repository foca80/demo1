package pe.edu.upc.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.Category;
import pe.edu.upc.service.ICategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private ICategoryService caService;

	@GetMapping("/new")
	public String newCategory(Model model) {
		model.addAttribute("category", new Category());
		return "category/category";
	}

	@PostMapping("/save")
	public String saveCategory(@Valid Category categoria, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "category/category";
		} else {
			int rpta = caService.insert(categoria);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/category/category";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listCategories", caService.list());

		return "/category/category";
	}

	@GetMapping("/list")
	public String listCategories(Model model) {
		try {
			model.addAttribute("category", new Category());
			model.addAttribute("listCategories", caService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/category/listCategories";
	}

	@RequestMapping("/delete")
	public String delete(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				caService.delete(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar una categoria");
		}
		model.put("listCategories", caService.list());

//		return "redirect:/categories/list";
		return "/category/listCategories";
	}

	@GetMapping("/detalle/{id}")
	public String detailsCategory(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Category> category = caService.listarId(id);
			if (!category.isPresent()) {
				model.addAttribute("info", "Categoria no existe");
				return "redirect:/categories/list";
			} else {
				model.addAttribute("category", category.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/category/update";
	}

	@GetMapping("/listFind")
	public String listCategoriesFind(Model model) {
		try {
			model.addAttribute("category", new Category());
			model.addAttribute("listCategories", caService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/category/find";
	}

	@RequestMapping("/find")
	public String findByCategory(Map<String, Object> model, @ModelAttribute Category category) throws ParseException {

		List<Category> listCategories;
		category.setName(category.getName());
		listCategories = caService.findByName(category.getName());
		if (listCategories.isEmpty()) {
			listCategories = caService.findByNameLikeIgnoreCase(category.getName());
		}
		if (listCategories.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listCategories", listCategories);
		return "category/find";

	}

}
