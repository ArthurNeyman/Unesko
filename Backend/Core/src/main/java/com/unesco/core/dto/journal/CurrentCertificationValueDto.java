package com.unesco.core.dto.journal;

import com.unesco.core.dto.account.StudentDTO;

import java.util.List;
import java.util.stream.IntStream;

public class CurrentCertificationValueDto {

    private StudentDTO student;
    private double missingHours;
    private int certificationValue;

    public StudentDTO getStudent() {
        return student;
    }
    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public double getMissingHours() {
        return missingHours;
    }
    public void setMissingHours(double missingHours) {
        this.missingHours = missingHours;
    }

    public void setCertificationValue(int certificationValue) {
        this.certificationValue = certificationValue;
    }
    public int getCertificationValue() {
        return certificationValue;
    }

}
