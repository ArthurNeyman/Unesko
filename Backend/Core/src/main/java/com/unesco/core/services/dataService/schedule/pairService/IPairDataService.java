package com.unesco.core.services.dataService.schedule.pairService;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.plan.EducationPeriodDTO;
import com.unesco.core.dto.shedule.LessonDTO;
import com.unesco.core.dto.shedule.PairDTO;
import com.unesco.core.entities.plan.EducationPeriodEntity;
import com.unesco.core.services.dataService.IDataService;

import java.util.List;

public interface IPairDataService extends IDataService<PairDTO> {
    List<PairDTO> getAllByProfessor(long professorId, int semester, int year);
    List<PairDTO> getAllByDepartament(long departmentId, int semester, int year);
    List<PairDTO> getAllByGroup(long groupId, int semester, int year);
    List<PairDTO> getAllByLesson(long lessonId, int semester, int year);
    EducationPeriodEntity getEducationPeriodForYearAndSemester(int semester, int year);
    List<PairDTO> getAllWhereYearAndSemesterFit(long lessonId, long periodId);
    ResponseStatusDTO<PairDTO> save(PairDTO pair, EducationPeriodDTO period, LessonDTO findLesson);
}
