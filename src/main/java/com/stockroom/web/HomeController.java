package com.stockroom.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.stockroom.service.CheckoutService;

/**
 * A controller is the traffic cop: a browser URL comes in, a page name goes out.
 */
@Controller
public class HomeController {

	private final CheckoutService checkouts;

	public HomeController(CheckoutService checkouts) {
		this.checkouts = checkouts;
	}

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("stats", checkouts.dashboard());
		return "home";
	}

	@GetMapping("/denied")
	public String denied() {
		return "denied";
	}
}
