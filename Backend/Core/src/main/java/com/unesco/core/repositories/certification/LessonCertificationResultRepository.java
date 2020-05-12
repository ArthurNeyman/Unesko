package com.unesco.core.repositories.certification;

import com.unesco.core.entities.certification.LessonCertificationResultEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonCertificationResultRepository extends CrudRepository<LessonCertificationResultEntity,Long> {

    List<LessonCertificationResultEntity> getByLessonCertificationId(long lessonCertificationId);
    List<LessonCertificationResultEntity> getByLessonCertificationId(long lessonCertificationId, Sort sort);

}
