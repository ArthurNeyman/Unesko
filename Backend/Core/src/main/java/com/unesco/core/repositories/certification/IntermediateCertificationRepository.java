package com.unesco.core.repositories.certification;

import com.unesco.core.entities.certification.IntermediateCertificationEntity;
import org.springframework.data.repository.CrudRepository;

public interface IntermediateCertificationRepository extends CrudRepository<IntermediateCertificationEntity,Long> {

    IntermediateCertificationEntity getByLessonId(long lessonId);

}
