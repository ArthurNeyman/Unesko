package com.unesco.core.dto.studentInterface;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unesco.core.entities.journal.PointEntity;

import java.util.Date;

public class ArchivePointDTO {

    int value;
    String date;
    String typePointTitle;
    String roomTitle;
    String dayOfweek;
    String lessonTitle;
    String typePairTitle;


    public ArchivePointDTO(int value, String date, String typePointTitle, String roomTitle, String dayOfweek, String lessonTitle, String typePairTitle) {
        this.value = value;
        this.date = date;
        this.typePointTitle = typePointTitle;
        this.roomTitle = roomTitle;
        this.dayOfweek = dayOfweek;
        this.lessonTitle = lessonTitle;
        this.typePairTitle = typePairTitle;
    }

    public ArchivePointDTO() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypePointTitle() {
        return typePointTitle;
    }

    public void setTypePointTitle(String typePointTitle) {
        this.typePointTitle = typePointTitle;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getDayOfweek() {
        return dayOfweek;
    }

    public void setDayOfweek(String dayOfweek) {
        this.dayOfweek = dayOfweek;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getTypePairTitle() {
        return typePairTitle;
    }

    public void setTypePairTitle(String typePairTitle) {
        this.typePairTitle = typePairTitle;
    }
}
