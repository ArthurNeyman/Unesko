package com.unesco.core.dto.studentInterface;
import com.unesco.core.dto.shedule.LessonDTO;
import java.util.Map;

public class PerformanceDTO {
    public int maxValue;
    public int value;
    public LessonDTO lesson;
    public Map<Long, EventPairDTO> eventsPairWithPoints;

    public PerformanceDTO() {
    }

    public PerformanceDTO(int maxValue, int value,  LessonDTO lesson, Map<Long, EventPairDTO> eventsPairWithPoints) {
        this.maxValue = maxValue;
        this.value = value;
        this.lesson = lesson;
        this.eventsPairWithPoints = eventsPairWithPoints;
    }

}
