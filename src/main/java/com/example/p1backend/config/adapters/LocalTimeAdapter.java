package com.example.p1backend.config.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public LocalTime unmarshal(String v) {
        return v != null ? LocalTime.parse(v, formatter) : null;
    }

    @Override
    public String marshal(LocalTime v) {
        return v != null ? formatter.format(v) : null;
    }
}