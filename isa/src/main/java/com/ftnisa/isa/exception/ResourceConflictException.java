package com.ftnisa.isa.exception;

public class ResourceConflictException extends RuntimeException {
    private Integer resourceId;

    public ResourceConflictException(Integer resourceId, String message) {
        super(message);
        this.setResourceId(resourceId);
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
}

