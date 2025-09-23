package com.ensak.emploi.model;

import lombok.Getter;

@Getter
public class IdTokenRequest {

    private String idToken;

    public IdTokenRequest() {
    }

    public IdTokenRequest(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

}
