package com.unesco.core.dto.journal;

import com.unesco.core.dto.account.StudentDTO;

import java.util.List;
import java.util.stream.IntStream;

public class CertificationStudentDto {

    /* Студент */
    private StudentDTO student;
    /* Атестация */
    private double value;
    /* Баллы за посещения */
    private double visitationValue;
    /* Пропущено часов */
    private double missingHours;
    /* События */
    private List<CertificationEventDTO> eventValue;
    /*Аттестационнаый балл*/
    private int certificationValue;

    public StudentDTO getStudent() {
        return student;
    }
    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }

    public double getMissingHours() {
        return missingHours;
    }
    public void setMissingHours(double missingHours) {
        this.missingHours = missingHours;
    }

    public double getVisitationValue() {
        return visitationValue;
    }
    public void setVisitationValue(double visitationValue) {
        this.visitationValue = visitationValue;
    }

    public List<CertificationEventDTO> getEventValue() {
        return eventValue;
    }
    public void setEventValue(List<CertificationEventDTO> eventValue) {
        this.eventValue = eventValue;
    }

    public void setCertificationValue(int certificationValue) {
        this.certificationValue = certificationValue;
    }
    public int getCertificationValue() {
        return certificationValue;
    }

    public void setCertificationValueByMaxValue(int maxEventPointValue){
        double sum=0;

        for(CertificationEventDTO certificationEventDTO:this.eventValue)
            sum+=certificationEventDTO.getValue();

        if(maxEventPointValue==0) {
            setCertificationValue(0);
            return;
        }

        if(sum/maxEventPointValue < 0.3)
            this.setCertificationValue(0);
        else if(sum/maxEventPointValue <0.6)
            this.setCertificationValue(1);
        else this.setCertificationValue(2);
    }

    public void setCertificationValueByVisitation(){
        if(visitationValue/(visitationValue+missingHours)< 0.3)
            this.setCertificationValue(0);
        else if(visitationValue/(visitationValue+missingHours) <0.6)
            this.setCertificationValue(1);
        else this.setCertificationValue(2);
    }
}
