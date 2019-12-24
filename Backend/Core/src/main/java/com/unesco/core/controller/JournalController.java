package com.unesco.core.controller;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.enums.StatusTypes;
import com.unesco.core.dto.journal.*;
import com.unesco.core.managers.journal.VisitationConfigManager.interfaces.IVisitationConfigManager;
import com.unesco.core.managers.journal.journalManager.interfaces.journal.IJournalManager;
import com.unesco.core.managers.journal.lessonEvent.interfaces.lessonEvent.ILessonEventManager;
import com.unesco.core.managers.journal.lessonEvent.interfaces.lessonEventList.ILessonEventListManager;
import com.unesco.core.services.dataService.journal.journal.IJournalDataService;
import com.unesco.core.services.dataService.journal.lessonEvent.ILessonEventDataService;
import com.unesco.core.services.dataService.journal.visitation.IVisitationConfigDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JournalController {

    @Autowired
    private IJournalDataService journalDataService;
    @Autowired
    private IJournalManager journalManager;
    @Autowired
    private ILessonEventManager lessonEventManager;
    @Autowired
    private ILessonEventListManager lessonEventListManager;
    @Autowired
    private IVisitationConfigDataService visitationConfigDataService;
    @Autowired
    private ILessonEventDataService lessonEventDataService;
    @Autowired
    private IVisitationConfigManager visitationConfigManager;

    public ResponseStatusDTO getJournal(long lessonId, int month, Date forDate, int semester, int year) {

        JournalDTO journal = journalDataService.getForMonth(lessonId, month, forDate, semester, year);

        VisitationConfigDTO visitConfig = visitationConfigDataService.getByLesson(lessonId);
        visitationConfigManager.init(visitConfig);

        List<LessonEventDTO> lessonEvents = lessonEventDataService.getAll();
        lessonEventListManager.init(lessonEvents);

        journalManager.init(journal, lessonEventListManager.getAll(), visitationConfigManager.get());
        journalManager.CreateJournal();
        System.out.println(journalManager.get().getComparison().size()+" "+forDate);

        return new ResponseStatusDTO(StatusTypes.OK, journalManager.get());
    }

    public ResponseStatusDTO getJournalHistoryDate(long lessonId, int semester, int year) {
        List<Date> historyDates = journalDataService.getHistoryDates(lessonId, semester, year);
        return new ResponseStatusDTO(StatusTypes.OK, historyDates);
    }

    /**
     * На данный момент метод не верен.
     * Должен возвращать даты исходя из семестра.
     * Сейчас даты заданы в коде метода get.
     * @param lessonId ID занятия
     * @return ResponseStatusDTO
     */
    public ResponseStatusDTO getDates(long lessonId, int semester, int year) {
        JournalDTO journal = journalDataService.get(lessonId, null, semester, year);
        return new ResponseStatusDTO(StatusTypes.OK, journal.getComparison().stream().map(x -> x.getDate()).collect(Collectors.toList()));
    }

    public ResponseStatusDTO saveJournal(JournalDTO journal) {

        VisitationConfigDTO visitConfig = visitationConfigDataService.getByLesson(journal.getLesson().getId());
        visitationConfigManager.init(visitConfig);

        List<LessonEventDTO> lessonEvents = lessonEventDataService.getAll();
        lessonEventListManager.init(lessonEvents);

        journalManager.init(journal, lessonEventListManager.getAll(), visitationConfigManager.get());

        ResponseStatusDTO result = journalManager.validate();
        if(result.getStatus() == StatusTypes.ERROR) return result;

        try {
            result.setStatus(StatusTypes.OK);
            journalDataService.save(journalManager.get());
            result.addMessage("Журнал сохранен.");
            return result;
        }
        catch (Exception e)
        {
            result.setStatus(StatusTypes.ERROR);
            result.addErrors("При сохранении журнала произошла ошибка");
            result.addErrors(e.getMessage());
            return result;
        }
    }

    public ResponseStatusDTO getEvents(long lessonId) {

        lessonEventListManager.init(lessonEventDataService.getByLesson(lessonId));

        return new ResponseStatusDTO(StatusTypes.OK, lessonEventListManager.getAll());
    }

    public ResponseStatusDTO saveEvent(LessonEventDTO event) {
        lessonEventManager.init(event);
        ResponseStatusDTO result = lessonEventManager.validate();

        if(result.getStatus() == StatusTypes.ERROR) return result;
        try {
            lessonEventDataService.save(lessonEventManager.get());
            result.setStatus(StatusTypes.OK);
            result.addMessage("Событие сохранено.");
            return result;
        }
        catch (Exception e)
        {
            result.setStatus(StatusTypes.ERROR);
            result.addErrors("При сохранении события произошла ошибка");
            result.addErrors(e.getMessage());
            return result;
        }
    }

    public ResponseStatusDTO deleteEvent(long id) {
        ResponseStatusDTO result = new ResponseStatusDTO();
        try {
            result.setStatus(StatusTypes.OK);
            lessonEventDataService.delete(id);
            result.addMessage("Событие удалено.");
            return result;
        }
        catch (Exception e)
        {
            result.setStatus(StatusTypes.ERROR);
            result.addErrors("При удалении события произошла ошибка");
            result.addErrors(e.getMessage());
            return result;
        }
    }

    public ResponseStatusDTO saveVisitationConfig(VisitationConfigDTO visit) {
        visitationConfigManager.init(visit);
        ResponseStatusDTO result = visitationConfigManager.validate();

        if(result.getStatus() == StatusTypes.ERROR) return result;
        try {
            visitationConfigDataService.save(visitationConfigManager.get());
            result.setStatus(StatusTypes.OK);
            result.addMessage("Настройки посещаемости сохранены.");
            return result;
        }
        catch (Exception e)
        {
            result.setStatus(StatusTypes.ERROR);
            result.addErrors("При сохранении настроек произошла ошибка");
            result.addErrors(e.getMessage());
            return result;
        }
    }

    public ResponseStatusDTO getVisitationConfig(long lessonId) {
        VisitationConfigDTO visitationConfig = visitationConfigDataService.getByLesson(lessonId);

        if (visitationConfig == null) {
            return new ResponseStatusDTO(StatusTypes.OK);
        }

        return new ResponseStatusDTO(StatusTypes.OK, visitationConfig);
    }

    public ResponseStatusDTO getCertificationReport(long lessonId, Date start, Date end, int semester, int year) {

        JournalDTO journal = journalDataService.get(lessonId, null, semester, year);

        VisitationConfigDTO visitConfig = visitationConfigDataService.getByLesson(lessonId);
        visitationConfigManager.init(visitConfig);

        List<LessonEventDTO> lessonEvents = lessonEventDataService.getAll();
        lessonEventListManager.init(lessonEvents);

        journalManager.init(journal, lessonEventListManager.getAll(), visitationConfigManager.get());
        CertificationReportDto result = journalManager.CertificationReportDto(start, end);

        return new ResponseStatusDTO(StatusTypes.OK, result);
    }

    public ResponseStatusDTO getCertificationReport2(long lessonId, Date start, Date end, int semester, int year) {

        JournalDTO journal = journalDataService.get(lessonId, null, semester, year);
        journalManager.init(journal, lessonEventListManager.getAll(), visitationConfigManager.get());

        CertificationReportDto result = journalManager.CertificationReportDto(start, end);

        JournalDTO journal2 = journalDataService.getForMonth(lessonId, -1, null, semester, year);

        int mustBeCount=0;//сколько всего пар
        int beCount=0;

        for(StudentJournalDTO studentJournalDTO: journal2.getStudents()) {
            mustBeCount=0;
            beCount=0;
            for (ComparisonDTO comparisonDTO : journal2.getComparison()) {
                for (ComparisonPointDTO comparisonPointDTO : comparisonDTO.getPoints()) {
                    if((studentJournalDTO.getSubgroup()==comparisonPointDTO.getPair().getSubgroup() || comparisonPointDTO.getPair().getSubgroup()==0)
                    && (comparisonDTO.getDate().after(start) && comparisonDTO.getDate().before(end) ) ){
                        mustBeCount++;
                    }
                }
            }
            for(PointDTO pointDTO:journal2.getJournalCell()){
                    if( pointDTO.getDate().after(start)  && pointDTO.getDate().before(end) && pointDTO.getStudentId()==studentJournalDTO.getStudent().getId() && pointDTO.getType().getName().equals("Посещение"))
                        beCount++;
            }
            System.out.println(studentJournalDTO.getStudent().getUser().getUserFIO()+" " + beCount+"/"+mustBeCount);

            for(CertificationStudentDto certificationStudentDto: result.getStudentCertification()) {
                if(certificationStudentDto.getStudent().getId()==studentJournalDTO.getStudent().getId()){
                    certificationStudentDto.setMissingHours((mustBeCount-beCount)*2);
                    certificationStudentDto.setVisitationValue(beCount*2);
                }
            }
            //пересчитать eventValue
        }

        result.setAllHours(mustBeCount*2);

        return new ResponseStatusDTO(StatusTypes.OK, result);
    }

}
