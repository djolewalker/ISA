package com.ftnisa.isa.exception;

import org.json.simple.JSONObject;

public class ORSException extends RuntimeException {
    private final JSONObject orsError;


    public ORSException(JSONObject orsError) {
        this.orsError = orsError;
    }

    public JSONObject getOrsError() {
        return orsError;
    }
}
