package com.unesco.core.dto.certification;

import com.unesco.core.dto.shedule.LessonDTO;

import java.util.List;

public  class ReportElement{
    private LessonDTO lesson;
    private List<ReportElementValue> infoList;
    private int maxVisitationValue;
    private int maxEventValue;

    public LessonDTO getLessonDTO() {
        return lesson;
    }

    public void setLesson(LessonDTO lesson) {
        this.lesson = lesson;
    }

    public List<ReportElementValue> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<ReportElementValue> infoList) {
        this.infoList = infoList;
    }

    public int getMaxVisitationValue() {
        return maxVisitationValue;
    }

    public void setMaxVisitationValue(int maxVisitationValue) {
        this.maxVisitationValue = maxVisitationValue;
    }

    public int getMaxEventValue() {
        return maxEventValue;
    }

    public void setMaxEventValue(int maxEventValue) {
        this.maxEventValue = maxEventValue;
    }
}