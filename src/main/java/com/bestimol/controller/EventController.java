package com.bestimol.controller;

import com.bestimol.dto.request.event.EventCreateRequestDTO;
import com.bestimol.dto.request.event.EventUpdateRequestDTO;
import com.bestimol.service.EventService;
import com.bestimol.SaveEntityResponse;
import com.bestimol.dto.response.event.EventResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Create an event")
    public ResponseEntity<SaveEntityResponse> createEvent(@RequestBody EventCreateRequestDTO eventDTO) {
        SaveEntityResponse response = eventService.createEvent(eventDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all events")
    public List<EventResponseDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an event by id")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable Long id) {
        EventResponseDTO eventResponseDTO = eventService.getEventById(id);
        return ResponseEntity.ok(eventResponseDTO);
    }

    @PutMapping(consumes = "application/json")
    @Operation(summary = "Update an event")
    public ResponseEntity<SaveEntityResponse> updateEvent(@RequestBody EventUpdateRequestDTO eventUpdateRequestDTO) {
        SaveEntityResponse response = eventService.updateEvent(eventUpdateRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an event")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}