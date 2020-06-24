package com.unesco.core.dto.certification;

import com.unesco.core.dto.account.StudentDTO;

public class CurrentCertificationValueDTO {

    private long id;
    private StudentDTO student;
    private int certificationValue;
    private int missedAcademicHours;
    private long certification_id;

    public long getCertification_id() {
        return certification_id;
    }

    public void setCertification_id(long certification_id) {
        this.certification_id = certification_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
