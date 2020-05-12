package com.unesco.core.controller;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.LessonCertificationDTO;
import com.unesco.core.dto.certification.LessonCertificationResultDTO;
import com.unesco.core.dto.enums.StatusTypes;
import com.unesco.core.services.dataService.journal.certification.ILessonCertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class LessonCertificationController {
    @Autowired
    ILessonCertificationService lessonCertificationService;

    public ResponseStatusDTO getLessonCertificationTypes(){
        ResponseStatusDTO result=new ResponseStatusDTO(StatusTypes.OK);
        try{
            result.setData(lessonCertificationService.getLessonCertificationTypes());
        }catch (Exception e){

        }
        return  result;
    }

    public  ResponseStatusDTO setCertificationType(LessonCertificationDTO lessonCertificationDTO){
        return  lessonCertificationService.setLessonCertificationType(lessonCertificationDTO);
    }

    public ResponseStatusDTO getLessonsByLessonIdAndProfessor(long professorId,int semestr,int year){
        return this.lessonCertificationService.getLessonsByLessonIdAndProfessor(professorId,semestr,year);
    }

    public ResponseStatusDTO setMaximumCertificationScore(@RequestBody LessonCertificationDTO lessonCertificationDTO){
        return  this.lessonCertificationService.setMaximumCertificationScore(lessonCertificationDTO);
    }

    public ResponseStatusDTO getLessonCertificationResult(@RequestBody LessonCertificationDTO lessonCertificationDTO){
        return  this.lessonCertificationService.getLessonCertificationResult(lessonCertificationDTO);
    }

    public ResponseStatusDTO getLessonCertification( long lessonId){
        return  this.lessonCertificationService.getLessonCertification(lessonId);
    }

    public  ResponseStatusDTO getLessonEvents(long lessonId){
        return this.lessonCertificationService.getLessonEvents(lessonId);
    }
    public  ResponseStatusDTO saveLessonCertificationResult(List<LessonCertificationResultDTO> lessonCertificationResultDTOS){
        return this.lessonCertificationService.saveLessonCertificationResult(lessonCertificationResultDTOS);
    }
}
