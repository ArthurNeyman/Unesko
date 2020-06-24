package com.unesco.core.entities.certification;

import com.unesco.core.entities.schedule.LessonEntity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="un_current_certification")
public class CurrentCertificationEntity {

    @Id
    @SequenceGenerator(name = "certificationSequenceGen", sequenceName = "certificationSequenceGen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "certificationSequenceGen")
    private long id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private LessonEntity lesson;

    @OneToMany(mappedBy = "currentCertification",cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    private List<CurrentCertificationValueEntity> currentCertificationValueEntities=new ArrayList<>();

    public CurrentCertificationEntity() {
    }

    public long getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
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

    public void setLesson(LessonEntity lesson) {
        this.lesson = lesson;
    }

    public LessonEntity getLesson() {
        return lesson;
    }

    public List<CurrentCertificationValueEntity> getCurrentCertificationValueEntities() {
        return currentCertificationValueEntities;
    }

    public void setCurrentCertificationValueEntities(List<CurrentCertificationValueEntity> currentCertificationValueEntities) {
        this.currentCertificationValueEntities = currentCertificationValueEntities;
    }
}
