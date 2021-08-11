package com.apecmdb.apecmdb.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.apecmdb.apecmdb.model.Director;
import com.apecmdb.apecmdb.model.DirectorRepository;

@Controller
public class DirectorController {
	@Autowired
	private final DirectorRepository directorRepository;
	
	@Autowired
	public DirectorController(DirectorRepository directorRepository) {
		this.directorRepository = directorRepository;
	}
	
	@GetMapping("/admin/director")
	public String showDirector(Director director, BindingResult result, Model model) {
		model.addAttribute("directors", directorRepository.findAll());
		return "/admin/director/read";
	}
	
	@GetMapping("/admin/director/add")
	public String showDirectorForm(Director director) {
		return "admin/director/add";
	}
	
	@PostMapping("admin/director/save")
	public String addDirector(@Valid Director director, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "/admin/director/add";
		}
		
		directorRepository.save(director);
		model.addAttribute("directors", directorRepository.findAll());
		return "redirect:/admin/director";
	}
	
	@GetMapping("admin/director/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Director director = directorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid director Id:" + id));
        model.addAttribute("director", director);
        return "/admin/director/update";
    }
    
    @PostMapping("admin/director/update/{id}")
    public String updateDirector(@PathVariable("id") long id, @Valid Director director, BindingResult result, Model model) {
        if (result.hasErrors()) {
            director.setId(id);
            return "/admin/director/update";
        }
        
        directorRepository.save(director);
        model.addAttribute("directors", directorRepository.findAll());
        return "redirect:/admin/director";
    }
    
    @GetMapping("admin/director/delete/{id}")
    public String deleteDirector(@PathVariable("id") long id, Model model) {
        Director director = directorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid director Id:" + id));
        directorRepository.delete(director);
        model.addAttribute("directors", directorRepository.findAll());
        return "redirect:/admin/director";
    }
}
