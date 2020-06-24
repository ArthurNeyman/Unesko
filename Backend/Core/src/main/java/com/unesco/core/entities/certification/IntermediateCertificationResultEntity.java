package com.unesco.core.entities.certification;

import com.unesco.core.entities.account.StudentEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="un_intermediate_certification_value")
public class IntermediateCertificationResultEntity {
    @Id
    @SequenceGenerator(name = "lessonCertificationResultSequenceGen", sequenceName = "lessonCertificationResultSequenceGen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessonCertificationResultSequenceGen")
    private long id;

    @Column(name="lesson_certification_id")
    private long lessonCertificationId;

    @ManyToOne
    @JoinColumn(name="student_id", referencedColumnName = "id")
    private StudentEntity studentEntity;

    @Column(name="certification_score")
    private int certificationScore;

    @Column(name="total_score")
    private float totalScore;

    private boolean absence;

    @Column(name="rating_date")
    private Date ratingDate;

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

    public StudentEntity getStudentEntity() {
        return studentEntity;
    }

    public void setStudentEntity(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
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
