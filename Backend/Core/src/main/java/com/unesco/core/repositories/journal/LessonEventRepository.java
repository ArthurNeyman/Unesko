package com.unesco.core.repositories.journal;

import com.unesco.core.entities.journal.LessonEventEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LessonEventRepository extends CrudRepository<LessonEventEntity, Long> {
    LessonEventEntity findByTypeId(long id);

    List<LessonEventEntity> findByLessonEntityId(long lessonId);
}
