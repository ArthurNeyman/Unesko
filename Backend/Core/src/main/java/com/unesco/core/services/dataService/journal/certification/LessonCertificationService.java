package com.unesco.core.services.dataService.journal.certification;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.IntermediateCertificationDTO;
import com.unesco.core.dto.certification.IntermediateCertificationResultDTO;
import com.unesco.core.dto.certification.IntermediateCertificationTypeDTO;
import com.unesco.core.dto.enums.StatusTypes;
import com.unesco.core.dto.shedule.LessonDTO;
import com.unesco.core.entities.certification.IntermediateCertificationEntity;
import com.unesco.core.entities.certification.IntermediateCertificationResultEntity;
import com.unesco.core.managers.journal.VisitationConfigManager.interfaces.IVisitationConfigManager;
import com.unesco.core.managers.journal.lessonEvent.interfaces.lessonEventList.ILessonEventListManager;
import com.unesco.core.managers.monitoringStudentsProgress.IMonitoringStudentsManager;
import com.unesco.core.repositories.account.StudentRepository;
import com.unesco.core.repositories.certification.IntermediateCertificationRepository;
import com.unesco.core.repositories.certification.IntermediateСertificationResultRepository;
import com.unesco.core.repositories.certification.IntermediateCertificationTypeRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LessonCertificationService implements  ILessonCertificationService {

    @Autowired
    private IntermediateСertificationResultRepository intermediateСertificationResultRepository;

    @Autowired
    private IntermediateCertificationTypeRepository intermediateCertificationTypeRepository;

    @Autowired
    private IntermediateCertificationRepository intermediateCertificationRepository;

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

    //------------------------------------------------------------------------------------------------------------------
    @Autowired
    private IMonitoringStudentsManager monitoringStudentsManager;

    //=================================================================================================================
    @Override//Назначить тип аттестации по предмету(Админ) ->DataService
    public IntermediateCertificationDTO setLessonCertificationType(IntermediateCertificationDTO intermediateCertificationDTO) {
        try{
            IntermediateCertificationEntity intermediateCertificationEntity = intermediateCertificationRepository.save((IntermediateCertificationEntity)mapperService.toEntity(intermediateCertificationDTO));
            intermediateCertificationDTO.setId(intermediateCertificationEntity.getId());
            return   intermediateCertificationDTO;
        }catch (Exception e){
            return  null;
        }

    }
    @Override//Получить типы аттестации по предметам(Админ) ->DataService
    public List<IntermediateCertificationTypeDTO> getLessonCertificationTypes() {

        List<IntermediateCertificationTypeDTO> intermediateCertificationTypeDTOS =new ArrayList<>();
        intermediateCertificationTypeRepository.findAll().forEach(el-> intermediateCertificationTypeDTOS.add((IntermediateCertificationTypeDTO) mapperService.toDto(el)));

        return intermediateCertificationTypeDTOS;
    }
    @Override//получить список предметов преподавателя с указанием типа аттестации ->DataService
    public ResponseStatusDTO<IntermediateCertificationDTO> getLessonCertificationByLesson(LessonDTO lessonDTO) {

        ResponseStatusDTO result=new ResponseStatusDTO(StatusTypes.OK);
        IntermediateCertificationEntity intermediateCertificationEntity =new IntermediateCertificationEntity();

        try{
            intermediateCertificationEntity = intermediateCertificationRepository.getByLessonId(lessonDTO.getId());
        }catch (Exception e){
            result.setStatus(StatusTypes.ERROR);
        }
        result.setData(mapperService.toDto(intermediateCertificationEntity));

        return result;
    }
    @Override//назначить максимальный аттестационный балл->DataService
    public ResponseStatusDTO setMaximumCertificationScore(IntermediateCertificationDTO intermediateCertificationDTO){
        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        try {
            intermediateCertificationRepository.save((IntermediateCertificationEntity) mapperService.toEntity(intermediateCertificationDTO));
            responseStatusDTO.setData(intermediateCertificationDTO);
        }catch (Exception e){
            responseStatusDTO.setStatus(StatusTypes.ERROR);
        }
        return responseStatusDTO;
    }
    @Override//Сохранить результаты аттестации-> DataService
    public ResponseStatusDTO saveLessonCertificationResult(List<IntermediateCertificationResultDTO> intermediateCertificationResultDTOS){

        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        List<IntermediateCertificationResultEntity> lessonCertificationResultEntities=new ArrayList<IntermediateCertificationResultEntity>();
        for(IntermediateCertificationResultDTO dto: intermediateCertificationResultDTOS)
            lessonCertificationResultEntities.add((IntermediateCertificationResultEntity) mapperService.toEntity(dto));

        try{
            int idx = 0;
            lessonCertificationResultEntities= (List<IntermediateCertificationResultEntity>) intermediateСertificationResultRepository.save(lessonCertificationResultEntities);
            for(IntermediateCertificationResultEntity entity:lessonCertificationResultEntities){
                intermediateCertificationResultDTOS.get(idx).setId(entity.getId());
                idx++;
            }
            responseStatusDTO.setData(intermediateCertificationResultDTOS);
        }catch (Exception e){
            responseStatusDTO.setStatus(StatusTypes.ERROR);
        }
        return  responseStatusDTO;
    }
    @Override//Получить список сущностей промежуточной аттестации для преподавателя по его id -> DataService
    public ResponseStatusDTO getLessonCertificationsByProfessorIdAndSemesterAndYear(long professorId, int semester, int year){

        List<IntermediateCertificationDTO> intermediateCertificationDTOS =new ArrayList<>();
        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        try {
            long educationPeriodId = educationPeriodService.getEducationPeriodForYearAndSemester(semester, year).getId();

            lessonRepository.findByProfessorId(professorId, educationPeriodId).forEach(
                    el -> {
                        IntermediateCertificationDTO intermediateCertificationDTO = new IntermediateCertificationDTO();
                        LessonDTO lessonDTO = (LessonDTO) mapperService.toDto(el);
                        intermediateCertificationDTO.setLesson(lessonDTO);
                        IntermediateCertificationEntity intermediateCertificationEntity = intermediateCertificationRepository.getByLessonId(el.getId());
                        intermediateCertificationDTO.setMaxCertificationScore(intermediateCertificationEntity != null ? intermediateCertificationEntity.getMaxCertificationScore() : 0);

                        intermediateCertificationDTO.setId(intermediateCertificationEntity != null ? intermediateCertificationEntity.getId() : 0);
                        intermediateCertificationDTO.setLessonCertificationType(intermediateCertificationEntity != null ?
                                (IntermediateCertificationTypeDTO) mapperService.toDto(intermediateCertificationRepository.getByLessonId(el.getId()).getIntermediateCertificationTypeEntity())
                                : new IntermediateCertificationTypeDTO());
                        intermediateCertificationDTOS.add(intermediateCertificationDTO);
                    }
            );
            responseStatusDTO.setData(intermediateCertificationDTOS);

        }catch (Exception e){
            responseStatusDTO.setStatus(StatusTypes.ERROR);
        }

        return  responseStatusDTO;
    }
    //==================================================================================================================
    @Override
    public IntermediateCertificationDTO getIntermediateCertification(long lessonId){
        return (IntermediateCertificationDTO) mapperService.toDto(this.intermediateCertificationRepository.getByLessonId(lessonId));
    }
    @Override
    public IntermediateCertificationResultDTO getIntermediateCertificationForStudent(long lessonId, long studentId) {
        long lessonCertificationId= intermediateCertificationRepository.getByLessonId(lessonId).getId();
        IntermediateCertificationResultDTO intermediateCertificationResultDTO = (IntermediateCertificationResultDTO) mapperService.toDto(intermediateСertificationResultRepository.getLessonCertificationResultByStudentEntityIdAndLessonCertificationId(studentId, lessonCertificationId));
        intermediateCertificationResultDTO.setMark(getMarkForLessonCertificationResult(intermediateCertificationResultDTO));
        return intermediateCertificationResultDTO;
    }
    public String getMarkForLessonCertificationResult(IntermediateCertificationResultDTO intermediateCertificationResultDTO){

        long typeId= intermediateCertificationRepository.findOne(intermediateCertificationResultDTO.getLessonCertificationId()).getIntermediateCertificationTypeEntity().getId();

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
}
