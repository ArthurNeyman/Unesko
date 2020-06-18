package com.unesco.core.dto.certification;

import com.unesco.core.dto.account.StudentDTO;

import java.util.List;

public class StudentEventDTO {

    private StudentDTO student;
    private List<LessonEventValueDTO> events;
    private Integer visitationValue;

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public List<LessonEventValueDTO> getEvents() {
        return events;
    }

    public void setEvents(List<LessonEventValueDTO> events) {
        this.events = events;
    }

    public Integer getVisitationValue() {
        return visitationValue;
    }

    public void setVisitationValue(Integer visitationValue) {
        this.visitationValue = visitationValue;
    }
}
