package com.unesco.core.repositories.certification;

import com.unesco.core.entities.certification.IntermediateCertificationResultEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IntermediateСertificationResultRepository extends CrudRepository<IntermediateCertificationResultEntity,Long> {

    List<IntermediateCertificationResultEntity> getByLessonCertificationId(long lessonCertificationId);
    List<IntermediateCertificationResultEntity> getByLessonCertificationId(long lessonCertificationId, Sort sort);


    IntermediateCertificationResultEntity getLessonCertificationResultByStudentEntityIdAndLessonCertificationId(@Param("studentEntityId") long studentId,
                                                                                                                @Param("lessonCertificationId") long lessonCertificationId);

}
