package com.unesco.core.dto.report;

import com.unesco.core.dto.account.ProfessorDTO;
import com.unesco.core.dto.journal.CurrentCertificationDto;

import java.util.List;

public class ReportAcademicPerformanceDto {

    private ProfessorDTO professor;
    private List<CurrentCertificationDto> lessonList;

    public ProfessorDTO getProfessor() {
        return professor;
    }

    public void setLessonList(List<CurrentCertificationDto> lessonList) {
        this.lessonList = lessonList;
    }

    public List<CurrentCertificationDto> getLessonList() {
        return lessonList;
    }


    public void setProfessor(ProfessorDTO professor) {
        this.professor = professor;
    }

}
