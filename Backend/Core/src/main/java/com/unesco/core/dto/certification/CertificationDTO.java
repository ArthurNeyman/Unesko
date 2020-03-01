package com.unesco.core.dto.certification;

import com.unesco.core.dto.plan.EducationPeriodDTO;
import com.unesco.core.dto.shedule.GroupDTO;
import com.unesco.core.dto.shedule.LessonDTO;

import java.util.Date;
import java.util.List;

public class CertificationDTO {

    private long id;
    private Date startDate;
    private Date endDate;
    private LessonDTO lesson;
    private List<CertificationValueDTO> certificationValueDTOList;

    public CertificationDTO() {
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

    public List<CertificationValueDTO> getCertificationValueDTOList() {
        return certificationValueDTOList;
    }

    public void setCertificationValueDTOList(List<CertificationValueDTO> certificationValueDTOList) {
        this.certificationValueDTOList = certificationValueDTOList;
    }
}
