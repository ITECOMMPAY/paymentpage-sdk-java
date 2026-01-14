package com.ecommpay.sdk.model.booking;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Booker {
    private String first_name;
    private String last_name;
    private String email;

    public String getFirst_name() {
        return first_name;
    }

    public Booker setFirst_name(String first_name) {
        this.first_name = first_name;

        return this;
    }

    public String getLast_name() {
        return last_name;
    }

    public Booker setLast_name(String last_name) {
        this.last_name = last_name;

        return this;
    }

    public String getEmail() {
        return email;
    }

    public Booker setEmail(String email) {
        this.email = email;

        return this;
    }
}

