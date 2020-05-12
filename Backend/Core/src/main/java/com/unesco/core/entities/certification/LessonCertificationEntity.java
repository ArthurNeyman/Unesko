package com.unesco.core.entities.certification;

import com.unesco.core.entities.schedule.LessonEntity;

import javax.persistence.*;

@Entity
@Table(name="un_lesson_certification")
public class LessonCertificationEntity {

    @Id
        @SequenceGenerator(name = "lessonCertificationSequenceGen", sequenceName = "lessonCertificationSequenceGen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessonCertificationSequenceGen")
    private long id;

    @OneToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    LessonEntity lesson;

    @ManyToOne
    @JoinColumn(name="lesson_certification_type_id",referencedColumnName = "id")
    LessonCertificationTypeEntity lessonCertificationTypeEntity;

    @Column(name="max_certification_score")
    int maxCertificationScore;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LessonEntity getLesson() {
        return lesson;
    }

    public void setLesson(LessonEntity lesson) {
        this.lesson = lesson;
    }

    public LessonCertificationTypeEntity getLessonCertificationTypeEntity() {
        return lessonCertificationTypeEntity;
    }

    public void setLessonCertificationTypeEntity(LessonCertificationTypeEntity lessonCertificationTypeEntity) {
        this.lessonCertificationTypeEntity = lessonCertificationTypeEntity;
    }

    public int getMaxCertificationScore() {
        return maxCertificationScore;
    }

    public void setMaxCertificationScore(int maxCertificationScore) {
        this.maxCertificationScore = maxCertificationScore;
    }
}
