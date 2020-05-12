package com.unesco.core.dto.certification;

import com.unesco.core.dto.account.StudentDTO;

import java.util.Date;

public class LessonCertificationResultDTO {

    private long id;
    private long lessonCertificationId;
    private StudentDTO studentDTO;
    private int certificationScore;
    private float totalScore;
    private boolean absence;
    private Date ratingDate;
    private int currentScore;

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
