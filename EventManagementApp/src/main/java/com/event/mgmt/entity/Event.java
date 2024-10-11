package com.event.mgmt.entity;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Event {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Event name cannot be blank")
    private String evtName;
    
    @NotBlank
    private String evtDate;
    
    @NotBlank(message = "Event type cannot be blank")
    private String evtType;
    
    private String evtImagePath;
    private String evtVideoPath;
    
    @NotBlank
    private String evtAttendeeListPath;
    
    @URL(message = "Event link must be a valid URL")
    private String evtLink;


}
