package com.unesco.core.dto.studentInterface;

import com.unesco.core.dto.shedule.LessonDTO;

import java.util.List;

public class PerformanceDTO {
    public int maxValue;
    public int value;
    public LessonDTO lesson;

    public PerformanceDTO() {
    }

    public PerformanceDTO(int maxValue, int value,  LessonDTO lesson) {
        this.maxValue = maxValue;
        this.value = value;
        this.lesson = lesson;
    }

}
