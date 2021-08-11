package com.apecmdb.apecmdb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.apecmdb.apecmdb.model.Actor;
import com.apecmdb.apecmdb.model.ActorRepository;

@Controller
public class ActorController {
	
	@Autowired
	private final ActorRepository actorRepository;
	
	@Autowired
	public ActorController(ActorRepository actorRepository) {
		this.actorRepository = actorRepository;
	}
	
	@GetMapping("/admin/actor")
	public String showActor(Actor actor, BindingResult result, Model model) {
		model.addAttribute("actors", actorRepository.findAll());
		return "/admin/actor/read";
	}
	
	@GetMapping("/admin/actor/add")
	public String showActorForm(Actor actor) {
		return "admin/actor/add";
	}
	
	@PostMapping("admin/actor/save")
	public String addActor(@Valid Actor actor, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "/admin/actor/add";
		}
		
		actorRepository.save(actor);
		model.addAttribute("actors", actorRepository.findAll());
		return "redirect:/admin/actor";
	}
	
	@GetMapping("admin/actor/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid actor Id:" + id));
        model.addAttribute("actor", actor);
        return "/admin/actor/update";
    }
    
    @PostMapping("admin/actor/update/{id}")
    public String updateActor(@PathVariable("id") long id, @Valid Actor actor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            actor.setId(id);
            return "/admin/actor/update";
        }
        
        actorRepository.save(actor);
        model.addAttribute("actors", actorRepository.findAll());
        return "redirect:/admin/actor";
    }
    
    @GetMapping("admin/actor/delete/{id}")
    public String deleteActor(@PathVariable("id") long id, Model model) {
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid actor Id:" + id));
        actorRepository.delete(actor);
        model.addAttribute("actors", actorRepository.findAll());
        return "redirect:/admin/actor";
    }

}
