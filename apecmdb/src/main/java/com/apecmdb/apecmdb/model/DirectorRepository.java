package com.apecmdb.apecmdb.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends CrudRepository<Director, Long> {
	List<Director> findByDirectorName(String directorName);

}
