package com.unesco.core.dto.certification;

import com.unesco.core.dto.journal.LessonEventDTO;

public class LessonEventValueDTO {

    LessonEventDTO event;
    Integer value;

    public LessonEventValueDTO(LessonEventDTO event, Integer value) {
        this.event = event;
        this.value = value;
    }

    public LessonEventDTO getEvent() {
        return event;
    }

    public void setEvent(LessonEventDTO event) {
        this.event = event;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
