package com.unesco.core.repositories.certification;

import com.unesco.core.entities.certification.CurrentCertificationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrentCertificationRepository extends CrudRepository<CurrentCertificationEntity, Long> {

    List<CurrentCertificationEntity> findByLessonId(long lessonId);
}
