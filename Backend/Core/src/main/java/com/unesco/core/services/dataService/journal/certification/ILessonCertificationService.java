package com.unesco.core.services.dataService.journal.certification;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.IntermediateCertificationDTO;
import com.unesco.core.dto.certification.IntermediateCertificationResultDTO;
import com.unesco.core.dto.certification.IntermediateCertificationTypeDTO;
import com.unesco.core.dto.shedule.LessonDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ILessonCertificationService {

    IntermediateCertificationDTO setLessonCertificationType(IntermediateCertificationDTO intermediateCertificationDTO);

    List<IntermediateCertificationTypeDTO> getLessonCertificationTypes();

    ResponseStatusDTO<IntermediateCertificationDTO> getLessonCertificationByLesson(LessonDTO lessonDTO);

    ResponseStatusDTO saveLessonCertificationResult(List<IntermediateCertificationResultDTO> intermediateCertificationResultDTOS);

    ResponseStatusDTO getLessonCertificationsByProfessorIdAndSemesterAndYear(long professorId, int semester, int year);

    ResponseStatusDTO setMaximumCertificationScore(IntermediateCertificationDTO intermediateCertificationDTO);

    IntermediateCertificationDTO getIntermediateCertification(long lessonId);
    IntermediateCertificationResultDTO getIntermediateCertificationForStudent(long lessonId,long studentId);
}
