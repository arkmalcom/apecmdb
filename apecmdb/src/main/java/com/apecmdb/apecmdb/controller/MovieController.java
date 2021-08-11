package com.apecmdb.apecmdb.controller;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.apecmdb.apecmdb.model.ActorRepository;
import com.apecmdb.apecmdb.model.DirectorRepository;
import com.apecmdb.apecmdb.model.GenreRepository;
//import com.apecmdb.apecmdb.model.Movie;
import com.apecmdb.apecmdb.model.MovieRepository;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.CastMember;
import com.uwetrottmann.tmdb2.entities.Credits;
import com.uwetrottmann.tmdb2.entities.CrewMember;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.entities.Videos;
import com.uwetrottmann.tmdb2.entities.Videos.Video;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.services.MoviesService;

import retrofit2.Response;

@Controller
public class MovieController {
	
	Tmdb tmdb = new Tmdb("ed51842525805095bb925db00a03edc4");
	MoviesService moviesService = tmdb.moviesService();
	
	
	/*
	 * @Autowired private final MovieRepository movieRepository;
	 * 
	 * @Autowired private final DirectorRepository directorRepository;
	 * 
	 * @Autowired private final GenreRepository genreRepository;
	 * 
	 * @Autowired private final ActorRepository actorRepository;
	 * 
	 * @Autowired public MovieController(MovieRepository movieRepository,
	 * DirectorRepository directorRepository, GenreRepository genreRepository,
	 * ActorRepository actorRepository) { this.actorRepository = actorRepository;
	 * this.directorRepository = directorRepository; this.genreRepository =
	 * genreRepository; this.movieRepository = movieRepository; }
	 * 
	 * @GetMapping("/admin/movie") public String showMovie(Movie movie,
	 * BindingResult result, Model model) { model.addAttribute("movies",
	 * movieRepository.findAll()); return "/admin/movie/read"; }
	 * 
	 * @GetMapping("/admin/movie/add") public String showMovieForm(Movie movie,
	 * Model model) { model.addAttribute("actors", actorRepository.findAll());
	 * model.addAttribute("directors", directorRepository.findAll());
	 * model.addAttribute("genres", genreRepository.findAll()); return
	 * "admin/movie/add"; }
	 * 
	 * @PostMapping("admin/movie/save") public String addMovie(@Valid Movie movie,
	 * BindingResult result, Model model) { if(result.hasErrors()) { return
	 * "/admin/movie/add"; } movieRepository.save(movie);
	 * model.addAttribute("movies", movieRepository.findAll()); return
	 * "redirect:/admin/movie"; }
	 * 
	 * @GetMapping("admin/movie/update/{id}") public String
	 * showUpdateForm(@PathVariable("id") long id, Model model) { Movie movie =
	 * movieRepository.findById(id).orElseThrow(() -> new
	 * IllegalArgumentException("Invalid movie Id:" + id));
	 * model.addAttribute("actors", actorRepository.findAll());
	 * model.addAttribute("directors", directorRepository.findAll());
	 * model.addAttribute("genres", genreRepository.findAll());
	 * model.addAttribute("movie", movie); return "/admin/movie/update"; }
	 */
    
	@GetMapping("movie/{id}")
	public String showMovie(@PathVariable("id") int id, Model model ) {
		try {
			Response<Movie> response = moviesService
					.summary(id, "en")
					.execute();
			Response<Videos> responseVideos = moviesService
					.videos(id, "en")
					.execute();
			Response<Credits> responseCredits = moviesService
					.credits(id)
					.execute();
			Response<MovieResultsPage> responseSimilar = moviesService
					.similar(id, 1, "es")
					.execute();
			if(response.isSuccessful() && responseVideos.isSuccessful() && responseCredits.isSuccessful()
					&& responseSimilar.isSuccessful()) {
				Movie movie = response.body();
				Videos video = responseVideos.body();
				MovieResultsPage similarMovies = responseSimilar.body();
				Credits credits = responseCredits.body();
				List<CastMember> cast = credits.cast;
				List<CrewMember> crew = credits.crew;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(movie.release_date);
				int releaseYear = calendar.get(Calendar.YEAR);
				model.addAttribute("similarMovies", similarMovies.results);
				model.addAttribute("cast", cast);
				model.addAttribute("crew", crew);
				model.addAttribute("videos", video.results);
				model.addAttribute("releaseYear", releaseYear);
				model.addAttribute("movie", movie);
			}
		}
		catch (Exception e) {
			
		}
		return "/movie";
	}
	
	
	@GetMapping("/")
	public String showNowPlaying(Model model) {
		try {
			Response<MovieResultsPage> response = moviesService
					.popular(1, "es", "DO")
					.execute();
			Response<MovieResultsPage> responseUpcoming = moviesService
					.nowPlaying(1, "es", "US")
					.execute();
			Response<MovieResultsPage> responseProximamente = moviesService
					.upcoming(1, "es", "US")
					.execute();
			if(response.isSuccessful() && responseUpcoming.isSuccessful() && responseProximamente.isSuccessful()) {
				MovieResultsPage movies = response.body();
				MovieResultsPage moviesUpcoming = responseUpcoming.body();
				MovieResultsPage moviesProximamente = responseProximamente.body();
				model.addAttribute("proximamenteMovies", moviesProximamente.results);
				model.addAttribute("upcomingMovies", moviesUpcoming.results);
				model.addAttribute("movies", movies.results);
			}
		}		
		catch(Exception e) {
			
		}
		return "home";
	}
	
	/*
	 * @PostMapping("admin/movie/update/{id}") public String
	 * updateMovie(@PathVariable("id") long id, @Valid Movie movie, BindingResult
	 * result, Model model) { if (result.hasErrors()) { movie.setId(id); return
	 * "/admin/movie/update"; }
	 * 
	 * movieRepository.save(movie); model.addAttribute("movies",
	 * movieRepository.findAll()); return "redirect:/admin/movie"; }
	 * 
	 * @GetMapping("admin/movie/delete/{id}") public String
	 * deleteMovie(@PathVariable("id") long id, Model model) { Movie movie =
	 * movieRepository.findById(id).orElseThrow(() -> new
	 * IllegalArgumentException("Invalid movie Id:" + id));
	 * movieRepository.delete(movie); model.addAttribute("movies",
	 * movieRepository.findAll()); return "redirect:/admin/movie"; }
	 */

}
