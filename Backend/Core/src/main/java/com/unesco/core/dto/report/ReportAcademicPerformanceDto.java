package com.unesco.core.dto.report;

import com.unesco.core.dto.account.ProfessorDTO;
import com.unesco.core.dto.journal.CertificationReportDto;
import com.unesco.core.dto.plan.SemesterNumberYear;
import com.unesco.core.dto.shedule.LessonDTO;
import java.util.Map;

public class ReportAcademicPerformanceDto {

    private ProfessorDTO professor;
    private Map<LessonDTO, CertificationReportDto> lessonList;
    private SemesterNumberYear semesterNumberYear;


    public ProfessorDTO getProfessor() {
        return professor;
    }

    public void setLessonList(Map<LessonDTO, CertificationReportDto> lessonList) {
        this.lessonList = lessonList;
    }

    public Map<LessonDTO, CertificationReportDto> getLessonList() {
        return lessonList;
    }

    public SemesterNumberYear getSemesterNumberYear() {
        return semesterNumberYear;
    }

    public void setProfessor(ProfessorDTO professor) {
        this.professor = professor;
    }

    public void setSemesterNumberYear(SemesterNumberYear semesterNumberYear) {
        this.semesterNumberYear = semesterNumberYear;
    }

}
