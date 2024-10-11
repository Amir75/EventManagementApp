package com.event.mgmt.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventDTO {
	    private String evtName;
	    private String evtDate;
	    private String evtType;
	    private MultipartFile evtImage;   
	    private MultipartFile evtVideo;   
	    private MultipartFile evtAttendeeList;   
	    private String evtLink;
}
