package com.unesco.core.dto.studentInterface;

public class EventPairDTO {
    public long idEvent;
    public String titleEvent;
    public float maxValue;
    public float currentValue;

    public EventPairDTO() {
    }

    public EventPairDTO(long idEvent, String titleEvent,  float maxValue, float currentValue) {
        this.idEvent = idEvent;
        this.titleEvent = titleEvent;
        this.maxValue = maxValue;
        this.currentValue = currentValue;
    }
}
