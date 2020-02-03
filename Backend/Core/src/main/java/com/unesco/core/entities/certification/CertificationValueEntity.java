package com.unesco.core.entities.certification;

import com.unesco.core.entities.account.StudentEntity;

import javax.persistence.*;

@Entity
@Table(name="un_certification_value")
public class CertificationValueEntity {
    @Id
    @SequenceGenerator(name = "certificationValueSequenceGen", sequenceName = "certificationValueSequenceGen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "certificationValueSequenceGen")
    private long id;

    @Column(name = "certification_id")
    private long certificationId;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEntity student;

    @Column(name ="certification_value")
    private int certificationValue;

    @Column(name ="missed_academic_hours")
    private int missedAcademicHours;

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

    public long getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(long certificationId) {
        this.certificationId = certificationId;
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
