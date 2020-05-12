package com.unesco.core.dto.certification;

import com.unesco.core.dto.shedule.LessonDTO;

public class LessonCertificationDTO {

    private  long id;
    LessonDTO lesson;
    LessonCertificationTypeDTO lessonCertificationType;
    int maxCertificationScore;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LessonDTO getLesson() {
        return lesson;
    }

    public void setLesson(LessonDTO lesson) {
        this.lesson = lesson;
    }

    public LessonCertificationTypeDTO getLessonCertificationType() {
        return lessonCertificationType;
    }

    public void setLessonCertificationType(LessonCertificationTypeDTO lessonCertificationType) {
        this.lessonCertificationType = lessonCertificationType;
    }

    public int getMaxCertificationScore() {
        return maxCertificationScore;
    }

    public void setMaxCertificationScore(int maxCertificationScore) {
        this.maxCertificationScore = maxCertificationScore;
    }

    @Override
    public String toString() {
        return "LessonCertificationDTO{" +
                "id=" + id +
                ", lesson=" + lesson +
                ", lessonCertificationType=" + lessonCertificationType +
                ", maxCertificationScore=" + maxCertificationScore +
                '}';
    }
}
