package com.doodle.pollservice.repository;

import com.doodle.pollservice.domain.Poll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface PollRepository extends CrudRepository<Poll, UUID> {

    List<Poll> findByCreatedOnAfter(Date createdOn);
}
