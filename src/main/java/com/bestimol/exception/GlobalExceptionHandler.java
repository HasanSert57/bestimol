package com.bestimol.exception;

import com.bestimol.SaveEntityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final ZoneId TURKEY_ZONE = ZoneId.of("Europe/Istanbul");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<SaveEntityResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        SaveEntityResponse response = new SaveEntityResponse(false, "İşlem başarısız", null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SaveEntityResponse> handleGenericException(Exception ex) {
        SaveEntityResponse response = new SaveEntityResponse(false, "İşlem başarısız", null, "Beklenmeyen bir hata oluştu");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<SaveEntityResponse> handleAuthenticationException(AuthenticationServiceException ex) {
        String formattedMessage = formatMessage(ex.getMessage());
        SaveEntityResponse response = new SaveEntityResponse(false, "Erişim yetkiniz yok", null, formattedMessage);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(io.jsonwebtoken.JwtException.class)
    public ResponseEntity<SaveEntityResponse> handleJwtException(io.jsonwebtoken.JwtException ex) {
        String formattedMessage = formatMessage(ex.getMessage());
        SaveEntityResponse response = new SaveEntityResponse(false, "Erişim yetkiniz yok", null, formattedMessage);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    private String formatMessage(String message) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z");
        Matcher matcher = pattern.matcher(message);
        StringBuffer formattedMessage = new StringBuffer();

        while (matcher.find()) {
            String dateStr = matcher.group();
            LocalDateTime utcDate = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
            LocalDateTime trDate = utcDate.atZone(ZoneId.of("UTC")).withZoneSameInstant(TURKEY_ZONE).toLocalDateTime();
            matcher.appendReplacement(formattedMessage, trDate.format(FORMATTER));
        }
        matcher.appendTail(formattedMessage);
        return formattedMessage.toString();
    }
}