package com.unesco.core.controller;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.CertificationDTO;
import com.unesco.core.dto.certification.LessonCertificationDTO;
import com.unesco.core.dto.certification.LessonCertificationResultDTO;
import com.unesco.core.dto.enums.StatusTypes;
import com.unesco.core.dto.journal.CertificationReportDto;
import com.unesco.core.dto.journal.JournalDTO;
import com.unesco.core.dto.journal.VisitationConfigDTO;
import com.unesco.core.dto.report.ReportAcademicPerformanceDto;
import com.unesco.core.dto.shedule.LessonDTO;
import com.unesco.core.managers.journal.VisitationConfigManager.interfaces.IVisitationConfigManager;
import com.unesco.core.managers.journal.journalManager.interfaces.journal.IJournalManager;
import com.unesco.core.managers.journal.lessonEvent.interfaces.lessonEvent.ILessonEventManager;
import com.unesco.core.managers.journal.lessonEvent.interfaces.lessonEventList.ILessonEventListManager;
import com.unesco.core.services.dataService.account.professorService.ProfessorDataService;
import com.unesco.core.services.dataService.account.studentService.StudentDataService;
import com.unesco.core.services.dataService.journal.certification.ICertificationService;
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
    private ICertificationService certificationService;
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


    //---------------------------------------------------------------------------------------------------
    public ResponseStatusDTO getCertificationReport(long lessonId, Date start, Date end, int semester, int year,String visitationOnly) {

        return new ResponseStatusDTO(StatusTypes.OK,lessonCertificationService.CertificationReportDto(lessonId,start,end,visitationOnly));
    }

    public ResponseStatusDTO getReportAcademicPerformance(long user_id,int semester, int year){

        ReportAcademicPerformanceDto reportAcademicPerformanceDto=new ReportAcademicPerformanceDto();
        reportAcademicPerformanceDto.setProfessor(professorDataService.getByUser(user_id));

        List<CertificationReportDto>lessonList=new ArrayList<CertificationReportDto>();

        Date today=new Date();
        //что бы добавить временное ограничение нужно просто фильровать по датам котрольные точки
        for(LessonDTO lessonDTO : lessonDataService.getByProfessorId(reportAcademicPerformanceDto.getProfessor().getId(),semester,year)){
            JournalDTO journal = journalDataService.get(lessonDTO.getId(), null, semester, year);
            journal.setMaxValue(lessonEventDataService.getSumMaxValueBetweenDates(lessonDTO.getId(),educationPeriodService.getEducationPeriodForYearAndSemester(semester,year).getStartDate(), today));
            CertificationReportDto result = lessonCertificationService.CertificationReportDto(lessonDTO.getId(),educationPeriodService.getEducationPeriodForYearAndSemester(semester,year).getStartDate(), today,"0");
            result.setAllEventValue(lessonEventDataService.getSumMaxValueBetweenDates(lessonDTO.getId(),educationPeriodService.getEducationPeriodForYearAndSemester(semester,year).getStartDate(), today));
            result.setLesson(lessonDTO);
            lessonList.add(result);
        }

        reportAcademicPerformanceDto.setLessonList(lessonList);

        return new ResponseStatusDTO(StatusTypes.OK, reportAcademicPerformanceDto);
    }

    public ResponseStatusDTO getStudentResults(long lessonId){

        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        responseStatusDTO.setData(lessonCertificationService.getStudentResults(lessonId,null));
        return responseStatusDTO;
    }

    public ResponseStatusDTO getCertificationStudent(long student_id,long lesson_id){

        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);


            responseStatusDTO.setData(lessonCertificationService.geEventsForStudent(student_id,lesson_id));


         return responseStatusDTO;
    }

    //------------------------------------------------------------------------------------------------------------------
    public  ResponseStatusDTO getCertification(long lessonId){
        ResponseStatusDTO answer=new ResponseStatusDTO(StatusTypes.OK);
        answer.setData(certificationService.getCertificationListByLessonId(lessonId));
        return  answer;
    }

    public ResponseStatusDTO saveCertification(CertificationDTO certification){
        return this.certificationService.saveCertification(certification);
    }

    public  ResponseStatusDTO deleteCertification(CertificationDTO certification){
        ResponseStatusDTO answer=new ResponseStatusDTO(StatusTypes.OK);
        this.certificationService.deleteCertification(certification);
        return answer;
    }

    //------------------------------------------------------------------------------------------------

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

    public ResponseStatusDTO getLessonsByLessonIdAndProfessor(long professorId,int semester,int year){
        return this.lessonCertificationService.getLessonCertificationsByProfessorIdAndSemesterAndYear(professorId,semester,year);
    }

    public ResponseStatusDTO setMaximumCertificationScore(LessonCertificationDTO lessonCertificationDTO){
        return  this.lessonCertificationService.setMaximumCertificationScore(lessonCertificationDTO);
    }

    public ResponseStatusDTO getLessonCertificationResult(LessonCertificationDTO lessonCertificationDTO,String currentOnly){
        return  this.lessonCertificationService.getLessonCertificationResult(lessonCertificationDTO,currentOnly.equals("1") ? true : false);
    }

    public ResponseStatusDTO getLessonCertification( long lessonId){
        return  this.lessonCertificationService.getLessonCertification(lessonId);
    }

    public  ResponseStatusDTO getLessonEvents(long lessonId,int semester,int year){
        return this.lessonCertificationService.getLessonEvents(lessonId,semester,year);
    }

    public  ResponseStatusDTO saveLessonCertificationResult(List<LessonCertificationResultDTO> lessonCertificationResultDTOS){
        return this.lessonCertificationService.saveLessonCertificationResult(lessonCertificationResultDTOS);
    }

    public ResponseStatusDTO getNewLessonCertificationResult(long lessonId,LessonCertificationResultDTO lessonCertificationResultDTO,int semester,int year,String currentOnly){
        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);

        responseStatusDTO.setData(this.lessonCertificationService.getNewLessonCertificationResultDTO(lessonId,lessonCertificationResultDTO,semester,year, currentOnly.equals("1") ? true: false ));
        return responseStatusDTO;
    }

}
