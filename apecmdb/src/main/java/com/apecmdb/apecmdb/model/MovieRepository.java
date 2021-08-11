package com.apecmdb.apecmdb.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
	
	List<Movie> findByTitle(String title);
	List<Movie> findByDirector(String director); 
	

}
