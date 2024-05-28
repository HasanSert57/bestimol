package com.bestimol.dto.request.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCreateRequestDTO {
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
