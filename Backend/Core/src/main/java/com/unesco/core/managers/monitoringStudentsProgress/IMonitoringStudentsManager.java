package com.unesco.core.managers.monitoringStudentsProgress;

import com.unesco.core.dto.journal.JournalDTO;
import com.unesco.core.dto.journal.LessonEventDTO;

import java.util.List;

public interface IMonitoringStudentsManager {

    void init(JournalDTO journal, List<LessonEventDTO> lessonEventDTOList);

}
