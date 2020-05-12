package com.unesco.core.services.dataService.journal.certification;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.LessonCertificationDTO;
import com.unesco.core.dto.certification.LessonCertificationResultDTO;
import com.unesco.core.dto.certification.LessonCertificationTypeDTO;
import com.unesco.core.dto.shedule.LessonDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ILessonCertificationService {

    //Назначить/изменить тип аттестации
    ResponseStatusDTO setLessonCertificationType(LessonCertificationDTO lessonCertificationDTO);

    //получить тип аттестации
    ResponseStatusDTO<LessonCertificationDTO> getLessonCertificationByLesson(LessonDTO lessonDTO);

    //получить список аттестационной инофрмации для предмета
    List<LessonCertificationResultDTO> getLessonCertificationResultListByLesson(LessonDTO lessonDTO);

    //получить типы аттестаций для назначения
    List<LessonCertificationTypeDTO> getLessonCertificationTypes();

    //Сохранить результат аттестации
    ResponseStatusDTO saveLessonCertificationResult(List<LessonCertificationResultDTO> lessonCertificationResultDTOS);

    ResponseStatusDTO getLessonsByLessonIdAndProfessor(long professorId, int semester, int year);

    ResponseStatusDTO setMaximumCertificationScore(@RequestBody LessonCertificationDTO lessonCertificationDTO);

    ResponseStatusDTO getLessonCertificationResult(LessonCertificationDTO lessonCertificationDTO);
    ResponseStatusDTO getLessonCertification(long lessonId);
    public ResponseStatusDTO getLessonEvents(long lessonId);

}
