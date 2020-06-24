package com.unesco.core.entities.certification;

import com.unesco.core.entities.account.StudentEntity;

import javax.persistence.*;

@Entity
@Table(name="un_current_certification_value")
public class CurrentCertificationValueEntity {

    @Id
    @SequenceGenerator(name = "certificationValueSequenceGen", sequenceName = "certificationValueSequenceGen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "certificationValueSequenceGen")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "current_certification_id")
    private CurrentCertificationEntity currentCertification;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEntity student;

    @Column(name ="certification_value")
    private int certificationValue;

    @Column(name ="missed_academic_hours")
    private int missedAcademicHours;

    public CurrentCertificationEntity getCurrentCertification() {
        return currentCertification;
    }

    public void setCurrentCertification(CurrentCertificationEntity currentCertification) {
        this.currentCertification = currentCertification;
    }

    public long getId() {
        return id;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public int getCertificationValue() {
        return certificationValue;
    }

    public int getMissedAcademicHours() {
        return missedAcademicHours;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public void setCertificationValue(int certificationValue) {
        this.certificationValue = certificationValue;
    }

    public void setMissedAcademicHours(int missedAcademicHours) {
        this.missedAcademicHours = missedAcademicHours;
    }
}
