package com.unesco.core.controller;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.enums.StatusTypes;


import com.unesco.core.dto.account.StudentDTO;

import com.unesco.core.dto.journal.JournalDTO;
import com.unesco.core.dto.journal.LessonEventDTO;
import com.unesco.core.dto.journal.PointDTO;
import com.unesco.core.dto.journal.VisitationConfigDTO;
import com.unesco.core.dto.shedule.LessonDTO;
import com.unesco.core.dto.shedule.PairDTO;
import com.unesco.core.dto.shedule.StudentLessonsDTO;
import com.unesco.core.dto.studentInterface.ArchivePointDTO;
import com.unesco.core.dto.studentInterface.EventPairDTO;
import com.unesco.core.dto.studentInterface.PerformanceDTO;
import com.unesco.core.managers.journal.lessonEvent.interfaces.lessonEventList.ILessonEventListManager;
import com.unesco.core.services.dataService.account.studentService.IStudentDataService;

import com.unesco.core.services.dataService.journal.journal.IJournalDataService;
import com.unesco.core.services.dataService.journal.lessonEvent.ILessonEventDataService;
import com.unesco.core.services.dataService.journal.point.IPointDataService;
import com.unesco.core.services.dataService.journal.visitation.IVisitationConfigDataService;
import com.unesco.core.services.dataService.schedule.lessonService.ILessonDataService;
import com.unesco.core.services.dataService.schedule.pairService.IPairDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentPerformanceController {

    @Autowired
    private IVisitationConfigDataService visitationConfigDataService;

    @Autowired
    private IJournalDataService journalDataService;

    @Autowired
    private IStudentDataService studentPointsService;
    @Autowired
    private ILessonDataService lessonDataService;

    @Autowired
    private IPairDataService pairDataService;
    @Autowired
    private IPointDataService pointDataService;
    @Autowired
    private ILessonEventDataService lessonEventDataService;
    @Autowired
    private ILessonEventListManager lessonEventListManager;

    @Autowired
    private JournalController journalController;

    public ResponseStatusDTO getPoints(long userId) {
        StudentDTO student = studentPointsService.getByUser(userId); // Студент

        List<StudentLessonsDTO> studentLessons = studentPointsService.findByStudentId(student.getId());                 // Занятия для конкретного студента(подгруппы студента)


        List<PerformanceDTO> config = new ArrayList<>();
        for (StudentLessonsDTO lesson : studentLessons) {
            List<PairDTO> studentPairs = pairDataService.getAllByLesson(lesson.getLesson().getId());
            Map<Long, EventPairDTO> eventsPairWithPoints = new HashMap<>();
            lessonEventListManager.init(lessonEventDataService.getByLesson(lesson.getLesson().getId()));

            // Костыль
            // Бегаем по событиям для предмета, и без повторений добавляем в объект
            for (LessonEventDTO lessonEvents : lessonEventListManager.getAll()) {
                long idEvent = lessonEvents.getType().getId();             // ключ типа
                float maxValueEvent = lessonEvents.getMaxValue();           // Макс значение баллов события
                if (eventsPairWithPoints.containsKey(idEvent)) {
                    eventsPairWithPoints.get(idEvent).maxValue += maxValueEvent;
                    continue;
                }
                String nameEvent = lessonEvents.getType().getName();       // Название события
                float valueForEvent = 0;  // сумма баллов за событие
                for (PairDTO lesson_event_pair : lessonEvents.getPairs()) {
                    valueForEvent += pointDataService.getSumValueByEventPairStudentId(idEvent, lesson_event_pair.getId(), student.getId());
                }
                eventsPairWithPoints.put(idEvent, new EventPairDTO(idEvent, nameEvent, maxValueEvent, valueForEvent));
            }
            List<PointDTO> pointsStudent;

            // Считаем баллы набранные за текущий предмет
            // Пробегаемся по всем найденным парам и считаем сумму для студента
            int sumValue = 0;                                                                                           // Количество полученных баллов
            for (PairDTO pair : studentPairs) {
                pointsStudent = pointDataService.getByStudentAndPair(student.getId(), pair.getId());
                for (PointDTO pointStudent : pointsStudent) {
                    sumValue += pointStudent.getValue();                         // Количество полученных баллов
                }
            }
            // Считаем максимум баллов за текущий предмет
            JournalDTO journal = journalDataService.get(lesson.getLesson().getId(), null, lesson.getLesson().getSemesterNumberYear().getSemester(), lesson.getLesson().getSemesterNumberYear().getYear());
            List<Date> dates = journal.getComparison().stream().map(x -> x.getDate()).collect(Collectors.toList());
            Integer maxValue = lessonEventDataService.getSumMaxValue(lesson.getLesson().getId()) + dates.size();                      // Максимальное кол-во баллов за предмет


            PerformanceDTO object = new PerformanceDTO(maxValue, sumValue, lesson.getLesson(), eventsPairWithPoints);
            config.add(object);                                                                                         // Объект для передачи
        }

        return new ResponseStatusDTO(StatusTypes.OK, config);
    }

    public ResponseStatusDTO getArchivePoints(long userId, String dateStart,String dateEnd) {
        StudentDTO student = studentPointsService.getByUser(userId); // Студент
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date formatDateStart = null;
        try {
            formatDateStart = formatter.parse(dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date formatDateEnd = null;
        try {
            formatDateEnd = formatter.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<ArchivePointDTO> pointsArchive = pointDataService.getArchivePoints(student.getId(), formatDateStart, formatDateEnd);

        return new ResponseStatusDTO(StatusTypes.OK, pointsArchive);
    }
}
