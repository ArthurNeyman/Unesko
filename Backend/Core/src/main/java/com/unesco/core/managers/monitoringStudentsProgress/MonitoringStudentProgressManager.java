package com.unesco.core.managers.monitoringStudentsProgress;

import com.unesco.core.dto.account.StudentDTO;
import com.unesco.core.dto.certification.*;
import com.unesco.core.dto.enums.PointTypes;
import com.unesco.core.dto.journal.*;
import com.unesco.core.utils.DateHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;



@Component
@Scope("prototype")
public class MonitoringStudentProgressManager implements  IMonitoringStudentsManager {

    private  JournalDTO journal;
    private  List<LessonEventDTO> events;
    private IntermediateCertificationDTO intermediateCertification;
    @Override
    public void init(JournalDTO journal, List<LessonEventDTO> lessonEventDTOList) {
        this.events=lessonEventDTOList;
        this.journal=journal;
    }

    public void init(IntermediateCertificationDTO intermediateCertificationDTO,JournalDTO journal, List<LessonEventDTO> lessonEventDTOList) {
        this.events=lessonEventDTOList;
        this.journal=journal;
        this.intermediateCertification=intermediateCertificationDTO;
    }

    @Override//Текущая аттестация
    public CurrentCertificationDto CurrentCertification(Date start, Date end) {

        CurrentCertificationDto certification=new CurrentCertificationDto();

        certification.setLesson(journal.getLesson());

        certification.setAllEventValue(getMaxEventValue(start,end));

        certification.setAllHours(getMaxVisitCount(start,end)*2);

        List<CurrentCertificationValueDto> studList=new ArrayList<CurrentCertificationValueDto>();

        journal.getStudents().forEach(student->{
            CurrentCertificationValueDto valueDto=new CurrentCertificationValueDto();

            valueDto.setStudent(student.getStudent());

            valueDto.setMissingHours((getMaxVisitCount(start,end) - getVisitationValueForStudent(student.getStudent(),start,end).mapToInt(PointDTO::getValue).count())*2);

            int value= getEventsValueForStudent(student.getStudent(), start, end) ;

            valueDto.setCertificationValue(getCurrentCertificationValueByMaxValueAndCurrentValue((int)(certification.getAllEventValue()),value));

            studList.add(valueDto);
        });


        certification.setStudentCertification(studList);

        return certification;
    }

    @Override//Отчет по успеваемости
    public ReportElement Report(){

        ReportElement reportElement=new ReportElement();

        reportElement.setLesson(journal.getLesson());

        reportElement.setMaxVisitationValue(getMaxVisitCount());////Нужно получить максимальный балл,а не количество
        reportElement.setMaxEventValue(getMaxEventValue());

        List<ReportElementValue> reportElementValues=new ArrayList<>();

        journal.getStudents().forEach(student->{

            ReportElementValue reportElementValue=new ReportElementValue();
            reportElementValue.setStudent(student.getStudent());
            reportElementValue.setEventValue(getEventsValueForStudent(student.getStudent()));
            reportElementValue.setVisitationValue(getVisitationValueForStudent(student.getStudent()).mapToInt(PointDTO::getValue).sum());

            reportElementValues.add(reportElementValue);
        });
        reportElement.setInfoList(reportElementValues);

        return reportElement;
    }

    //Промежуточная аттестация
    public IntermediateCertificationDTO IntermediateCertification(boolean currentOnly){

        intermediateCertification.setEvents(getLessonEvents());

        List<IntermediateCertificationResultDTO> studList=new ArrayList<>();

        journal.getStudents().forEach(student->{
                IntermediateCertificationResultDTO intermediateCertificationResultDTO =new IntermediateCertificationResultDTO();

                intermediateCertificationResultDTO.setLessonCertificationId(intermediateCertification.getId());

                intermediateCertificationResultDTO.setStudentDTO(student.getStudent());

                intermediateCertificationResultDTO.setEvents(getEvents(student));

                intermediateCertificationResultDTO.setCurrentScore(getEventsValueForStudent(student.getStudent())+getVisitationValueForStudent(student.getStudent()).mapToInt(PointDTO::getValue).sum());

                intermediateCertificationResultDTO.setCertificationScore(0);

                intermediateCertificationResultDTO.setAbsence(false);
                intermediateCertificationResultDTO.setRatingDate(null);

                intermediateCertificationResultDTO =getTotalScore(intermediateCertificationResultDTO,currentOnly);
                intermediateCertificationResultDTO.setMark(getMarkForLessonCertificationResult(intermediateCertificationResultDTO));

                studList.add(intermediateCertificationResultDTO);
        });

        intermediateCertification.setStudList(studList);

        return intermediateCertification;
    }

