package com.unesco.core.dto.certification;


public class IntermediateCertificationEventDTO {

    protected String name;
    protected String type;
    protected int maxValue;

    public IntermediateCertificationEventDTO() {
    }

    public IntermediateCertificationEventDTO(String name, String type, int value) {
        this.name = name;
        this.type = type;
        this.maxValue = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int value) {
        this.maxValue = value;
    }
}
