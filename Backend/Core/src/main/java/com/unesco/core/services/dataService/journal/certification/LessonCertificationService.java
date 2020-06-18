package com.unesco.core.services.dataService.journal.certification;

import com.unesco.core.dto.account.StudentDTO;
import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.*;
import com.unesco.core.dto.enums.PointTypes;
import com.unesco.core.dto.enums.StatusTypes;
import com.unesco.core.dto.journal.*;
import com.unesco.core.dto.shedule.LessonDTO;
import com.unesco.core.dto.shedule.PairDTO;
import com.unesco.core.entities.certification.LessonCertificationEntity;
import com.unesco.core.entities.certification.LessonCertificationResultEntity;
import com.unesco.core.entities.schedule.LessonEntity;
import com.unesco.core.managers.journal.VisitationConfigManager.interfaces.IVisitationConfigManager;
import com.unesco.core.managers.journal.lessonEvent.interfaces.lessonEventList.ILessonEventListManager;
import com.unesco.core.repositories.account.StudentRepository;
import com.unesco.core.repositories.certification.LessonCertificationRepository;
import com.unesco.core.repositories.certification.LessonCertificationResultRepository;
import com.unesco.core.repositories.certification.LessonCertificationTypeRepository;
import com.unesco.core.repositories.journal.LessonEventRepository;
import com.unesco.core.repositories.journal.PointTypeRepository;
import com.unesco.core.repositories.schedule.LessonRepository;
import com.unesco.core.services.dataService.account.studentService.IStudentDataService;
import com.unesco.core.services.dataService.journal.journal.IJournalDataService;
import com.unesco.core.services.dataService.journal.lessonEvent.ILessonEventDataService;
import com.unesco.core.services.dataService.journal.point.IPointDataService;
import com.unesco.core.services.dataService.journal.visitation.IVisitationConfigDataService;
import com.unesco.core.services.dataService.plan.educationPeriodService.IEducationPeriodService;
import com.unesco.core.services.dataService.schedule.lessonService.ILessonDataService;
import com.unesco.core.services.dataService.schedule.pairService.IPairDataService;
import com.unesco.core.services.mapperService.IMapperService;
import com.unesco.core.utils.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LessonCertificationService implements  ILessonCertificationService {

    @Autowired
    private LessonCertificationResultRepository lessonCertificationResultRepository;

    @Autowired
    private LessonCertificationTypeRepository lessonCertificationTypeRepository;

    @Autowired
    private LessonCertificationRepository lessonCertificationRepository;

    @Autowired
    private PointTypeRepository pointTypeRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonEventRepository lessonEventRepository;


    @Autowired
    private IEducationPeriodService educationPeriodService;

    @Autowired
    private IMapperService mapperService;

    @Autowired
    private IPairDataService pairDataService;

    @Autowired
    private IStudentDataService studentDataService;

    @Autowired
    private IPointDataService pointDataService;

    @Autowired
    private ILessonEventDataService lessonEventDataService;

    @Autowired
    private IJournalDataService journalDataService;

    @Autowired
    private ILessonEventListManager lessonEventListManager;

    @Autowired
    private IVisitationConfigManager visitationConfigManager;

    @Autowired
    private ILessonDataService lessonDataService;

    @Autowired
    private IVisitationConfigDataService visitationConfigDataService;

    @Override//Назначить тип аттестации по предмету(Админ)
    public ResponseStatusDTO setLessonCertificationType(LessonCertificationDTO lessonCertificationDTO) {

        ResponseStatusDTO result=new ResponseStatusDTO(StatusTypes.OK);

        LessonCertificationEntity lessonCertificationEntity;
        try{
            lessonCertificationEntity=lessonCertificationRepository.save((LessonCertificationEntity)mapperService.toEntity(lessonCertificationDTO));
            lessonCertificationDTO.setId(lessonCertificationEntity.getId());
            result.setData(lessonCertificationDTO);
        }catch (Exception e){
            result.setStatus(StatusTypes.ERROR);
        }

        return result;
    }

    @Override//Получить типы аттестации по предметам(Админ)
    public List<LessonCertificationTypeDTO> getLessonCertificationTypes() {

        List<LessonCertificationTypeDTO> lessonCertificationTypeDTOS=new ArrayList<>();
        lessonCertificationTypeRepository.findAll().forEach(el->lessonCertificationTypeDTOS.add((LessonCertificationTypeDTO) mapperService.toDto(el)));

        return lessonCertificationTypeDTOS;
    }

    @Override//получить список предметов преподавателя с указанием типа аттестации
    public ResponseStatusDTO<LessonCertificationDTO> getLessonCertificationByLesson(LessonDTO lessonDTO) {

        ResponseStatusDTO result=new ResponseStatusDTO(StatusTypes.OK);
        LessonCertificationEntity lessonCertificationEntity=new LessonCertificationEntity();

        try{
            lessonCertificationEntity=lessonCertificationRepository.getByLessonId(lessonDTO.getId());
        }catch (Exception e){
            result.setStatus(StatusTypes.ERROR);
        }
        result.setData(mapperService.toDto(lessonCertificationEntity));

        return result;
    }

    @Override//Получить список студентов и данные их аттестации
    public List<LessonCertificationResultDTO> getLessonCertificationResultListByLesson(LessonDTO lessonDTO) {

        List<LessonCertificationResultDTO> lessonCertificationResultDTOS=new ArrayList<>();

        lessonCertificationResultRepository.getByLessonCertificationId(lessonDTO.getId()).forEach(el->
                lessonCertificationResultDTOS.add((LessonCertificationResultDTO)mapperService.toDto(el)));
        return lessonCertificationResultDTOS;
    }

    @Override//Получить список сущностей промежуточной аттестации для преподавателя по его id
    public ResponseStatusDTO getLessonCertificationsByProfessorIdAndSemesterAndYear(long professorId, int semester, int year){

        List<LessonCertificationDTO> lessonCertificationDTOS=new ArrayList<>();
        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        try {
            long educationPeriodId = educationPeriodService.getEducationPeriodForYearAndSemester(semester, year).getId();

            lessonRepository.findByProfessorId(professorId, educationPeriodId).forEach(
                    el -> {
                        LessonCertificationDTO lessonCertificationDTO = new LessonCertificationDTO();
                        LessonDTO lessonDTO = (LessonDTO) mapperService.toDto(el);
                        lessonCertificationDTO.setLesson(lessonDTO);
                        LessonCertificationEntity lessonCertificationEntity = lessonCertificationRepository.getByLessonId(el.getId());
                        lessonCertificationDTO.setMaxCertificationScore(lessonCertificationEntity != null ? lessonCertificationEntity.getMaxCertificationScore() : 0);

                        lessonCertificationDTO.setId(lessonCertificationEntity != null ? lessonCertificationEntity.getId() : 0);
                        lessonCertificationDTO.setLessonCertificationType(lessonCertificationEntity != null ?
                                (LessonCertificationTypeDTO) mapperService.toDto(lessonCertificationRepository.getByLessonId(el.getId()).getLessonCertificationTypeEntity())
                                : new LessonCertificationTypeDTO());
                        lessonCertificationDTOS.add(lessonCertificationDTO);
                    }
            );
            responseStatusDTO.setData(lessonCertificationDTOS);

        }catch (Exception e){
            responseStatusDTO.setStatus(StatusTypes.ERROR);
        }

        return  responseStatusDTO;
    }

    @Override//назначить максимальный аттестационный балл
    public ResponseStatusDTO setMaximumCertificationScore(LessonCertificationDTO lessonCertificationDTO){
        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        try {
            lessonCertificationRepository.save((LessonCertificationEntity) mapperService.toEntity(lessonCertificationDTO));
            responseStatusDTO.setData(lessonCertificationDTO);
        }catch (Exception e){
            responseStatusDTO.setStatus(StatusTypes.ERROR);
        }
        return responseStatusDTO;
    }

    //получить список результатов аттестации
    public ResponseStatusDTO getLessonCertificationResult(LessonCertificationDTO lessonCertificationDTO,boolean currentCertificationOnly){

        int semester=lessonCertificationDTO.getLesson().getSemesterNumberYear().getSemester();
        int year=lessonCertificationDTO.getLesson().getSemesterNumberYear().getYear();

        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);

        List<LessonCertificationResultDTO> lessonCertificationResultDTOS=new ArrayList<>();

            studentDataService.getByGroup(lessonCertificationDTO.getLesson().getGroup().getId())
                    .forEach(el->{

                        LessonCertificationResultDTO lessonCertificationResultDTO=new LessonCertificationResultDTO();

                        lessonCertificationResultDTO.setStudentDTO(el);
                        lessonCertificationResultDTO.setLessonCertificationId(lessonCertificationDTO.getId());
                        lessonCertificationResultDTO.setAbsence(false);

                        StudentEventDTO studentEventDTO= geEventsForStudent(el.getId(),lessonCertificationDTO.getLesson().getId());

                        int currentScore=0;

                        for( LessonEventValueDTO res: studentEventDTO.getEvents())
                            currentScore+=res.getValue();
                        currentScore/=2;

                        lessonCertificationResultDTO.setCurrentScore(currentScore);
                        LessonCertificationResultDTO lessonCertificationResultDTO1=(LessonCertificationResultDTO) mapperService.toDto(lessonCertificationResultRepository.getLessonCertificationResultByStudentEntityIdAndLessonCertificationId(el.getId(),lessonCertificationDTO.getLesson().getId()));

                        int certificationScore = lessonCertificationResultDTO1!=null ? lessonCertificationResultDTO1.getCertificationScore():0  ;

                        lessonCertificationResultDTO.setCertificationScore(certificationScore);
                        lessonCertificationResultDTO.setTotalScore(getTotalScore(lessonCertificationDTO.getLesson().getId(),lessonCertificationResultDTO,semester,year,currentCertificationOnly));
                        lessonCertificationResultDTO.setMark(getMarkForLessonCertificationResult(lessonCertificationResultDTO));
                        lessonCertificationResultDTOS.add(lessonCertificationResultDTO);
                    }
            );
            responseStatusDTO.setData(lessonCertificationResultDTOS);


        return responseStatusDTO;
    }

    //получить оценку,в зависимости от общего балла
    public String getMarkForLessonCertificationResult(LessonCertificationResultDTO lessonCertificationResultDTO){

        long typeId=lessonCertificationRepository.findOne(lessonCertificationResultDTO.getLessonCertificationId()).getLessonCertificationTypeEntity().getId();

        if(typeId==1 || typeId==3){
            if(lessonCertificationResultDTO.getTotalScore()>50){
                if(lessonCertificationResultDTO.getTotalScore()>65){
                    if(lessonCertificationResultDTO.getTotalScore()>85){
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
            if(lessonCertificationResultDTO.getTotalScore()>50){
                return "Зачтено";
            }else{
                return  "Не зачтено";
            }
        }

        return " ";
    }

    //получить общий балл
    public float getTotalScore(long lessonId,LessonCertificationResultDTO lessonCertificationResultDTO,int semester,int year,boolean currentScoreOnly){

//        LessonDTO lessonDTO=lessonDataService.get(lessonId);
//        int semester=lessonDTO.getSemesterNumberYear().getSemester();
//        int year=lessonDTO.getSemesterNumberYear().getYear();

        LessonCertificationEntity lessonCertificationEntity=lessonCertificationRepository.findOne(lessonCertificationResultDTO.getLessonCertificationId());
        long lessonCertificationTypeId=lessonCertificationEntity.getLessonCertificationTypeEntity().getId();
        float maxValue=0;
        float resultTotalScore=0;

        Map<String,Integer> map=(Map<String,Integer>)getLessonEvents(lessonId,semester,year).getData();

        for(int val : map.values())
            maxValue+=val;
        maxValue/=2;

        if(maxValue>0)
            resultTotalScore+=(lessonCertificationTypeId == 1 || lessonCertificationTypeId == 3 ?
                    (!currentScoreOnly && lessonCertificationEntity.getMaxCertificationScore() != 0 ? 60 :100) :
                    (!currentScoreOnly && lessonCertificationEntity.getMaxCertificationScore() != 0 ? 80:100 ))
                    / maxValue * lessonCertificationResultDTO.getCurrentScore();

        if(lessonCertificationEntity.getMaxCertificationScore()!=0)
            resultTotalScore+= (lessonCertificationTypeId == 1 || lessonCertificationTypeId == 3 ?
                    ( !currentScoreOnly ? 40 : 0 ) : (!currentScoreOnly ? 20 : 0)) /
                    lessonCertificationEntity.getMaxCertificationScore() * lessonCertificationResultDTO.getCertificationScore();

        return Math.round(resultTotalScore);
    }

    //получить список мероприятий с текущими и максимальными баллами для всей группы если student_id=null,иначе для указаного студента
    public List<LessonEventResultDTO> getStudentResults(long lessonId, Long student_id){

        Map<StudentJournalDTO,Integer> map=new HashMap<>();
        List<LessonEventResultDTO> list=new ArrayList<>();

        LessonDTO lessonDTO=lessonDataService.get(lessonId);
//-----------------------------------------------------------------------------------------------------------------------
        JournalDTO journal = journalDataService.get(lessonId, null, lessonDTO.getSemesterNumberYear().getSemester(),lessonDTO.getSemesterNumberYear().getYear());

        VisitationConfigDTO visitConfig = visitationConfigDataService.getByLesson(lessonId);
        visitationConfigManager.init(visitConfig);

        List<LessonEventDTO> lessonEvents = lessonEventDataService.getAll();
        lessonEventListManager.init(lessonEvents);

        Date start=educationPeriodService.getEducationPeriodForYearAndSemester(lessonDTO.getSemesterNumberYear().getSemester(),lessonDTO.getSemesterNumberYear().getYear()).getStartDate();
        Date end=educationPeriodService.getEducationPeriodForYearAndSemester(lessonDTO.getSemesterNumberYear().getSemester(),lessonDTO.getSemesterNumberYear().getYear()).getEndDate();
        journal.setMaxValue(lessonEventDataService.getSumMaxValueBetweenDates(lessonId,start,end));
//-----------------------------------------------------------------------------------------------------------------------

       //фильтрация списка студентов
        List<StudentJournalDTO> studentList = journal.getStudents() .stream()
                .filter(stud->{
                    if(student_id==null) return  true;
                    else return stud.getStudent().getId()==student_id;
                }).collect(Collectors.toList());

        //получение списка мероприятий
        List<LessonEventDTO> lessonEventDTOS=lessonEventListManager.getAll().stream().filter(lessonEvent->lessonEvent.getLesson().getId()==lessonId).collect(Collectors.toList());
        List<PointDTO> pointDTOS=journal.getJournalCell();//pointDataService.getAllByLesson(lessonId);
        List<PairDTO> pairDTOS=pairDataService.getAllByLesson(lessonId);
        lessonEventDTOS.forEach(el->{

            List<LessonEventResultValueDTO> lessonEventResultValueDTOList =new ArrayList<>();
                    studentList.forEach(student->{

                        map.put(student, map.get(student)!=null ? map.get(student) : 0 );

                        LessonEventResultValueDTO lessonEventResultValueDTO2 =new LessonEventResultValueDTO();
                        lessonEventResultValueDTO2.setStudentDTO(student);
                        lessonEventResultValueDTO2.setValue(0);

                        pairDTOS.forEach(pair->
                                pointDTOS.stream()
                                        .filter(point-> student.getSubgroup()==pair.getSubgroup() && point.getPairId()==pair.getId() && point.getStudentId()==student.getStudent().getId())
                                        .forEach(point-> {
                                            if( el.getType().getId()== point.getType().getId()  && DateHelper.getZeroTimeDate(el.getDate()).equals(DateHelper.getZeroTimeDate(point.getDate()))) {
                                                lessonEventResultValueDTO2.setValue(point.getValue());
                                                map.put(student,map.get(student)+point.getValue());
                                            }
                                                }));
                        lessonEventResultValueDTOList.add(lessonEventResultValueDTO2);
                    });

                    list.add(new LessonEventResultDTO(el, lessonEventResultValueDTOList));
                });


        //добавление события посещаемости
        double visitation= CertificationReportDto(lessonId, start,  end, "0").getAllHours()/2;

       List<PointDTO> size=journal.getJournalCell().stream().filter(el->{
            if(student_id==null) return  true;
            else return el.getStudentId()==student_id ;
        }).collect(Collectors.toList());

       long sum=0;
       for(PointDTO pointDTO:size)
           sum+=pointDTO.getValue();

       LessonEventDTO lessonEventDTO=new LessonEventDTO();
        lessonEventDTO.setDate(null);
        lessonEventDTO.setMaxValue((int)visitation);
        lessonEventDTO.setType((PointTypeDTO) mapperService.toDto(pointTypeRepository.findOne((long)1)));
        lessonEventDTO.setComment(lessonEventDTO.getType().getName());

        List<LessonEventResultValueDTO> lessonEventResultValueDTOList =new ArrayList<>();

        List<PointDTO> pointDTOS2=pointDTOS.stream()
                .filter(el-> el.getType().getName().equals(PointTypes.Visitation.toString()))
                .collect(Collectors.toList());

        studentList.forEach(el->{
            LessonEventResultValueDTO lessonEventResultValueDTO2 =new LessonEventResultValueDTO();
            lessonEventResultValueDTO2.setStudentDTO(el);
            lessonEventResultValueDTO2.setValue(0);
            int visit=0;

            for(PointDTO pointDTO:pointDTOS2) {
                if (pointDTO.getStudentId() == el.getStudent().getId()){
                    visit += pointDTO.getValue();}
            }
            lessonEventResultValueDTO2.setValue(visit);

            if(map.get(el)==null)
                map.put(el,visit);
                else
                map.put(el,map.get(el)+visit);

            lessonEventResultValueDTOList.add(lessonEventResultValueDTO2);
        });

        list.add(new LessonEventResultDTO(lessonEventDTO, lessonEventResultValueDTOList));
//----------------------------------------------------------------------------------------------------------------------
        int maxScore=(int)visitation+lessonEventDataService.getSumMaxValue(lessonId);

        lessonEventDTO=new LessonEventDTO();
        lessonEventDTO.setDate(null);
        lessonEventDTO.setMaxValue(maxScore);
        PointTypeDTO point=new PointTypeDTO();
        point.setName("Суммарно балов");
        lessonEventDTO.setType(point);
        lessonEventDTO.setComment("Суммарно балов");

        List<LessonEventResultValueDTO> lessonEventResultValueDTOList2 =new ArrayList<>();

        map.forEach((key,value)->{
            LessonEventResultValueDTO lessonEventResultValueDTO2 =new LessonEventResultValueDTO();
            lessonEventResultValueDTO2.setStudentDTO(key);
            lessonEventResultValueDTO2.setValue(value);
            lessonEventResultValueDTOList2.add(lessonEventResultValueDTO2);
        });

        list.add(new LessonEventResultDTO(lessonEventDTO, lessonEventResultValueDTOList2));
        return list;
    }

    //получить список мероприятий с текущими и максимальными баллами,для конкретного студента
    public StudentEventDTO geEventsForStudent(long student_id, long lesson_id){

        List<LessonEventValueDTO> list2=new ArrayList<>();
        StudentEventDTO studentEventDTO=new StudentEventDTO();

        List<LessonEventResultDTO> lessonEventResultDTOS = getStudentResults(lesson_id,student_id);

        lessonEventResultDTOS.stream()
                    .forEach(el-> {
                        el.getResult()
                                .stream()
                                .filter(elo -> elo.getStudentDTO().getStudent().getId() == student_id)
                                .forEach(res->{
                                    list2.add(new LessonEventValueDTO(el.getLessonEventDTO(),res.getValue()));
                                });
                    });

            studentEventDTO.setEvents(list2);

        return studentEventDTO;
    }

    //Обновить общий балл и оценку при изменеии аттестационного балла
    public  LessonCertificationResultDTO getNewLessonCertificationResultDTO( long lessonId,LessonCertificationResultDTO lessonCertificationResultDTO,int semester,int year,boolean currentOnly){
        lessonCertificationResultDTO.setTotalScore(getTotalScore(lessonId,lessonCertificationResultDTO,semester,year,currentOnly));
        lessonCertificationResultDTO.setMark(getMarkForLessonCertificationResult(lessonCertificationResultDTO));
        return  lessonCertificationResultDTO;
    }

    @Override//Получить DTO аттестации предмета
    public ResponseStatusDTO getLessonCertification(long lessonId) {
        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        try{
            responseStatusDTO.setData(mapperService.toDto(this.lessonCertificationRepository.getByLessonId(lessonId)));
        }catch (Exception e){
            responseStatusDTO.setStatus(StatusTypes.ERROR);
        }
        return responseStatusDTO;
    }

    //Получить список мероприятий для прелмета,учитывая посещаемость
    public ResponseStatusDTO getLessonEvents(long lessonId,int semester,int year){

        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);

        Map<String,Integer> map=new TreeMap<>();//карта под события

        try{
            LessonEntity lessonEntity=lessonRepository.findById(lessonId);
            Date start=lessonEntity.getEducationPeriod().getStartDate();
            Date end=lessonEntity.getEducationPeriod().getEndDate();

            int visitation=(int)CertificationReportDto(lessonId, start,  end, "0").getAllHours()/2;

            map.put(PointTypes.Visitation.toString(),visitation);

            map.put("Общий балл",0);

            //Добавление событий
            lessonEventRepository.findByLessonEntityId(lessonId).forEach(el->{
                map.put("Общий балл",map.get("Общий балл")+el.getMaxValue());

                if(!map.keySet().contains(el.getType().getName())){
                    map.put(el.getType().getName(),el.getMaxValue());
                }
                else{
                    map.put(el.getType().getName(),map.get(el.getType().getName())+el.getMaxValue());
                }
            });


            map.put("Общий балл",map.get("Общий балл")+visitation);

            responseStatusDTO.setData(map);
        }catch (Exception e){
            responseStatusDTO.setStatus(StatusTypes.ERROR);
        }


        return  responseStatusDTO;
    }

    @Override//Сохранить результаты аттестации
    public ResponseStatusDTO saveLessonCertificationResult(List<LessonCertificationResultDTO> lessonCertificationResultDTOS){

        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        List<LessonCertificationResultEntity> lessonCertificationResultEntities=new ArrayList<LessonCertificationResultEntity>();
        for(LessonCertificationResultDTO dto:lessonCertificationResultDTOS)
            lessonCertificationResultEntities.add((LessonCertificationResultEntity) mapperService.toEntity(dto));

        try{
            int idx = 0;
            lessonCertificationResultEntities= (List<LessonCertificationResultEntity>)lessonCertificationResultRepository.save(lessonCertificationResultEntities);
            for(LessonCertificationResultEntity entity:lessonCertificationResultEntities){
                lessonCertificationResultDTOS.get(idx).setId(entity.getId());
                idx++;
            }
            responseStatusDTO.setData(lessonCertificationResultDTOS);
        }catch (Exception e){
            responseStatusDTO.setStatus(StatusTypes.ERROR);
        }
        return  responseStatusDTO;
    }

    @Override//получить результат аттестации для студента по его id и id предмета
    public LessonCertificationResultDTO getLessonCertificationResultByStudentIdAndLessonCertificationId(long studentId, long lessonId) {

        long lessonCertificationId=lessonCertificationRepository.getByLessonId(lessonId).getId();
        LessonCertificationResultDTO lessonCertificationResultDTO = (LessonCertificationResultDTO) mapperService.toDto(lessonCertificationResultRepository.getLessonCertificationResultByStudentEntityIdAndLessonCertificationId(studentId, lessonCertificationId));
        lessonCertificationResultDTO.setMark(getMarkForLessonCertificationResult(lessonCertificationResultDTO));

        return lessonCertificationResultDTO;
    }

    public CertificationReportDto CertificationReportDto(long lessonId,Date start, Date end,String visitationOnly) {

        LessonDTO lessonDTO=lessonDataService.get(lessonId);
        JournalDTO journal = journalDataService.get(lessonDTO.getId(), null, lessonDTO.getSemesterNumberYear().getSemester(),lessonDTO.getSemesterNumberYear().getYear());
        journal.setMaxValue(lessonEventDataService.getSumMaxValueBetweenDates(lessonId,start,end));

        CertificationReportDto result = new CertificationReportDto();

        result.setLesson(journal.getLesson());

        List<CertificationStudentDto> studentCertification = new ArrayList<>();

        List<StudentJournalDTO> studentJournalDTOS=journal.getStudents();

        for (StudentJournalDTO student : studentJournalDTOS) {

            List<PointDTO> cells = journal.getJournalCell().stream().filter(
                    x -> x.getStudentId() == student.getStudent().getId()
                            && x.getDate().compareTo(DateHelper.getZeroTimeDate(start)) >= 0
                            && x.getDate().compareTo(DateHelper.getZeroTimeDate(end)) <= 0).collect(Collectors.toList());

            CertificationStudentDto certificationStudentDto = new CertificationStudentDto();
            double visitedHours = 0;
            double visitedValue = 0;

            List<CertificationEventDTO> certEvents = new ArrayList<>();

            for (PointDTO cell : cells) {
                if(cell.getValue() > 0 && cell.getType().getName().equals(PointTypes.Visitation.toString())) {
                    visitedHours += 2;
                    visitedValue += cell.getValue();
                } else {

                    CertificationEventDTO certEvent = new CertificationEventDTO();
                    certEvent.setEvent(cell.getType().getName());
                    certEvent.setValue(cell.getValue());

                    if(certEvents.stream().anyMatch(x -> x.getEvent().equals(certEvent.getEvent()))) {
                        CertificationEventDTO certificationEventDTO = certEvents.stream().filter(x -> x.getEvent() == certEvent.getEvent()).collect(Collectors.toList()).get(0);
                        certificationEventDTO.setValue(certificationEventDTO.getValue() + cell.getValue());
                    } else {
                        certEvents.add(certEvent);
                    }
                }
            }

            certificationStudentDto.setMissingHours(result.getAllHours() - visitedHours);
            certificationStudentDto.setStudent(student.getStudent());
            certificationStudentDto.setVisitationValue(visitedValue);
            certificationStudentDto.setEventValue(certEvents);

            int resultCertificationValue = 0;
            if(visitedHours > result.getAllHours() / 3) resultCertificationValue = 1;
            if(visitedHours > ((result.getAllHours() / 3) * 2)) resultCertificationValue = 2;


            certificationStudentDto.setValue(resultCertificationValue);

            studentCertification.add(certificationStudentDto);
        }

        result.setStudentCertification(studentCertification);

        int mustBeCount=0;
        int beCount=0;

        for(StudentJournalDTO studentJournalDTO: studentJournalDTOS) {
            mustBeCount=0;
            beCount=0;
            //Сколько часов у студента по расписанию в указанный период
            for (ComparisonDTO comparisonDTO : journal.getComparison()) {
                for (ComparisonPointDTO comparisonPointDTO : comparisonDTO.getPoints()) {
                    if((studentJournalDTO.getSubgroup()==comparisonPointDTO.getPair().getSubgroup() || comparisonPointDTO.getPair().getSubgroup()==0)
                            && (comparisonDTO.getDate().after(start) && comparisonDTO.getDate().before(end) ) ){
                        mustBeCount++;
                    }
                }
            }

            //На сколки парах студент был
            List<PointDTO> pointDTOS=journal.getJournalCell();
            for(PointDTO pointDTO: pointDTOS){
                if( pointDTO.getDate().after(start)  && pointDTO.getDate().before(end) && pointDTO.getStudentId()==studentJournalDTO.getStudent().getId() && pointDTO.getType().getName().equals("Посещение"))
                    beCount++;
            }

            //Перезапись данных
            for(CertificationStudentDto certificationStudentDto: result.getStudentCertification()) {
                if(certificationStudentDto.getStudent().getId()==studentJournalDTO.getStudent().getId()){
                    certificationStudentDto.setMissingHours((mustBeCount-beCount)*2);
                    certificationStudentDto.setVisitationValue(beCount*2);
                    certificationStudentDto.setCertificationValueByMaxValue(journal.getMaxValue());
                    if(visitationOnly.equals("1"))
                        certificationStudentDto.setCertificationValueByVisitation();
                }
            }
        }

        result.setAllHours(mustBeCount*2);

        return result;
    }

}
