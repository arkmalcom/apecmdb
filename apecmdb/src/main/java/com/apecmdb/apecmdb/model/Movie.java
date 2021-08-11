package com.apecmdb.apecmdb.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="movie")
public class Movie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "director_id", referencedColumnName = "id")
	private Director director;
	
	@ManyToOne
	@JoinColumn(name="genre_id", referencedColumnName="id")
	private Genre genre;
	
	private String title;
	private String trailer;
	private String image;
	@NotBlank
	private String description;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date releaseDate;
	
	public Movie() {
		
	}
	
	public Movie(String title, Director director, String trailer, String image, Date releaseDate, String description) {
		this.title = title;
		this.director = director;
		this.trailer = trailer;
		this.image = image;
		this.releaseDate = releaseDate;
		this.description = description;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}
	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	

}
