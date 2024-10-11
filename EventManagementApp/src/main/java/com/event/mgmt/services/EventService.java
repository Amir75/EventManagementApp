package com.event.mgmt.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.event.mgmt.dto.EventDTO;
import com.event.mgmt.entity.Attendee;
import com.event.mgmt.entity.Event;
import com.event.mgmt.repository.AttendeeRepository;
import com.event.mgmt.repository.EventRepository;

import jakarta.validation.Valid;

@Service
public class EventService {
	private static final String UPLOAD_DIR = "uploads/";
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private AttendeeRepository attendeeRepository;

	@Cacheable(value = "events")
	public Page<Event> getAllEvents(Pageable pageable) {
		return eventRepository.findAll(pageable);
	}

	public Event addEvent(@Valid EventDTO eventDTO) throws IOException {
		// Create the uploads directory if it doesn't exist
		File directory = new File(UPLOAD_DIR);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		// Save files
		String evtImagePath = saveFile(eventDTO.getEvtImage());
		String evtVideoPath = saveFile(eventDTO.getEvtVideo());
		String evtAttendeeListPath = saveFile(eventDTO.getEvtAttendeeList());

		// Create an Event entity and save to the database
		Event event = new Event();
		event.setEvtName(eventDTO.getEvtName());
		event.setEvtDate(eventDTO.getEvtDate());
		event.setEvtType(eventDTO.getEvtType());
		event.setEvtImagePath(evtImagePath);
		event.setEvtVideoPath(evtVideoPath);
		event.setEvtAttendeeListPath(evtAttendeeListPath);
		event.setEvtLink(eventDTO.getEvtLink());

		//saveExcelData(eventDTO.getEvtAttendeeList());
		return eventRepository.save(event);
	}

	/*
	 * public void saveFromExcel(MultipartFile file) throws IOException { try
	 * (Workbook workbook = WorkbookFactory.create(file.getInputStream())) { Sheet
	 * sheet = workbook.getSheetAt(0); List<String> attendeeList = new
	 * ArrayList<String>(); for (Row row : sheet) {
	 * attendeeList.add(row.getCell(0).getStringCellValue());
	 * 
	 * //Event event = new Event();
	 * //event.setEvtName(row.getCell(0).getStringCellValue()); // Assuming name is
	 * in the first column //event.setEvtType("YourEventType"); // Set other
	 * properties as needed //eventRepository.save(event);
	 * 
	 * } System.out.println(attendeeList); } }
	 * 
	 */

	public List<Attendee> saveExcelData(MultipartFile file) {
		List<Attendee> attendees = new ArrayList<>();

		try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
			XSSFSheet sheet = workbook.getSheetAt(0);
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = sheet.getRow(i);
				Attendee att = new Attendee();
				att.setName(row.getCell(0).getStringCellValue());
				att.setEmail(row.getCell(1).getStringCellValue());
				att.setDepartment(row.getCell(2).getStringCellValue());
				attendees.add(att);
			}

			attendeeRepository.saveAll(attendees);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return attendees;
	}

	private String saveFile(MultipartFile file) throws IOException {
		if (file == null || file.isEmpty())
			return null;

		Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
		Files.write(path, file.getBytes());
		return path.toString();
	}

	public Optional<Event> updateEvent(Long id, EventDTO eventDetails) {
		return eventRepository.findById(id).map(event -> {
			// Save files
			String evtImagePath = null, evtVideoPath = null, evtAttendeeListPath = null;
			try {
				evtImagePath = saveFile(eventDetails.getEvtImage());
				evtVideoPath = saveFile(eventDetails.getEvtVideo());
				evtAttendeeListPath = saveFile(eventDetails.getEvtAttendeeList());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			event.setEvtName(eventDetails.getEvtName());
			event.setEvtDate(eventDetails.getEvtDate());
			event.setEvtType(eventDetails.getEvtType());
			event.setEvtImagePath(evtImagePath);
			event.setEvtVideoPath(evtVideoPath);
			event.setEvtAttendeeListPath(evtAttendeeListPath);
			event.setEvtLink(eventDetails.getEvtLink());
			// saveFromExcel(eventDTO.getEvtAttendeeList());
			return eventRepository.save(event);
		});
	}

	public void deleteEvent(Long id) {
		eventRepository.deleteById(id);
	}

	// @Cacheable("geteventsByName")
	public Page<Event> getEventsByName(String name, Pageable pageable) {
		if (name != null && !name.isEmpty()) {
			return eventRepository.findByEvtNameContainingIgnoreCase(name, pageable);
		}
		return eventRepository.findAll(pageable);
	}

	public Page<Event> getEventsByDate(String date, Pageable pageable) {
		if (date != null && !date.isEmpty()) {
			return eventRepository.findByEvtDateContainingIgnoreCase(date, pageable);
		}
		return eventRepository.findAll(pageable);
	}

}
