package com.unesco.core.entities.certification;

import com.unesco.core.dto.certification.CertificationValueDTO;
import com.unesco.core.entities.plan.EducationPeriodEntity;
import com.unesco.core.entities.schedule.GroupEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="un_certification")
public class CertificationEntity {
    @Id
    @SequenceGenerator(name = "pointSequenceGen", sequenceName = "pointSequenceGen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pointSequenceGen")
    private long id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private GroupEntity group;

    @ManyToOne
    @JoinColumn(name = "education_period_id", referencedColumnName = "id")
    private EducationPeriodEntity educationPeriod;

    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "certificationId")
    private List<CertificationValueEntity> certificationValueList;

    public long getId() {
        return id;
    }

    public void setCertificationValueList(List<CertificationValueEntity> certificationValueList) {
        this.certificationValueList = certificationValueList;
    }

    public List<CertificationValueEntity> getCertificationValueList() {
        return certificationValueList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public EducationPeriodEntity getEducationPeriod() {
        return educationPeriod;
    }

    public void setEducationPeriod(EducationPeriodEntity educationPeriod) {
        this.educationPeriod = educationPeriod;
    }
}
