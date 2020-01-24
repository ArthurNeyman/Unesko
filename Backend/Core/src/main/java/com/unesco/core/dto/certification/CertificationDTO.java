package com.unesco.core.dto.certification;

import com.unesco.core.dto.plan.EducationPeriodDTO;
import com.unesco.core.dto.shedule.GroupDTO;

import java.util.Date;
import java.util.List;

public class CertificationDTO {


    private long id;

    public long getId() {
        return id;
    }

    private Date startDate;

    public CertificationDTO(long id, Date startDate, Date endDate, GroupDTO group, EducationPeriodDTO educationPeriod, List<CertificationValueDTO> certificationValueDTOList) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.group = group;
        this.educationPeriod = educationPeriod;
        this.certificationValueDTOList = certificationValueDTOList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public EducationPeriodDTO getEducationPeriod() {
        return educationPeriod;
    }

    public List<CertificationValueDTO> getCertificationValueDTOList() {
        return certificationValueDTOList;
    }

    private Date endDate;
    private GroupDTO group;
    private EducationPeriodDTO educationPeriod;
    private List<CertificationValueDTO> certificationValueDTOList;
}
