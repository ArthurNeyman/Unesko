package com.unesco.core.controller;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.enums.StatusTypes;


import com.unesco.core.dto.account.StudentDTO;

import com.unesco.core.dto.journal.PointDTO;
import com.unesco.core.dto.shedule.LessonDTO;
import com.unesco.core.dto.shedule.PairDTO;
import com.unesco.core.dto.shedule.StudentLessonsDTO;
import com.unesco.core.dto.studentInterface.PerformanceDTO;
import com.unesco.core.entities.schedule.StudentLessonSubgroupEntity;
import com.unesco.core.services.dataService.account.studentService.IStudentDataService;

import com.unesco.core.services.dataService.journal.lessonEvent.ILessonEventDataService;
import com.unesco.core.services.dataService.journal.point.IPointDataService;
import com.unesco.core.services.dataService.schedule.lessonService.ILessonDataService;
import com.unesco.core.services.dataService.schedule.pairService.IPairDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StudentPerformanceController {

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

    public ResponseStatusDTO getPoints(long userId) {
        StudentDTO student = studentPointsService.getByUser(userId);

        List<LessonDTO> groupLessons = lessonDataService.getByGroupId(student.getGroup().getId());                      // Список всех занятий для группы за всё время

        List<StudentLessonsDTO> studentLessons = studentPointsService.findByStudentId(student.getId());                 // Занятия для конкретного студента(подгруппы студента)

        List<List<PairDTO>> pairs = new ArrayList<>();
        List<Integer> pairStudent = new ArrayList<>();
        List<PerformanceDTO> config = new ArrayList<>();
        for (StudentLessonsDTO lessons:  studentLessons) {
            List<PairDTO> studentPairs = pairDataService.getAllByLesson(lessons.getLesson().getId());
            pairs.add(studentPairs);

            // Пробегаемся по всем найденным парам и считаем сумму для студента
            int sumValue = 0;                                                                                           // Количество полученных баллов
            for (PairDTO pair: studentPairs) {
                sumValue += pointDataService.getByStudentAndPair(student.getId(),pair.getId());                         // Количество полученных баллов
            }
            Integer maxValue = lessonEventDataService.getSumMaxValue(lessons.getLesson().getId());                      // Максимальное кол-во баллов за предмет

            PerformanceDTO object = new PerformanceDTO(maxValue,sumValue,lessons.getLesson());
            config.add(object);                                                                                         // Объект для передачи
        }

        return new ResponseStatusDTO(StatusTypes.OK, config);
    }
}
