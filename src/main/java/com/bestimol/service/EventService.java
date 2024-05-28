package com.bestimol.service;

import com.bestimol.dto.request.event.EventCreateRequestDTO;
import com.bestimol.dto.request.event.EventUpdateRequestDTO;
import com.bestimol.model.Category;
import com.bestimol.model.City;
import com.bestimol.model.District;
import com.bestimol.model.Event;
import com.bestimol.repository.CategoryRepository;
import com.bestimol.repository.CityRepository;
import com.bestimol.repository.DistrictRepository;
import com.bestimol.SaveEntityResponse;
import com.bestimol.dto.response.event.EventResponseDTO;
import com.bestimol.exception.ResourceNotFoundException;
import com.bestimol.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    public SaveEntityResponse createEvent(EventCreateRequestDTO eventDTO) {
        SaveEntityResponse response = new SaveEntityResponse();
        try {
            validateEventCreateDTO(eventDTO);
            Event event = mapEventFromDTO(new Event(), eventDTO);
            eventRepository.save(event);
            response.setSuccess(true);
            response.setMessage("Event created successfully");
            response.setId(event.getId());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    private Event mapEventFromDTO(Event event, EventCreateRequestDTO eventDTO) {
        event.setEventName(eventDTO.getEventName());
        event.setParticipantCount(eventDTO.getParticipantCount());
        event.setEventDate(eventDTO.getEventDate());
        event.setLocationDetail(eventDTO.getLocationDetail());
        event.setDescription(eventDTO.getDescription());
        Optional<Category> category = categoryRepository.findById(eventDTO.getCategoryId());
        if (category.isPresent()) {
            Optional<Category> subCategory = categoryRepository.findById(eventDTO.getSubCategoryId());
            if (subCategory.isPresent()) {
                event.setSubCategory(subCategory.get());
                event.setCategory(category.get());
            }
        }

        Optional<City> city = cityRepository.findById(eventDTO.getCityId());
        city.ifPresent(event::setCity);

        Optional<District> district = districtRepository.findById(eventDTO.getDistrictId());
        district.ifPresent(event::setDistrict);

        return event;
    }


    private void validateEventCreateDTO(EventCreateRequestDTO eventDTO) {
        Optional<Category> category = categoryRepository.findById(eventDTO.getCategoryId());

        if (category.isPresent()) {
            Optional<Category> subCategory = categoryRepository.findById(eventDTO.getSubCategoryId());
            if (subCategory.isEmpty()) {
                throw new ResourceNotFoundException("SubCategory not found with id: " + eventDTO.getSubCategoryId());
            }
        } else {
            throw new ResourceNotFoundException("Category not found with id: " + eventDTO.getCategoryId());
        }

        Optional<City> city = cityRepository.findById(eventDTO.getCityId());
        if (city.isEmpty()) {
            throw new ResourceNotFoundException("City not found with id: " + eventDTO.getCityId());
        }

        Optional<District> district = districtRepository.findById(eventDTO.getDistrictId());
        if (district.isEmpty()) {
            throw new ResourceNotFoundException("District not found with id: " + eventDTO.getDistrictId());
        }
    }


    public List<EventResponseDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private EventResponseDTO convertToDTO(Event event) {
        EventResponseDTO eventDTO = new EventResponseDTO();
        eventDTO.setId(event.getId());
        eventDTO.setEventName(event.getEventName());
        eventDTO.setParticipantCount(event.getParticipantCount());
        eventDTO.setEventDate(event.getEventDate());
        eventDTO.setLocationDetail(event.getLocationDetail());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setCategoryId(event.getCategory().getId());
        eventDTO.setSubCategoryId(event.getSubCategory().getId());
        eventDTO.setCityId(event.getCity().getId());
        eventDTO.setDistrictId(event.getDistrict().getId());
        return eventDTO;
    }

    public EventResponseDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        return convertToDTO(event);
    }

    public SaveEntityResponse updateEvent(EventUpdateRequestDTO updateEventRequest) {
        SaveEntityResponse response = new SaveEntityResponse();
        try {
            Event event = eventRepository.findById(updateEventRequest.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + updateEventRequest.getId()));
            validateEventCreateDTO(updateEventRequest);
            eventRepository.save(mapEventFromDTO(event, updateEventRequest));

            response.setSuccess(true);
            response.setMessage("Event updated successfully");
            response.setId(updateEventRequest.getId());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    public void deleteEvent(Long id) {
        eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        eventRepository.deleteById(id);
    }
}