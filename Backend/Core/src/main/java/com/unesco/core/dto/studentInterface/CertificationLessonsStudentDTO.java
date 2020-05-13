package com.unesco.core.dto.studentInterface;

import com.unesco.core.dto.shedule.LessonDTO;

import java.util.Map;

public class CertificationLessonsStudentDTO {
    public LessonDTO lesson;
    public int currentPoints; // текущий балл
    public int currentCertificationPoints; // текущий аттестационный балл
    public int maxGotPoints;  // макс. кол-во баллов полученные в течение семестра
    public int maxCertificationPoints;  // макс. кол-во баллов за аттестацию
    public long certificationTypeId;  // тип аттестации (1 - Экзамен, 2 - зачет)
    public boolean absence;  // была ли явка
    public String date;  // дата проведения аттестации
    public String statusCertification;  // Статус аттестации словами для фронта
    public int statusCertificationId;  // Статус аттестации 1 - не зачтено, 6 - зачтено, 2-5 оценка за экзамен




    public CertificationLessonsStudentDTO() {}

    public CertificationLessonsStudentDTO(LessonDTO lesson ,int currentPoints, int maxGotPoints,
                                          int currentCertificationPoints, int maxCertificationPoints,
                                          long certificationTypeId, boolean absence, String date,
                                          String statusCertification, int statusCertificationId) {
        this.lesson = lesson;
        this.currentPoints = currentPoints;
        this.currentCertificationPoints = currentCertificationPoints;
        this.maxGotPoints = maxGotPoints;
        this.maxCertificationPoints = maxCertificationPoints;
        this.certificationTypeId = certificationTypeId;
        this.absence = absence;
        this.date = date;
        this.statusCertification = statusCertification;
        this.statusCertificationId = statusCertificationId;
    }
}
