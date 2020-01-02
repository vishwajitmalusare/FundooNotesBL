package com.bridgelabz.fundooApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundooApp.model.Label;
import java.lang.String;
import java.util.List;
import java.util.Optional;

public interface LabelRespository extends MongoRepository<Label, String>{

	List<Label> findByUserId(String userId);
	Optional<Label> findByLabelIdAndUserId(String labelId,String userId);
}
