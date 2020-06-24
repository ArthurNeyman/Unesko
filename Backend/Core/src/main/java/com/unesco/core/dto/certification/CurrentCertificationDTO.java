package com.unesco.core.dto.certification;

import com.unesco.core.dto.shedule.LessonDTO;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class CurrentCertificationDTO {

    private long id;
    private Date startDate;
    private Date endDate;
    private LessonDTO lesson;
    private List<CurrentCertificationValueDTO> currentCertificationValueDTOList;

    public CurrentCertificationDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public LessonDTO getLesson() {
        return lesson;
    }

    public void setLesson(LessonDTO lesson) {
        this.lesson = lesson;
    }

    public List<CurrentCertificationValueDTO> getCurrentCertificationValueDTOList() {
        return this.currentCertificationValueDTOList;
    }

    public void setCurrentCertificationValueDTOList(List<CurrentCertificationValueDTO> currentCertificationValueDTOList) {
        this.currentCertificationValueDTOList = currentCertificationValueDTOList;
    }
}
