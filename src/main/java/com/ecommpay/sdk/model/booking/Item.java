package com.ecommpay.sdk.model.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
public class Item {
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonProperty("end_date")
    private LocalDate endDate;

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;

        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Item setStartDate(LocalDate start_date) {
        this.startDate = start_date;

        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Item setEndDate(LocalDate end_date) {
        this.endDate = end_date;

        return this;
    }
}