    public  IntermediateCertificationResultDTO updateIntermediateCertificationForResult(IntermediateCertificationResultDTO intermediateCertificationResultDTO,boolean currentOnly){
        intermediateCertificationResultDTO=getTotalScore(intermediateCertificationResultDTO,currentOnly);
        intermediateCertificationResultDTO.setMark(getMarkForLessonCertificationResult(intermediateCertificationResultDTO));
        return  intermediateCertificationResultDTO;
    }

    //Получить список мерприятий
    private List<IntermediateCertificationEventDTO> getLessonEvents(){

        List<IntermediateCertificationEventDTO> eventsCert=new ArrayList<>();

        events.forEach(event->{
            eventsCert.add(new IntermediateCertificationEventDTO(event.getComment(),event.getType().getName(),event.getMaxValue()));
        });

        eventsCert.add(new IntermediateCertificationEventDTO("Посещение","Посещение",getMaxVisitCount()));
        eventsCert.add(new IntermediateCertificationEventDTO("Всего баллов","Всего баллов",getMaxVisitCount()+getMaxEventValue()));

        return  eventsCert;
    }

    //получить общий балл
    private IntermediateCertificationResultDTO getTotalScore(IntermediateCertificationResultDTO intermediateCertificationResultDTO, boolean currentScoreOnly){

        float maxValue=0;
        float resultTotalScore=0;

        long lessonCertificationTypeId=intermediateCertification.getLessonCertificationType().getId();

        maxValue=getMaxEventValue()+getMaxVisitCount();

        if(maxValue>0)
            resultTotalScore+=(lessonCertificationTypeId == 1 || lessonCertificationTypeId == 3 ?
                    (!currentScoreOnly && intermediateCertification.getMaxCertificationScore() != 0 ? 60 :100) :
                    (!currentScoreOnly && intermediateCertification.getMaxCertificationScore() != 0 ? 80:100 ))
                    / maxValue * intermediateCertificationResultDTO.getCurrentScore();

        if(intermediateCertification.getMaxCertificationScore()!=0)
            resultTotalScore+= (lessonCertificationTypeId == 1 || lessonCertificationTypeId == 3 ?
                    ( !currentScoreOnly ? 40 : 0 ) : (!currentScoreOnly ? 20 : 0)) /
                    intermediateCertification.getMaxCertificationScore() * intermediateCertificationResultDTO.getCertificationScore();

        intermediateCertificationResultDTO.setTotalScore(Math.round(resultTotalScore));
        return intermediateCertificationResultDTO;
    }

    //получить оценку,в зависимости от общего балла
    private String getMarkForLessonCertificationResult(IntermediateCertificationResultDTO intermediateCertificationResultDTO){

        long typeId = this.intermediateCertification.getLessonCertificationType().getId();

        if(typeId==1 || typeId==3){
            if(intermediateCertificationResultDTO.getTotalScore()>50){
                if(intermediateCertificationResultDTO.getTotalScore()>65){
                    if(intermediateCertificationResultDTO.getTotalScore()>85){
                        return "Отлично";
                    }else{
                        return "Хорошо";
                    }
                }else{
                    return "Удовлетворительно";
                }
            }else{
                return  "Неудовлетворительно";
            }
        }else if(typeId==2){
            if(intermediateCertificationResultDTO.getTotalScore()>50){
                return "Зачтено";
            }else{
                return  "Не зачтено";
            }
        }

        return " ";
    }

    //Получить список мероприятий и баллы за них для студента
    private List<IntermediateCertificationEventDTO> getEvents(StudentDTO studentDTO){

        List<IntermediateCertificationEventDTO> eventDTOS=new ArrayList<>();

        events.forEach(event->{
            event.getPairs().forEach(pair->{

            });
            int value=journal.getJournalCell()
                    .stream()
                    .filter(pointDTO -> pointDTO.getStudentId()==studentDTO.getId()
                            && pointDTO.getType().getId()==event.getType().getId()
                            && DateHelper.getZeroTimeDate(pointDTO.getDate()).equals(DateHelper.getZeroTimeDate(event.getDate())))
                    .mapToInt(PointDTO::getValue).sum();

            eventDTOS.add(new IntermediateCertificationEventStudentDTO(event.getComment(),event.getType().getName(),event.getMaxValue(),value));
        });

        eventDTOS.add(new IntermediateCertificationEventStudentDTO("Посещение","Посещение",getMaxVisitCount(),getVisitationValueForStudent(studentDTO).mapToInt(PointDTO::getValue).sum()));
        eventDTOS.add(new IntermediateCertificationEventStudentDTO("Всего баллов","Всего баллов",getMaxEventValue()+getMaxVisitCount(),getEventsValueForStudent(studentDTO)+getVisitationValueForStudent(studentDTO).mapToInt(PointDTO::getValue).sum()));

        return eventDTOS;
    }

