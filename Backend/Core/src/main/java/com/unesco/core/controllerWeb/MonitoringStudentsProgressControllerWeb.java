package com.unesco.core.controllerWeb;

import com.unesco.core.controller.MonitoringStudentsProgressController;
import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.CurrentCertificationDTO;
import com.unesco.core.dto.certification.IntermediateCertificationDTO;
import com.unesco.core.dto.certification.IntermediateCertificationResultDTO;
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
                                           @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end)
    {
        return controller.getCurrentCertification(lessonId,start,end);
    }

    @RequestMapping("/getCertification/{lessonId}")
    public ResponseStatusDTO getCertification(@PathVariable("lessonId") long lessonId){
        return controller.getCurrentCertificationListForLesson(lessonId);
    }

    @RequestMapping ("/saveCertification")
    public ResponseStatusDTO saveCertification(@RequestBody CurrentCertificationDTO certification){
        return controller.saveCurrentCertification(certification);
    }

    @RequestMapping("/deleteCertification")
    public ResponseStatusDTO deleteCertification(@RequestBody CurrentCertificationDTO certification){
        return controller.deleteCurrentCertification(certification);
    }
    //==================================================================================================================
    @RequestMapping("/onProgress/{userId}")
    public ResponseStatusDTO getReportAcademicPerfomance(@PathVariable("userId") long userId,
                                                         @RequestParam int semester,
                                                         @RequestParam int year){
        return controller.getReportAcademicPerformance(userId,semester,year);
    }
    //==================================================================================================================
//    @RequestMapping("getLessonCertification/{lessonId}")
//    public ResponseStatusDTO getLessonCertification(@PathVariable("lessonId") long lessonId){
//        return this.controller.getLessonCertification(lessonId);
//    }

//    @RequestMapping("/getLessonCertificationResult")
//    public ResponseStatusDTO getLessonCertificationResult(@RequestBody IntermediateCertificationDTO intermediateCertificationDTO,
//                                                          @RequestParam String currentOnly ){
//        return  this.controller.getLessonCertificationResult(intermediateCertificationDTO,currentOnly);
//    }

//    @RequestMapping("/getLessonEvents/{lessonId}")
//    public ResponseStatusDTO getLessonCertificationResult(@PathVariable("lessonId") long lessonId,
//                                                          @RequestParam int semester,
//                                                          @RequestParam int year){
//        return  this.controller.getLessonEvents(lessonId,semester,year);
//    }

//    @RequestMapping("/getStudentCertification")
//    public ResponseStatusDTO geuStudentCertificationforLesson(@RequestParam long lessonId,
//                                                              @RequestParam long student_id){
//        return this.controller.getCertificationStudent(student_id,lessonId);
//
//    }

//    @RequestMapping("/getStudentResults/{lessonId}")
//    public ResponseStatusDTO getLessonCertificationStudentResults(@PathVariable("lessonId") long lessonId){
//        return  this.controller.getStudentResults(lessonId);
//    }

    //==================================================================================================================
    @RequestMapping("/getIntermediateCertification/{lessonId}")
    public ResponseStatusDTO getIntermediateCertification(@PathVariable("lessonId") long lessonId){
        return  this.controller.getIntermediateCertification(lessonId);
    }
    @RequestMapping("/saveLessonCertificationResult")
    public ResponseStatusDTO saveLessonCertificationResult(@RequestBody List<IntermediateCertificationResultDTO> intermediateCertificationResultDTOList){
        return this.controller.saveLessonCertificationResult(intermediateCertificationResultDTOList);
    }
    @RequestMapping("/setMaxScore")
    public ResponseStatusDTO setMaximunCertificationScore(@RequestBody IntermediateCertificationDTO intermediateCertificationDTO){
        return  this.controller.setMaximumCertificationScore(intermediateCertificationDTO);
    }
    @RequestMapping("/getLessonCertificationList/{professorId}")
    public  ResponseStatusDTO getLessonCertificationList(@PathVariable("professorId") long professorId,
                                                         @RequestParam int semester,
                                                         @RequestParam int year){
        return this.controller.getLessonsByLessonIdAndProfessor(professorId,semester,year);
    }
    @RequestMapping("/getNewLessonCertificationResultDTO")
    public ResponseStatusDTO getNewresult(@RequestBody IntermediateCertificationResultDTO intermediateCertificationResultDTO,
                                          @RequestParam int semester,
                                          @RequestParam int year,
                                          @RequestParam long lesson_id,
                                          @RequestParam String currentOnly){
        return this.controller.getNewLessonCertificationResult(lesson_id, intermediateCertificationResultDTO,currentOnly);
    }
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping("/getLessonCertificationTypes")
    ResponseStatusDTO getLessonCertificationTypes(){
        return  controller.getLessonCertificationTypes();
    }
    @RequestMapping("/setLessonCertificationType")
    public  ResponseStatusDTO setCertificationType(@RequestBody IntermediateCertificationDTO intermediateCertificationDTO){
        return this.controller.setIntermediateCertificationType(intermediateCertificationDTO);
    }


}
