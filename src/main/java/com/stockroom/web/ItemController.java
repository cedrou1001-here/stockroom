package com.stockroom.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stockroom.domain.Item;
import com.stockroom.service.DuplicateSkuException;
import com.stockroom.service.ItemService;

import jakarta.validation.Valid;

@Controller
public class ItemController {

	private final ItemService items;

	public ItemController(ItemService items) {
		this.items = items;
	}

	@GetMapping("/items")
	public String list(@RequestParam(name = "q", required = false) String query, Model model) {
		model.addAttribute("items", items.search(query));
		model.addAttribute("q", query == null ? "" : query);
		return "items/list";
	}

	@GetMapping("/items/new")
	public String newForm(Model model) {
		model.addAttribute("item", new Item());
		model.addAttribute("editing", false);
		return "items/form";
	}

	@PostMapping("/items")
	public String create(@Valid @ModelAttribute("item") Item item, BindingResult binding, Model model,
			RedirectAttributes redirect) {
		if (binding.hasErrors()) {
			model.addAttribute("editing", false);
			return "items/form";
		}
		try {
			items.create(item);
		}
		catch (DuplicateSkuException ex) {
			binding.rejectValue("sku", "duplicate", "That SKU is already used.");
			model.addAttribute("editing", false);
			return "items/form";
		}
		redirect.addFlashAttribute("message", "Item saved.");
		return "redirect:/items";
	}

	@GetMapping("/items/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		model.addAttribute("item", items.get(id));
		model.addAttribute("editing", true);
		return "items/form";
	}

	@PostMapping("/items/{id}")
	public String update(@PathVariable Long id, @Valid @ModelAttribute("item") Item item, BindingResult binding,
			Model model, RedirectAttributes redirect) {
		if (binding.hasErrors()) {
			model.addAttribute("editing", true);
			return "items/form";
		}
		try {
			items.update(id, item);
		}
		catch (DuplicateSkuException ex) {
			binding.rejectValue("sku", "duplicate", "That SKU is already used.");
			model.addAttribute("editing", true);
			return "items/form";
		}
		redirect.addFlashAttribute("message", "Item updated.");
		return "redirect:/items";
	}
}
