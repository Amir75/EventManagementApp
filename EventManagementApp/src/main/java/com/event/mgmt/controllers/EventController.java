package com.event.mgmt.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.event.mgmt.dto.EventDTO;
import com.event.mgmt.entity.Event;
import com.event.mgmt.services.EventService;

@RestController
@RequestMapping("/events")
public class EventController {
	@Autowired
	private EventService eventService;

	 @GetMapping("getevents")
	public ResponseEntity<Page<Event>> getEvents(Pageable pageable) {
		return ResponseEntity.ok(eventService.getAllEvents(pageable));
	}

	@PostMapping("addevent")
	public ResponseEntity<Event> addEvent(@ModelAttribute EventDTO eventDTO) {

		try {
			Event event = eventService.addEvent(eventDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(event);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/searchname/{name}")
	public ResponseEntity<Page<Event>> searchByEventName(@PathVariable String name,
			@PageableDefault(size = 10, page = 0) Pageable pageable) {
		Page<Event> events = eventService.getEventsByName(name, pageable);
		return ResponseEntity.ok(events);
	}
	
	@GetMapping("/searchdate/{date}")
	public ResponseEntity<Page<Event>> searchByEventDate(@PathVariable String date,
			@PageableDefault(size = 10, page = 0) Pageable pageable) {
		Page<Event> events = eventService.getEventsByDate(date, pageable);
		return ResponseEntity.ok(events);
	}

	@PutMapping("editevent/{id}")
	public ResponseEntity<Event> updateEvent(@PathVariable Long id, @ModelAttribute EventDTO eventdto) {
		return eventService.updateEvent(id, eventdto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
		eventService.deleteEvent(id);
		return ResponseEntity.noContent().build();
	}
}
