package com.unesco.core.dto.certification;

import com.unesco.core.dto.account.StudentDTO;

public  class ReportElementValue{
    private StudentDTO student;
    private int visitationValue;
    private int eventValue;

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public int getEventValue() {
        return eventValue;
    }

    public int getVisitationValue() {
        return visitationValue;
    }

    public void setVisitationValue(int visitationValue) {
        this.visitationValue = visitationValue;
    }

    public void setEventValue(int eventValue) {
        this.eventValue = eventValue;
    }
}

