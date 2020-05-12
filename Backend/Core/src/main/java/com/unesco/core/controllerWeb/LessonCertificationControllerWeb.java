package com.unesco.core.controllerWeb;

import com.unesco.core.controller.LessonCertificationController;
import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.LessonCertificationDTO;
import com.unesco.core.dto.certification.LessonCertificationResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/lessonCertification")
public class LessonCertificationControllerWeb {

    @Autowired
    private LessonCertificationController lessonCertificationController;

    @RequestMapping("/getLessonCertificationTypes")
    ResponseStatusDTO getLessonCertificationTypes(){
        return  lessonCertificationController.getLessonCertificationTypes();
    }

    @RequestMapping("/setLessonCertificationType")
    public  ResponseStatusDTO setCertificationType(@RequestBody LessonCertificationDTO lessonCertificationDTO){
        return this.lessonCertificationController.setCertificationType(lessonCertificationDTO);
    }

    @RequestMapping("/getLessonCertificationList/{professorId}")
    public  ResponseStatusDTO getLessonCertificationList(@PathVariable("professorId") long professorId,
                                                         @RequestParam int semester,
                                                         @RequestParam int year){
        return this.lessonCertificationController.getLessonsByLessonIdAndProfessor(professorId,semester,year);
    }
    @RequestMapping("getLessonCertification/{lessonId}")
    public ResponseStatusDTO getLessonCertification(@PathVariable("lessonId") long lessonId){
        return this.lessonCertificationController.getLessonCertification(lessonId);
    }

    @RequestMapping("/setMaxScore")
    public ResponseStatusDTO setMaximunCertificationScore(@RequestBody LessonCertificationDTO lessonCertificationDTO){
        return  this.lessonCertificationController.setMaximumCertificationScore(lessonCertificationDTO);
    }

    @RequestMapping("/getLessonCertificationResult")
    public ResponseStatusDTO getLessonCertificationResult(@RequestBody LessonCertificationDTO lessonCertificationDTO){
        return  this.lessonCertificationController.getLessonCertificationResult(lessonCertificationDTO);
    }

    @RequestMapping("/getLessonEvents/{lessonId}")
    public ResponseStatusDTO getLessonCertificationResult(@PathVariable("lessonId") long lessonId){
        return  this.lessonCertificationController.getLessonEvents(lessonId);
    }

    @RequestMapping("/saveLessonCertificationResult")
    public ResponseStatusDTO saveLessonCertificationResult(@RequestBody List<LessonCertificationResultDTO> lessonCertificationResultDTOList){

        return this.lessonCertificationController.saveLessonCertificationResult(lessonCertificationResultDTOList);
    }
}