    //Если у разных пдогрупп контрольные точки в разные дни, то общий балл не правильный !!!! и вообще хз как это сверять

    private List<IntermediateCertificationEventDTO> getEvents(StudentJournalDTO studentDTO){

        List<IntermediateCertificationEventDTO> eventDTOS=new ArrayList<>();

        events.forEach(event->{
                int value=journal.getJournalCell()
                        .stream()
                        .filter(pointDTO -> pointDTO.getStudentId()==studentDTO.getStudent().getId()
                                && pointDTO.getType().getId()==event.getType().getId()
                                && DateHelper.getZeroTimeDate(pointDTO.getDate()).equals(DateHelper.getZeroTimeDate(event.getDate())))
                        .mapToInt(PointDTO::getValue).sum();
                eventDTOS.add(new IntermediateCertificationEventStudentDTO(event.getComment(),event.getType().getName(),event.getMaxValue(),value));
            });


        eventDTOS.add(new IntermediateCertificationEventStudentDTO("Посещение","Посещение",getMaxVisitCount(),getVisitationValueForStudent(studentDTO.getStudent()).mapToInt(PointDTO::getValue).sum()));
        eventDTOS.add(new IntermediateCertificationEventStudentDTO("Всего баллов","Всего баллов",getMaxEventValue()+getMaxVisitCount(),getEventsValueForStudent(studentDTO.getStudent())+getVisitationValueForStudent(studentDTO.getStudent()).mapToInt(PointDTO::getValue).sum()));

        return eventDTOS;
    }

    //Доп методы
    private Stream<PointDTO> getVisitationValueForStudent(StudentDTO student){
       return  journal.getJournalCell().stream().
                filter(pointDTO -> pointDTO.getStudentId()==student.getId()
                        && pointDTO.getType().getName().equals(PointTypes.Visitation.toString()));
    }

    private  Stream<PointDTO> getVisitationValueForStudent(StudentDTO student, Date start, Date end){
        return  journal.getJournalCell().stream().
                filter(pointDTO -> pointDTO.getStudentId()==student.getId()
                        && pointDTO.getType().getName().equals(PointTypes.Visitation.toString()));
    }

    private int getMaxEventValue(){
        return events.stream()
                .mapToInt(LessonEventDTO::getMaxValue).sum();
    }

    private int getMaxEventValue(Date start,Date end){
        return events.stream()
                .filter(el->el.getDate().after(start) && el.getDate().before(end))
                .mapToInt(LessonEventDTO::getMaxValue).sum();
    }

    private int getEventsValueForStudent(StudentDTO studentDTO, Date start, Date end){
        return journal.getJournalCell().stream().
                filter(pointDTO -> pointDTO.getDate().after(start)
                        && pointDTO.getDate().before(end)
                        && pointDTO.getStudentId()==studentDTO.getId()
                        && !pointDTO.getType().getName().equals(PointTypes.Visitation.toString())).mapToInt(PointDTO::getValue).sum();
    }

    //Учитываются баллы если контрольные точки удалены
    private int getEventsValueForStudent(StudentDTO studentDTO){
        return journal.getJournalCell().stream().
                filter(pointDTO -> pointDTO.getStudentId()==studentDTO.getId()
                        && !pointDTO.getType().getName().equals(PointTypes.Visitation.toString())).mapToInt(PointDTO::getValue).sum();
    }

    private int getMaxVisitCount(Date start, Date end){

        int mustBeCount=0;

        for (ComparisonDTO comparisonDTO : journal.getComparison()) {
            for (ComparisonPointDTO comparisonPointDTO : comparisonDTO.getPoints()) {
                if((comparisonPointDTO.getPair().getSubgroup()==0 || comparisonPointDTO.getPair().getSubgroup()==1)
                        && (comparisonDTO.getDate().after(start) && comparisonDTO.getDate().before(end) ) ){
                    mustBeCount++;
                }
            }
        }
        return  mustBeCount;
    }

    private int getMaxVisitCount(){

        int mustBeCount=0;

        for (ComparisonDTO comparisonDTO : journal.getComparison()) {
            for (ComparisonPointDTO comparisonPointDTO : comparisonDTO.getPoints()) {
                if((comparisonPointDTO.getPair().getSubgroup()==0 || comparisonPointDTO.getPair().getSubgroup()==1))
                    mustBeCount++;
            }
        }
        return  mustBeCount;
    }

    private int getCurrentCertificationValueByMaxValueAndCurrentValue(int maxValue,int currentValue){
        double res=(double)currentValue/maxValue;
        if(res>0.5)
            if(res>0.8)
                return 2;
            else  return 1;
        else return 0;
    }

}
