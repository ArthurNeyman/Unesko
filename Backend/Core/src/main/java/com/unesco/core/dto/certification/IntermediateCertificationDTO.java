package com.unesco.core.dto.certification;

import com.unesco.core.dto.shedule.LessonDTO;

import java.util.List;

public class IntermediateCertificationDTO {

    private  long id;
    private LessonDTO lesson;
    private IntermediateCertificationTypeDTO lessonCertificationType;
    private int maxCertificationScore;
    private List<IntermediateCertificationEventDTO> events;
    private List<IntermediateCertificationResultDTO> studList;

    public List<IntermediateCertificationEventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<IntermediateCertificationEventDTO> events) {
        this.events = events;
    }

    public List<IntermediateCertificationResultDTO> getStudList() {
        return studList;
    }

    public void setStudList(List<IntermediateCertificationResultDTO> studList) {
        this.studList = studList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LessonDTO getLesson() {
        return lesson;
    }

    public void setLesson(LessonDTO lesson) {
        this.lesson = lesson;
    }

    public IntermediateCertificationTypeDTO getLessonCertificationType() {
        return lessonCertificationType;
    }

    public void setLessonCertificationType(IntermediateCertificationTypeDTO lessonCertificationType) {
        this.lessonCertificationType = lessonCertificationType;
    }

    public int getMaxCertificationScore() {
        return maxCertificationScore;
    }

    public void setMaxCertificationScore(int maxCertificationScore) {
        this.maxCertificationScore = maxCertificationScore;
    }
}
