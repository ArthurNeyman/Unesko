package com.unesco.core.managers.monitoringStudentsProgress;

import com.unesco.core.dto.certification.IntermediateCertificationDTO;
import com.unesco.core.dto.certification.IntermediateCertificationResultDTO;
import com.unesco.core.dto.certification.ReportElement;
import com.unesco.core.dto.journal.CurrentCertificationDto;
import com.unesco.core.dto.journal.JournalDTO;
import com.unesco.core.dto.journal.LessonEventDTO;

import java.util.Date;
import java.util.List;

public interface IMonitoringStudentsManager {

    void init(JournalDTO journal, List<LessonEventDTO> lessonEventDTOList);
    void init(IntermediateCertificationDTO intermediateCertificationDTO,JournalDTO journal, List<LessonEventDTO> lessonEventDTOList);
    CurrentCertificationDto CurrentCertification(Date start, Date end);
    ReportElement Report();
    IntermediateCertificationDTO IntermediateCertification(boolean currentOnly);
    IntermediateCertificationResultDTO updateIntermediateCertificationForResult(IntermediateCertificationResultDTO intermediateCertificationResultDTO, boolean currentOnly);
}
