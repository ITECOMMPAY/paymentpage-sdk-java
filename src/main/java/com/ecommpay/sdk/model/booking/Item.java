package com.ecommpay.sdk.model.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item {
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate start_date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate end_date;

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;

        return this;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public Item setStart_date(LocalDate start_date) {
        this.start_date = start_date;

        return this;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public Item setEnd_date(LocalDate end_date) {
        this.end_date = end_date;

        return this;
    }
}

