package com.unesco.core.controller;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.LessonCertificationDTO;
import com.unesco.core.dto.certification.LessonCertificationResultDTO;
import com.unesco.core.dto.enums.StatusTypes;


import com.unesco.core.dto.account.StudentDTO;

import com.unesco.core.dto.journal.JournalDTO;
import com.unesco.core.dto.journal.LessonEventDTO;
import com.unesco.core.dto.journal.PointDTO;
import com.unesco.core.dto.shedule.PairDTO;
import com.unesco.core.dto.shedule.StudentLessonsDTO;
import com.unesco.core.dto.studentInterface.ArchivePointDTO;
import com.unesco.core.dto.studentInterface.EventPairDTO;
import com.unesco.core.dto.studentInterface.PerformanceDTO;
import com.unesco.core.dto.studentInterface.CertificationLessonsStudentDTO;
import com.unesco.core.managers.journal.lessonEvent.interfaces.lessonEventList.ILessonEventListManager;
import com.unesco.core.services.dataService.account.studentService.IStudentDataService;

import com.unesco.core.services.dataService.journal.certification.ILessonCertificationService;
import com.unesco.core.services.dataService.journal.journal.IJournalDataService;
import com.unesco.core.services.dataService.journal.lessonEvent.ILessonEventDataService;
import com.unesco.core.services.dataService.journal.point.IPointDataService;
import com.unesco.core.services.dataService.schedule.lessonService.ILessonDataService;
import com.unesco.core.services.dataService.schedule.pairService.IPairDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentPerformanceController {

    @Autowired
    private IJournalDataService journalDataService;

    @Autowired
    private IStudentDataService studentPointsService;

    @Autowired
    private IPairDataService pairDataService;
    @Autowired
    private ILessonCertificationService lessonCertificationService;
    @Autowired
    private IPointDataService pointDataService;
    @Autowired
    private ILessonEventDataService lessonEventDataService;
    @Autowired
    private ILessonEventListManager lessonEventListManager;

    @Autowired
    private JournalController journalController;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    // Получить баллы успеваемости студента
    public ResponseStatusDTO getPoints(long userId) {
        StudentDTO student = studentPointsService.getByUser(userId); // Студент

        List<StudentLessonsDTO> studentLessons = studentPointsService.findByStudentId(student.getId());                 // Занятия для конкретного студента(подгруппы студента)


        List<PerformanceDTO> config = new ArrayList<>();
        for (StudentLessonsDTO lesson : studentLessons) {
            ///////// События для предмета
            // Костыль
            Map<Long, EventPairDTO> eventsPairWithPoints = new HashMap<>();
            lessonEventListManager.init(lessonEventDataService.getByLesson(lesson.getLesson().getId()));
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
            ///////

            // Считаем баллы набранные за текущий предмет
            int sumValue = getSumPointForLesson(student.getId(), lesson.getLesson().getId());

            // Считаем максимум баллов за текущий предмет
            int maxValue = getMaxPointsForLesson(lesson);

            PerformanceDTO object = new PerformanceDTO(maxValue, sumValue, lesson.getLesson(), eventsPairWithPoints);
            config.add(object);                                                                                         // Объект для передачи
        }

        return new ResponseStatusDTO(StatusTypes.OK, config);
    }

    // Получить архив отметок
    public ResponseStatusDTO getArchivePoints(long userId, String dateStart,String dateEnd) {
        StudentDTO student = studentPointsService.getByUser(userId); // Студент
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

    // Получить список предметов с информацией об аттестации
    public ResponseStatusDTO getLessonListWithCertification(long userId) {
        StudentDTO student = studentPointsService.getByUser(userId); // Студент

        List<StudentLessonsDTO> studentLessons = studentPointsService.findByStudentId(student.getId());                 // Занятия для конкретного студента(подгруппы студента)


        List<CertificationLessonsStudentDTO> resultList = new ArrayList<>();
        for (StudentLessonsDTO lesson : studentLessons) {
            // Считаем баллы набранные за текущий предмет
            int sumValue = getSumPointForLesson(student.getId(), lesson.getLesson().getId());

            // Считаем максимум баллов за текущий предмет
            int maxValue = getMaxPointsForLesson(lesson);



            int currentCertificationPoints = -1;
            boolean absence = false;
            String date = "";
            int maxCertificationPoints = -1;
            long certificationTypeId = -1;

            if ((lessonCertificationService.getLessonCertification(lesson.getLesson().getId())).getData() != null) {
               LessonCertificationDTO lessonCertification = (LessonCertificationDTO) (lessonCertificationService.getLessonCertification(lesson.getLesson().getId())).getData();
               maxCertificationPoints = lessonCertification.getMaxCertificationScore();
               certificationTypeId = lessonCertification.getMaxCertificationScore();
               if (lessonCertificationService.getLessonCertificationResultByStudentIdAndLessonCertificationId(student.getId(),  lessonCertification.getId()) != null) {
                    LessonCertificationResultDTO lessonCertificationResult = lessonCertificationService.getLessonCertificationResultByStudentIdAndLessonCertificationId(student.getId(),  lessonCertification.getId());
                    currentCertificationPoints = lessonCertificationResult.getCertificationScore();
                    absence = lessonCertificationResult.isAbsence();
                    date = lessonCertificationResult.getRatingDate() != null ? formatter.format(lessonCertificationResult.getRatingDate()) : "";
                }
            }
            
            CertificationLessonsStudentDTO object = new CertificationLessonsStudentDTO(lesson.getLesson(), sumValue, maxValue,
                    currentCertificationPoints,  maxCertificationPoints,
                    certificationTypeId, absence,
                    date);
            resultList.add(object);                                                                                         // Объект для передачи
        }

        return new ResponseStatusDTO(StatusTypes.OK, resultList);
    }

    // Получить сумму баллов за предмет
    public int getSumPointForLesson(long studentId, long lessonId) {
        List<PairDTO> studentPairs = pairDataService.getAllByLesson(lessonId);
        List<PointDTO> pointsStudent;        // Количество полученных баллов
        int sumValue = 0;
        // Пробегаемся по всем найденным парам и считаем сумму для студента
        for (PairDTO pair : studentPairs) {
            pointsStudent = pointDataService.getByStudentAndPair(studentId, pair.getId());
            for (PointDTO pointStudent : pointsStudent) {
                sumValue += pointStudent.getValue();                         // Количество полученных баллов
            }
        }
        return sumValue;
    }

    // Получить максимум баллов за предмет
    public int getMaxPointsForLesson(StudentLessonsDTO lesson) {
        int maxValue = 0;
        JournalDTO journal = journalDataService.get(lesson.getLesson().getId(), null, lesson.getLesson().getSemesterNumberYear().getSemester(), lesson.getLesson().getSemesterNumberYear().getYear());
        List<Date> dates = journal.getComparison().stream().map(x -> x.getDate()).collect(Collectors.toList());                 // максимальное кол-во баллов за посещение
        maxValue = lessonEventDataService.getSumMaxValue(lesson.getLesson().getId()) + dates.size();                      // Максимальное кол-во баллов за предмет
        return maxValue;
    }
}
