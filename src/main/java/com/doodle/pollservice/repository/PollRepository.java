package com.doodle.pollservice.repository;

import com.doodle.pollservice.domain.Poll;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PollRepository extends MongoRepository<Poll, String> {

    List<Poll> findByCreatedOnAfter(Date createdOn);

    List<Poll> findAllByInitiatorEmail(String email);

    List<Poll> findAllByTitleStartingWith(String title);

}
