package com.unesco.core.repositories.certification;

import com.unesco.core.entities.certification.LessonCertificationEntity;
import org.springframework.data.repository.CrudRepository;

public interface LessonCertificationRepository extends CrudRepository<LessonCertificationEntity,Long> {

    LessonCertificationEntity getByLessonId(long lessonId);

}
