package com.doodle.pollservice.repository;

import com.doodle.pollservice.domain.Poll;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface PollRepository extends MongoRepository<Poll, String> {

    List<Poll> findByCreatedOnAfter(Date createdOn);

    List<Poll> findAllByUserId(UUID userId);

}
