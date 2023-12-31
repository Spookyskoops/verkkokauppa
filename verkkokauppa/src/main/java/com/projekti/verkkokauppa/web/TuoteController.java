package com.projekti.verkkokauppa.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projekti.verkkokauppa.domain.TuoteRepository;
import com.projekti.verkkokauppa.domain.Valmistaja;
import com.projekti.verkkokauppa.domain.ValmistajaRepository;

import jakarta.validation.Valid;

import com.projekti.verkkokauppa.domain.Tuote;

@Controller
public class TuoteController {
	@Autowired
	private TuoteRepository trepository;
	@Autowired
	private ValmistajaRepository vrepository;

	//Listaa tuotteet
	@RequestMapping("/tuotelista")
	public String tuoteLista(Model model) {
		model.addAttribute("Tuotteet", trepository.findAll());
		return "tuotelistat";
	}

	//Etusivu
	@GetMapping({ "/", "index" })
	public String showHome() {
		return "index";
	}
	
	//Kirjautuminen
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	//Tuotteen lisääminen
	@RequestMapping(value = "addTuote")
	public String addTuote(Model model) {
		model.addAttribute("tuote", new Tuote());
		model.addAttribute("valmistajat", vrepository.findAll());
		return "addTuote";
	}

	//Tuotteen tallentaminen
	@RequestMapping(value = "/saveTuote", method = RequestMethod.POST)
	public String saveTuote(@Valid @ModelAttribute("tuote") Tuote tuote, BindingResult bindingResult, Model model, @RequestParam("kuva") String kuva) {
		if (bindingResult.hasErrors()) {
			System.out.println("Virhe" + tuote);
			model.addAttribute("valmistajat", vrepository.findAll());
			return "addTuote";
		}
		
		tuote.setKuva(kuva);
		trepository.save(tuote);
		return "redirect:tuotelista";
	}
	
	//Tuotteen muokkauksen tallentaminen
		@RequestMapping(value = "/saveTuoteEdit", method = RequestMethod.POST)
		public String saveTuoteEdit(@Valid @ModelAttribute("tuote") Tuote tuote, BindingResult bindingResult, Model model, @RequestParam("kuva") String kuva) {
			if (bindingResult.hasErrors()) {
				System.out.println("Virhe" + tuote);
				model.addAttribute("valmistajat", vrepository.findAll());
				return "editTuote";
			}
			
			tuote.setKuva(kuva);
			trepository.save(tuote);
			return "redirect:tuotelista";
		}


	//Listaa valmistajat
	@RequestMapping("/valmistajat")
	public String valmistajaLista(Model model) {
		model.addAttribute("Valmistajat", vrepository.findAll());
		return "valmistajat";
	}

	//Lisää valmistajan
	@RequestMapping(value = "addValmistaja")
	public String addValmistaja(Model model) {
		model.addAttribute("valmistaja", new Valmistaja());
		return "addValmistaja";
	}

	//Tallentaa valmistajan
	@RequestMapping(value = "/saveValmistaja", method = RequestMethod.POST)
	public String saveValmistaja(@Valid @ModelAttribute("valmistaja") Valmistaja valmistaja,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println("Virhe" + valmistaja);
			return "addValmistaja";
		}
		vrepository.save(valmistaja);
		return "redirect:valmistajat";
	}

	//Muokkaa tuotetta
	@RequestMapping(value = "/editTuote/{id}")
	public String editTuote(@PathVariable("id") Long Id, Model model) {
		model.addAttribute("tuote", trepository.findById(Id));
		model.addAttribute("valmistajat", vrepository.findAll());
		return "editTuote";
	}

	//Poistaa tuotteen
	@RequestMapping(value = "/deleteTuote/{id}", method = RequestMethod.GET)
	public String deleteTuote(@PathVariable Long id) {
		trepository.deleteById(id);
		return "redirect:/tuotelista";
	}

	//Poistaa valmistajan
	@RequestMapping(value = "/deleteValmistaja/{id}", method = RequestMethod.GET)
	public String deleteValmistaja(@PathVariable Long id) {
		vrepository.deleteById(id);
		return "redirect:/valmistajat";
	}
}
