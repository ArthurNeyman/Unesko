package com.unesco.core.services.dataService.journal.lessonEvent;

import com.unesco.core.dto.journal.LessonEventDTO;
import com.unesco.core.services.dataService.IDataService;

import java.util.Date;
import java.util.List;

public interface ILessonEventDataService extends IDataService<LessonEventDTO> {
    List<LessonEventDTO> getByLesson(long lessonId);
    int getSumMaxValueBetweenDates(long lessonId, Date start, Date end);

    int getSumMaxValue(long lessonId);
}
