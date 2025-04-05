package br.com.douglas.project.build_token.controller.exception;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.time.Instant;


public class ErrorResponse {
    private Instant timestamp;
    private String error;
    private String path;
    private Integer status;
    private String message;

    public ErrorResponse(Instant timestamp, String error, String message, Integer status, String path) {
        this.timestamp = timestamp;
        this.error = error;
        this.message = message;
        this.status = status;
        this.path = path;
    }

    public ErrorResponse(Instant now, int value, String error, String message, String requestURI) {
    }


    public Instant getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
