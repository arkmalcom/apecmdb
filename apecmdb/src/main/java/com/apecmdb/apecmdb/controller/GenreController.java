package com.apecmdb.apecmdb.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.apecmdb.apecmdb.model.Genre;
import com.apecmdb.apecmdb.model.GenreRepository;

@Controller
public class GenreController {
	
	@Autowired
	private final GenreRepository genreRepository;
	
	@Autowired
	public GenreController(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}
	
	@GetMapping("/admin/genre")
	public String showGenre(Genre genre, BindingResult result, Model model) {
		model.addAttribute("genres", genreRepository.findAll());
		return "/admin/genre/read";
	}
	
	@GetMapping("/admin/genre/add")
	public String showGenreForm(Genre genre) {
		return "admin/genre/add";
	}
	
	@PostMapping("admin/genre/save")
	public String addGenre(@Valid Genre genre, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "/admin/genre/add";
		}
		
		genreRepository.save(genre);
		model.addAttribute("genres", genreRepository.findAll());
		return "redirect:/admin/genre";
	}
	
	@GetMapping("admin/genre/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));
        model.addAttribute("genre", genre);
        return "/admin/genre/update";
    }
    
    @PostMapping("admin/genre/update/{id}")
    public String updateGenre(@PathVariable("id") long id, @Valid Genre genre, BindingResult result, Model model) {
        if (result.hasErrors()) {
            genre.setId(id);
            return "/admin/genre/update";
        }
        
        genreRepository.save(genre);
        model.addAttribute("genres", genreRepository.findAll());
        return "redirect:/admin/genre";
    }
    
    @GetMapping("admin/genre/delete/{id}")
    public String deleteGenre(@PathVariable("id") long id, Model model) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));
        genreRepository.delete(genre);
        model.addAttribute("genres", genreRepository.findAll());
        return "redirect:/admin/genre";
    }
	
}
