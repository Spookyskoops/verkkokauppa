package com.projekti.verkkokauppa.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projekti.verkkokauppa.domain.TuoteRepository;
import com.projekti.verkkokauppa.domain.ValmistajaRepository;
import com.projekti.verkkokauppa.domain.Tuote;

@Controller
public class TuoteController {
	@Autowired
	private TuoteRepository trepository;
	@Autowired
	private ValmistajaRepository vrepository;
	
	@RequestMapping("/tuotelista")
	public String tuoteLista(Model model) {
		model.addAttribute("Tuotteet", trepository.findAll());
		return "tuotelistat";	
	}
	
	@GetMapping({"/", "index"})
	public String showHome() {
		return "index";
	}
	
	 @RequestMapping(value="/login")
	    public String login() {	
	        return "login";
	    }	
	 
	 @RequestMapping(value = "addTuote")
		public String addTuote (Model model) {
			model.addAttribute("tuote", new Tuote());
			model.addAttribute("valmistajat", vrepository.findAll());
			return "addTuote";
		}
	 
		@RequestMapping(value = "/saveTuote", method = RequestMethod.POST)
		public String saveTuote (Tuote tuote) {
			trepository.save(tuote);
			return "redirect:tuotelista";
		}
		
		@RequestMapping(value = "/editTuote/{id}")
		public String editTuote(@PathVariable("id") Long Id, Model model){
			model.addAttribute("tuote", trepository.findById(Id));
			model.addAttribute("valmistajat", vrepository.findAll());
			return "editTuote";
		}
		
		
		@RequestMapping(value = "/deleteTuote/{id}", method = RequestMethod.GET)
		public String deleteTuote(@PathVariable Long id) {
			trepository.deleteById(id);
			return "redirect:/tuotelista";
	}
}
