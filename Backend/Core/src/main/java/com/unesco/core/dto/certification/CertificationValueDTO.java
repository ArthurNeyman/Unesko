package com.unesco.core.dto.certification;

import com.unesco.core.dto.account.StudentDTO;

public class CertificationValueDTO {

    private long id;
    private long certificationId;
    private StudentDTO student;
    private int certificationValue;
    private int missedAcademicHours;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getCertificationId() {
        return certificationId;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public void setCertificationValue(int certificationValue) {
        this.certificationValue = certificationValue;
    }

    public void setMissedAcademicHours(int missedAcademicHours) {
        this.missedAcademicHours = missedAcademicHours;
    }

    public void setCertificationId(long certificationId) {
        this.certificationId = certificationId;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public int getCertificationValue() {
        return certificationValue;
    }

    public int getMissedAcademicHours() {
        return missedAcademicHours;
    }
}
