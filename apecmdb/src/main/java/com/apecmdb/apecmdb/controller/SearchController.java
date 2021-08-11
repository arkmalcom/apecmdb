package com.apecmdb.apecmdb.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.SearchService;

import retrofit2.Response;

@Controller
public class SearchController {
	
	Tmdb tmdb = new Tmdb("ed51842525805095bb925db00a03edc4");
	SearchService searchService = tmdb.searchService();
	
	@GetMapping("/search")
	public String showByTitle(@RequestParam(value="t", required=true) String t, Model model) {
		if(t == "" || t == null) {
			return "redirect:/";
		}
		else {
			try {
				Response<MovieResultsPage> response = searchService
						.movie(t, null, "es", "", false, null, null)
						.execute();
				if(response.isSuccessful()) {		
					List<String> releaseYearList = new ArrayList<String>();
					Calendar calendar = Calendar.getInstance();	
					MovieResultsPage movies = response.body();
					
					for (int i=0; i< movies.results.size(); i++) {
						BaseMovie movie = movies.results.get(i);
						if(movie.release_date != null) {
							calendar.setTime(movie.release_date);
							int releaseYear = calendar.get(Calendar.YEAR);
							releaseYearList.add(Integer.toString(releaseYear));
						}
						else {
							releaseYearList.add("N/A");
						}
					}
					model.addAttribute("movies", movies.results);
					model.addAttribute("releaseYears", releaseYearList);
				}
			
			}
			catch(Exception e) {
				
			}
			return "search";
		}
	}

}
