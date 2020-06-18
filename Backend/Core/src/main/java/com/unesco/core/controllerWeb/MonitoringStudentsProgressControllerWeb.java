package com.unesco.core.controllerWeb;

import com.unesco.core.controller.MonitoringStudentsProgressController;
import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.CertificationDTO;
import com.unesco.core.dto.certification.LessonCertificationDTO;
import com.unesco.core.dto.certification.LessonCertificationResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/monitoringStudentsProgress")
public class MonitoringStudentsProgressControllerWeb {

    @Autowired
    private MonitoringStudentsProgressController controller;

    //Аттестация
    @RequestMapping("/certification/{lessonId}")
    public ResponseStatusDTO certification(@PathVariable("lessonId") long lessonId,
                                           @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                           @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                                           @RequestParam int semester,
                                           @RequestParam int year,
                                           @RequestParam String visitationOnly)
    {
        return controller.getCertificationReport(lessonId,start,end,semester,year,visitationOnly);
    }

    @RequestMapping("/getCertification/{lessonId}")
    public ResponseStatusDTO getCertification(@PathVariable("lessonId") long lessonId){
        return controller.getCertification(lessonId);
    }

    @RequestMapping ("/saveCertification")
    public ResponseStatusDTO saveCertification(@RequestBody CertificationDTO certification){
        return controller.saveCertification(certification);
    }

    @RequestMapping("/deleteCertification")
    public ResponseStatusDTO deleteCertification(@RequestBody CertificationDTO certification){
        return controller.deleteCertification(certification);
    }

    //Отчет по успеваемости
    @RequestMapping("/onProgress/{professorId}")
    public ResponseStatusDTO getReportAcademicPerfomance(@PathVariable("professorId") long professorId,
                                                         @RequestParam int semester,
                                                         @RequestParam int year){
        return controller.getReportAcademicPerformance(professorId,semester,year);
    }
    //Итоговая успеваемость
    @RequestMapping("/getLessonCertificationTypes")
    ResponseStatusDTO getLessonCertificationTypes(){
        return  controller.getLessonCertificationTypes();
    }

    @RequestMapping("/setLessonCertificationType")
    public  ResponseStatusDTO setCertificationType(@RequestBody LessonCertificationDTO lessonCertificationDTO){
        return this.controller.setCertificationType(lessonCertificationDTO);
    }

    @RequestMapping("/getLessonCertificationList/{professorId}")
    public  ResponseStatusDTO getLessonCertificationList(@PathVariable("professorId") long professorId,
                                                         @RequestParam int semester,
                                                         @RequestParam int year){
        return this.controller.getLessonsByLessonIdAndProfessor(professorId,semester,year);
    }
    @RequestMapping("getLessonCertification/{lessonId}")
    public ResponseStatusDTO getLessonCertification(@PathVariable("lessonId") long lessonId){
        return this.controller.getLessonCertification(lessonId);
    }

    @RequestMapping("/setMaxScore")
    public ResponseStatusDTO setMaximunCertificationScore(@RequestBody LessonCertificationDTO lessonCertificationDTO){
        return  this.controller.setMaximumCertificationScore(lessonCertificationDTO);
    }

    @RequestMapping("/getLessonCertificationResult")
    public ResponseStatusDTO getLessonCertificationResult(@RequestBody LessonCertificationDTO lessonCertificationDTO,
                                                          @RequestParam String currentOnly ){
        return  this.controller.getLessonCertificationResult(lessonCertificationDTO,currentOnly);
    }

    @RequestMapping("/getLessonEvents/{lessonId}")
    public ResponseStatusDTO getLessonCertificationResult(@PathVariable("lessonId") long lessonId,
                                                          @RequestParam int semester,
                                                          @RequestParam int year){
        return  this.controller.getLessonEvents(lessonId,semester,year);
    }

    @RequestMapping("/saveLessonCertificationResult")
    public ResponseStatusDTO saveLessonCertificationResult(@RequestBody List<LessonCertificationResultDTO> lessonCertificationResultDTOList){
        return this.controller.saveLessonCertificationResult(lessonCertificationResultDTOList);
    }

    @RequestMapping("/getStudentCertification")
    public ResponseStatusDTO geuStudentCertificationforLesson(@RequestParam long lessonId,
                                                              @RequestParam long student_id){
        return this.controller.getCertificationStudent(student_id,lessonId);

    }

    @RequestMapping("/getNewLessonCertificationResultDTO")
    public ResponseStatusDTO getNewresult(@RequestBody LessonCertificationResultDTO lessonCertificationResultDTO,
                                          @RequestParam int semester,
                                          @RequestParam int year,
                                          @RequestParam long lesson_id,
                                          @RequestParam String currentOnly){
        return this.controller.getNewLessonCertificationResult(lesson_id,lessonCertificationResultDTO,semester,year,currentOnly);
    }

    @RequestMapping("/getStudentResults/{lessonId}")
    public ResponseStatusDTO getLessonCertificationStudentResults(@PathVariable("lessonId") long lessonId){
        return  this.controller.getStudentResults(lessonId);
    }

}
