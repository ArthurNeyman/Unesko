package com.unesco.core.dto.certification;

import com.unesco.core.dto.account.StudentDTO;

import java.util.Date;
import java.util.List;


public class IntermediateCertificationResultDTO {

    private long id;
    private long lessonCertificationId;
    private StudentDTO studentDTO;
    private int certificationScore;//аттестационный балл
    private float totalScore;//общий балл
    private boolean absence;//неяка
    private Date ratingDate;//дата
    private int currentScore;//текущий балл
    private String Mark;
    private List<IntermediateCertificationEventDTO> events;

    public List<IntermediateCertificationEventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<IntermediateCertificationEventDTO> events) {
        this.events = events;
    }

    public String getMark() {
        return Mark;
    }

    public void setMark(String mark) {
        Mark = mark;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLessonCertificationId() {
        return lessonCertificationId;
    }

    public void setLessonCertificationId(long lessonCertificationId) {
        this.lessonCertificationId = lessonCertificationId;
    }

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    public int getCertificationScore() {
        return certificationScore;
    }

    public void setCertificationScore(int certificationScore) {
        this.certificationScore = certificationScore;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isAbsence() {
        return absence;
    }

    public void setAbsence(boolean absence) {
        this.absence = absence;
    }

    public Date getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(Date ratingDate) {
        this.ratingDate = ratingDate;
    }
}
