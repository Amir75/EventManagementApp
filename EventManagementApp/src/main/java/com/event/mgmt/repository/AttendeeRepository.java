package com.event.mgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.event.mgmt.entity.Attendee;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long> { 

}
