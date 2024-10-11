package com.event.mgmt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.event.mgmt.entity.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
	Page<Event> findByEvtNameContainingIgnoreCase(String name, Pageable pageable);
	
	Page<Event> findByEvtDateContainingIgnoreCase(String date, Pageable pageable);
}

