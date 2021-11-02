package com.example.demo.serviceImpl;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.seqgenerator.DataSequence;
import com.example.demo.service.SequenceGenerator;

@Service
public class SequenceGeneratorImpl implements SequenceGenerator {

	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public int getSequenceNumber(String sequenceName) {
		// get sequence no
		Query query = new Query(Criteria.where("id").is(sequenceName));
		// update the sequence
		Update update = new Update().inc("seq", 1);

		DataSequence counter = mongoOperations.findAndModify(query, update, options().returnNew(true).upsert(true),
				DataSequence.class);

		return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}

}
