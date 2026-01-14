package com.ecommpay.sdk.model.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
public class Booker {
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    public String getFirstName() {
        return firstName;
    }

    public Booker setFirstName(String first_name) {
        this.firstName = first_name;

        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Booker setLastName(String last_name) {
        this.lastName = last_name;

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

