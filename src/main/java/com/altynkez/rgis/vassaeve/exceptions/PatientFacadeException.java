package com.altynkez.rgis.vassaeve.exceptions;

import com.altynkez.rgis.vassaeve.ws.patients.XError;

/**
 *
 * @author vassaeve
 */
public class PatientFacadeException extends Exception {

    private static final long serialVersionUID = -1588889968354351139L;
    private final XError error;

    private PatientFacadeException() {
        error = null;
    }

    public PatientFacadeException(XError error) {
        this.error = error;
    }

    public XError getError() {
        return error;
    }

}
