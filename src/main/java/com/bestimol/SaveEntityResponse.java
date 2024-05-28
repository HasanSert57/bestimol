package com.bestimol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveEntityResponse {
    private boolean success;
    private String message;
    private Long id;
    private String errorDetails;
}