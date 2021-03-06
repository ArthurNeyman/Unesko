package com.unesco.core.repositories.journal;

import com.unesco.core.entities.journal.PointEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PointRepository extends CrudRepository<PointEntity, Long> {

    @Query("SELECT p FROM PointEntity p where p.student.user.id = :studentId AND" +
            " p.pair.id = :pairId AND" +
            " p.date BETWEEN :from AND :to ")
    List<PointEntity> findByStudentIdAndPairIdAndMonth(@Param("studentId") long studentId,
                                               @Param("pairId") long pairId, @Param("from") Date from,
                                               @Param("to") Date to);

    @Query("SELECT p FROM PointEntity p where p.pair.lesson.id = :lessonId")
    List<PointEntity> findByLesson(@Param("lessonId") long lessonId);

    PointEntity findByStudentIdAndDateAndTypeIdAndPairIdAndDateOfCreate(@Param("studentId") long studentId,
                                                         @Param("date") Date date,
                                                         @Param("typeId") long typeId,
                                                         @Param("pairId") long pairId,
                                                         @Param("dateOfCreate") Date dateOfCreate);
    @Query("SELECT p FROM PointEntity p where p.student.id = :studentId AND" +
            " p.pair.id = :pairId")
    List<PointEntity> findByStudentIdAndPairId(@Param("studentId") long studentId,
                                               @Param("pairId") long pairId);

    List<PointEntity> findByStudentIdAndTypeId(@Param("studentId") long studentId, @Param("typeId") long typeId);


    @Query("SELECT sum(p.value) FROM PointEntity p where p.student.id = :studentId AND" +
            " p.pair.id = :pairId AND p.type.id = :eventId")
    Integer getSumEvent(@Param("eventId") long event_id,
                      @Param("pairId") long pair_id,
                      @Param("studentId") long student_id);

    List<PointEntity> findByStudentIdAndDateBetween(@Param("studentId") long studentId,@Param("dateStart") Date dateStart,@Param("dateEnd") Date dateEnd);

}
