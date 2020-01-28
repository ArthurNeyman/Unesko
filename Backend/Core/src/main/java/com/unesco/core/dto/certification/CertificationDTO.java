package com.unesco.core.dto.certification;

import com.unesco.core.dto.plan.EducationPeriodDTO;
import com.unesco.core.dto.shedule.GroupDTO;
import com.unesco.core.dto.shedule.LessonDTO;

import java.util.Date;
import java.util.List;

public class CertificationDTO {


    private long id;

    public long getId() {
        return id;
    }

    private Date startDate;

    public CertificationDTO() {
    }

    public CertificationDTO(long id, Date startDate, Date endDate, LessonDTO lesson, List<CertificationValueDTO> certificationValueDTOList) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lesson = lesson;
        this.certificationValueDTOList = certificationValueDTOList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<CertificationValueDTO> getCertificationValueDTOList() {
        return certificationValueDTOList;
    }

    public LessonDTO getLesson() {
        return lesson;
    }

    private Date endDate;
    private LessonDTO lesson;
    private List<CertificationValueDTO> certificationValueDTOList;
}
