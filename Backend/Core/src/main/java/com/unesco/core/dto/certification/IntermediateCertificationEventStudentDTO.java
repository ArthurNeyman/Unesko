package com.unesco.core.dto.certification;

public class IntermediateCertificationEventStudentDTO extends IntermediateCertificationEventDTO {

    private int currentValue;

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public IntermediateCertificationEventStudentDTO(String name, String type, int value, int currentValue) {
        super(name, type, value);
        this.currentValue=currentValue;
    }

}
