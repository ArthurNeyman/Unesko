package com.unesco.core.entities.certification;

import com.unesco.core.entities.schedule.LessonEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="un_certification")
public class CertificationEntity {
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

    public CertificationEntity() {
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
}
