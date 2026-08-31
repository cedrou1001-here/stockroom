package com.stockroom.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stockroom.domain.InsufficientStockException;
import com.stockroom.domain.ItemNotAvailableException;
import com.stockroom.service.CheckoutService;
import com.stockroom.service.ItemService;
import com.stockroom.web.form.CheckoutForm;

import jakarta.validation.Valid;

@Controller
public class CheckoutController {

	private final CheckoutService checkouts;
	private final ItemService items;

	public CheckoutController(CheckoutService checkouts, ItemService items) {
		this.checkouts = checkouts;
		this.items = items;
	}

	@GetMapping("/checkouts")
	public String list(Model model) {
		model.addAttribute("checkouts", checkouts.findOpen());
		return "checkouts/list";
	}

	@GetMapping("/checkouts/new")
	public String newForm(@RequestParam(name = "itemId", required = false) Long itemId, Model model) {
		CheckoutForm form = new CheckoutForm();
		if (itemId != null) {
			form.setItemId(itemId);
		}
		model.addAttribute("checkoutForm", form);
		model.addAttribute("itemChoices", items.search(""));
		return "checkouts/form";
	}

	@PostMapping("/checkouts")
	public String create(@Valid @ModelAttribute("checkoutForm") CheckoutForm form, BindingResult binding, Model model,
			Principal principal, RedirectAttributes redirect) {
		if (binding.hasErrors()) {
			model.addAttribute("itemChoices", items.search(""));
			return "checkouts/form";
		}
		try {
			checkouts.checkout(form.getItemId(), form.getBorrowerName(), form.getBorrowerEmail(), form.getQuantity(),
					form.getDueDate(), principal.getName());
		}
		catch (InsufficientStockException | ItemNotAvailableException ex) {
			binding.reject("stock", ex.getMessage());
			model.addAttribute("itemChoices", items.search(""));
			return "checkouts/form";
		}
		redirect.addFlashAttribute("message", "Checked out.");
		return "redirect:/checkouts";
	}

	@PostMapping("/checkouts/{id}/return")
	public String returnItem(@PathVariable Long id, RedirectAttributes redirect) {
		try {
			checkouts.returnCheckout(id);
			redirect.addFlashAttribute("message", "Returned. Stock is back on the shelf.");
		}
		catch (IllegalStateException ex) {
			redirect.addFlashAttribute("error", ex.getMessage());
		}
		return "redirect:/checkouts";
	}
}
