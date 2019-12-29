package com.unesco.core.dto.report;

import com.unesco.core.dto.account.ProfessorDTO;
import com.unesco.core.dto.journal.CertificationReportDto;

import java.util.List;

public class ReportAcademicPerformanceDto {

    private ProfessorDTO professor;
    private List<CertificationReportDto> lessonList;

    public ProfessorDTO getProfessor() {
        return professor;
    }

    public void setLessonList(List<CertificationReportDto> lessonList) {
        this.lessonList = lessonList;
    }

    public List<CertificationReportDto> getLessonList() {
        return lessonList;
    }


    public void setProfessor(ProfessorDTO professor) {
        this.professor = professor;
    }

}
