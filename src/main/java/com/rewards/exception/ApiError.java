package com.rewards.exception;

import java.time.OffsetDateTime;
import java.util.List;

public class ApiError {
    private String error;
    private String message;
    private int status;
    private String path;
    private OffsetDateTime timestamp = OffsetDateTime.now();
    private List<FieldError> fieldErrors;

    public static class FieldError {
        private String field;
        private String issue;
        public FieldError() {}
        public FieldError(String field, String issue) { 
        	this.field = field; 
        	this.issue = issue; 
        	}
        public String getField() { 
        	return field; 
        	}
        public String getIssue() { 
        	return issue; 
        	}
    }

    public ApiError() {}

    public ApiError(String error, String message, int status, String path, List<FieldError> fieldErrors) {
        this.error = error; this.message = message; this.status = status; this.path = path; this.fieldErrors = fieldErrors;
    }

    public String getError() { 
    	return error; 
    	}
    public String getMessage() { 
    	return message; 
    	}
    public int getStatus() { 
    	return status; 
    	}
    public String getPath() { 
    	return path; 
    	}
    public OffsetDateTime getTimestamp() { 
    	return timestamp; 
    	}
    public List<FieldError> getFieldErrors() { 
    	return fieldErrors; 
    	}
}
