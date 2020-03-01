package com.unesco.core.dto.certification;

import com.unesco.core.dto.account.StudentDTO;

public class CertificationValueDTO {

    private long id;
    private long certification_id;
    private StudentDTO student;
    private int certificationValue;
    private int missedAcademicHours;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCertificationId() {
        return certification_id;
    }

    public void setCertificationId(long certificationId) {
        this.certification_id = certificationId;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public int getCertificationValue() {
        return certificationValue;
    }

    public void setCertificationValue(int certificationValue) {
        this.certificationValue = certificationValue;
    }

    public int getMissedAcademicHours() {
        return missedAcademicHours;
    }

    public void setMissedAcademicHours(int missedAcademicHours) {
        this.missedAcademicHours = missedAcademicHours;
    }
}
