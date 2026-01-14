package com.ecommpay.sdk.model.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingInfo {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate start_date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate end_date;
    private String description;
    private Integer total;
    private Integer pax;
    private List<Booker> bookers;
    private List<Item> items;
    private String reference;
    private String id;

    public LocalDate getStart_date() {
        return start_date;
    }

    public BookingInfo setStart_date(LocalDate start_date) {
        this.start_date = start_date;

        return this;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public BookingInfo setEnd_date(LocalDate end_date) {
        this.end_date = end_date;

        return this;
    }

    public String getDescription() {
        return description;
    }

    public BookingInfo setDescription(String description) {
        this.description = description;

        return this;
    }

    public Integer getTotal() {
        return total;
    }

    public BookingInfo setTotal(Integer total) {
        this.total = total;

        return this;
    }

    public Integer getPax() {
        return pax;
    }

    public BookingInfo setPax(Integer pax) {
        this.pax = pax;

        return this;
    }

    public List<Booker> getBookers() {
        return bookers;
    }

    public BookingInfo setBookers(List<Booker> bookers) {
        this.bookers = bookers;

        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public BookingInfo setItems(List<Item> items) {
        this.items = items;

        return this;
    }

    public String getReference() {
        return reference;
    }

    public BookingInfo setReference(String reference) {
        this.reference = reference;

        return this;
    }

    public String getId() {
        return id;
    }

    public BookingInfo setId(String id) {
        this.id = id;

        return this;
    }
}

