package com.unesco.core.controller;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.CurrentCertificationDTO;
import com.unesco.core.dto.certification.IntermediateCertificationDTO;
import com.unesco.core.dto.certification.IntermediateCertificationResultDTO;
import com.unesco.core.dto.enums.StatusTypes;
import com.unesco.core.dto.journal.CurrentCertificationDto;
import com.unesco.core.dto.journal.JournalDTO;
import com.unesco.core.dto.report.ReportAcademicPerformanceDto;
import com.unesco.core.dto.shedule.LessonDTO;
import com.unesco.core.managers.journal.VisitationConfigManager.interfaces.IVisitationConfigManager;
import com.unesco.core.managers.journal.journalManager.interfaces.journal.IJournalManager;
import com.unesco.core.managers.journal.lessonEvent.interfaces.lessonEvent.ILessonEventManager;
import com.unesco.core.managers.journal.lessonEvent.interfaces.lessonEventList.ILessonEventListManager;
import com.unesco.core.managers.monitoringStudentsProgress.IMonitoringStudentsManager;
import com.unesco.core.services.dataService.account.professorService.ProfessorDataService;
import com.unesco.core.services.dataService.account.studentService.StudentDataService;
import com.unesco.core.services.dataService.journal.certification.ICurrentCertificationDataService;
import com.unesco.core.services.dataService.journal.certification.LessonCertificationService;
import com.unesco.core.services.dataService.journal.journal.IJournalDataService;
import com.unesco.core.services.dataService.journal.lessonEvent.ILessonEventDataService;
import com.unesco.core.services.dataService.journal.point.IPointDataService;
import com.unesco.core.services.dataService.journal.visitation.IVisitationConfigDataService;
import com.unesco.core.services.dataService.plan.educationPeriodService.EducationPeriodService;
import com.unesco.core.services.dataService.schedule.lessonService.ILessonDataService;
import com.unesco.core.services.dataService.schedule.pairService.IPairDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MonitoringStudentsProgressController {


    @Autowired
    private IJournalDataService journalDataService;
    @Autowired
    private ILessonEventDataService lessonEventDataService;
    @Autowired
    private IJournalManager journalManager;
    @Autowired
    private ILessonEventListManager lessonEventListManager;
    @Autowired
    private IVisitationConfigManager visitationConfigManager;
    @Autowired
    private ILessonEventManager lessonEventManager;
    @Autowired
    private IPointDataService pointDataService;
    @Autowired
    private ILessonDataService lessonDataService;
    @Autowired
    private ProfessorDataService professorDataService;
    @Autowired
    private EducationPeriodService educationPeriodService;
    @Autowired
    private IVisitationConfigDataService visitationConfigDataService;
    @Autowired
    private LessonCertificationService lessonCertificationService;
    @Autowired
    private IPairDataService pairDataService;
    @Autowired
    private StudentDataService studentDataService;

    //==================================================================================================================

    @Autowired
    private IMonitoringStudentsManager monitoringStudentsManager;

    @Autowired
    private ICurrentCertificationDataService certificationDataService;

    //===================================Текущая аттестация=============================================================

    public ResponseStatusDTO getCurrentCertification(long lessonId, Date start, Date end) {
        LessonDTO lessonDTO=lessonDataService.get(lessonId);
        JournalDTO journal = journalDataService.get(lessonDTO.getId(), null, lessonDTO.getSemesterNumberYear().getSemester(),lessonDTO.getSemesterNumberYear().getYear());
        monitoringStudentsManager.init(journal,lessonEventDataService.getByLesson(lessonId));

        return new ResponseStatusDTO(StatusTypes.OK,monitoringStudentsManager.CurrentCertification(start,end));
    }

    public  ResponseStatusDTO getCurrentCertificationListForLesson(long lessonId){
        return  new ResponseStatusDTO(StatusTypes.OK,certificationDataService.getCurrentCertifications(lessonId));
    }

    public ResponseStatusDTO saveCurrentCertification(CurrentCertificationDTO certification){
        return this.certificationDataService.saveCurrentCertification(certification);
    }

    public  ResponseStatusDTO deleteCurrentCertification(CurrentCertificationDTO certification){
        return new ResponseStatusDTO(StatusTypes.OK,this.certificationDataService.deleteCurrentCertification(certification));
    }

    //--------------------------------Отчет по успеваемости-------------------------------------------------------------

    public ResponseStatusDTO getReportAcademicPerformance(long user_id,int semester, int year){

        ReportAcademicPerformanceDto reportAcademicPerformanceDto=new ReportAcademicPerformanceDto();

        reportAcademicPerformanceDto.setProfessor(professorDataService.getByUser(user_id));

        List lessonList=new ArrayList<CurrentCertificationDto>();

        List<LessonDTO> lessonDTOS=lessonDataService.getByProfessorId(professorDataService.getByUser(user_id).getId(),semester,year);

        for(LessonDTO lessonDTO : lessonDTOS) {
            JournalDTO journal = journalDataService.get(lessonDTO.getId(), null, semester, year);
            monitoringStudentsManager.init(journal,lessonEventDataService.getByLesson(lessonDTO.getId()));
            lessonList.add(monitoringStudentsManager.Report());
        }
        reportAcademicPerformanceDto.setLessonList(lessonList);

        return new ResponseStatusDTO(StatusTypes.OK, reportAcademicPerformanceDto);
    }

    //===================================Промежуточная аттестация=======================================================

    public ResponseStatusDTO getLessonCertificationTypes(){
        ResponseStatusDTO result=new ResponseStatusDTO(StatusTypes.OK);
        try{
            result.setData(lessonCertificationService.getLessonCertificationTypes());
        }catch (Exception e){
            result.setStatus(StatusTypes.ERROR);
        }
        return  result;
    }

    public  ResponseStatusDTO setIntermediateCertificationType(IntermediateCertificationDTO intermediateCertificationDTO){
        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);

        try {
            responseStatusDTO.setData(lessonCertificationService.setLessonCertificationType(intermediateCertificationDTO));
        }catch (Exception e){
            responseStatusDTO.setStatus(StatusTypes.ERROR);
        }
        return  responseStatusDTO;
    }

    public ResponseStatusDTO getLessonsByLessonIdAndProfessor(long professorId,int semester,int year){
        return this.lessonCertificationService.getLessonCertificationsByProfessorIdAndSemesterAndYear(professorId,semester,year);
    }

    public ResponseStatusDTO setMaximumCertificationScore(IntermediateCertificationDTO intermediateCertificationDTO){
        return  this.lessonCertificationService.setMaximumCertificationScore(intermediateCertificationDTO);
    }

    public  ResponseStatusDTO saveLessonCertificationResult(List<IntermediateCertificationResultDTO> intermediateCertificationResultDTOS){
        return this.lessonCertificationService.saveLessonCertificationResult(intermediateCertificationResultDTOS);
    }

    public ResponseStatusDTO getNewLessonCertificationResult(long lessonId, IntermediateCertificationResultDTO intermediateCertificationResultDTO, String currentOnly){
        LessonDTO lessonDTO=lessonDataService.get(lessonId);
        JournalDTO journal = journalDataService.get(lessonDTO.getId(), null, lessonDTO.getSemesterNumberYear().getSemester(),lessonDTO.getSemesterNumberYear().getYear());
        IntermediateCertificationDTO intermediateCertificationDTO=lessonCertificationService.getIntermediateCertification(lessonId);
        monitoringStudentsManager.init(intermediateCertificationDTO,journal,lessonEventDataService.getByLesson(lessonId));
        IntermediateCertificationResultDTO res=monitoringStudentsManager.updateIntermediateCertificationForResult(intermediateCertificationResultDTO,currentOnly.equals("0") ? false : true);
        return new ResponseStatusDTO(StatusTypes.OK,res);
    }

    public ResponseStatusDTO getIntermediateCertification(long lessonId){

        LessonDTO lessonDTO=lessonDataService.get(lessonId);
        JournalDTO journal = journalDataService.get(lessonDTO.getId(), null, lessonDTO.getSemesterNumberYear().getSemester(),lessonDTO.getSemesterNumberYear().getYear());
        IntermediateCertificationDTO intermediateCertificationDTO=lessonCertificationService.getIntermediateCertification(lessonId);
        monitoringStudentsManager.init(intermediateCertificationDTO,journal,lessonEventDataService.getByLesson(lessonId));

        return  new ResponseStatusDTO(StatusTypes.OK,monitoringStudentsManager.IntermediateCertification(false));
    }
}
