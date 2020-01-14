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

    @Query("SELECT sum(p.maxValue) FROM LessonEventEntity p where p.lessonEntity.id = :lessonId AND" +
            " p.date BETWEEN :startDate AND :endDate" )
    Integer getMaxPointValue(@Param("lessonId") long lessonId,
                         @Param("startDate") Date startDate,
                         @Param("endDate") Date endDate);

    @Query("SELECT sum(p.maxValue) FROM LessonEventEntity p where p.lessonEntity.id = :lessonId")
    Integer getMaxPointValue(@Param("lessonId") long lessonId);


}
