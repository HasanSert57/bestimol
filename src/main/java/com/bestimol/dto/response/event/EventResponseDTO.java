package com.bestimol.dto.response.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventResponseDTO {
    private Long id;
    private String eventName;
    private Long categoryId;
    private Long subCategoryId;
    private Integer participantCount;
    private LocalDateTime eventDate;
    private Long cityId;
    private Long districtId;
    private String locationDetail;
    private String description;
}
