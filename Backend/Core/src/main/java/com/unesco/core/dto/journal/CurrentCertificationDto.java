package com.unesco.core.dto.journal;

import com.unesco.core.dto.shedule.LessonDTO;

import java.util.List;

public class CurrentCertificationDto {

    private LessonDTO lesson;
    private List<CurrentCertificationValueDto> studentCertification;
    private double allHours;
    private int allEventValue;

    public void setAllEventValue(int allEventValue) {
        this.allEventValue = allEventValue;
    }
    public int getAllEventValue() {
        return allEventValue;
    }

    public LessonDTO getLesson() {
        return lesson;
    }
    public void setLesson(LessonDTO lesson) {
        this.lesson = lesson;
    }

    public List<CurrentCertificationValueDto> getStudentCertification() {
        return studentCertification;
    }
    public void setStudentCertification(List<CurrentCertificationValueDto> studentCertification) {
        this.studentCertification = studentCertification;
    }

    public double getAllHours() {
        return allHours;
    }
    public void setAllHours(double allHours) {
        this.allHours = allHours;
    }
}
