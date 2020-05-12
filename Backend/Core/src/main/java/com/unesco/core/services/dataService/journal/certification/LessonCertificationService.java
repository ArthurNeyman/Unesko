package com.unesco.core.services.dataService.journal.certification;

import com.unesco.core.dto.account.StudentDTO;
import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.LessonCertificationDTO;
import com.unesco.core.dto.certification.LessonCertificationResultDTO;
import com.unesco.core.dto.certification.LessonCertificationTypeDTO;
import com.unesco.core.dto.enums.StatusTypes;
import com.unesco.core.dto.shedule.LessonDTO;
import com.unesco.core.entities.certification.LessonCertificationEntity;
import com.unesco.core.entities.certification.LessonCertificationResultEntity;
import com.unesco.core.entities.journal.LessonEventEntity;
import com.unesco.core.entities.journal.PointEntity;
import com.unesco.core.repositories.account.StudentRepository;
import com.unesco.core.repositories.certification.LessonCertificationRepository;
import com.unesco.core.repositories.certification.LessonCertificationResultRepository;
import com.unesco.core.repositories.certification.LessonCertificationTypeRepository;
import com.unesco.core.repositories.journal.LessonEventRepository;
import com.unesco.core.repositories.journal.PointRepository;
import com.unesco.core.repositories.schedule.LessonRepository;
import com.unesco.core.services.dataService.journal.visitation.VisitationConfigDataService;
import com.unesco.core.services.dataService.plan.educationPeriodService.IEducationPeriodService;
import com.unesco.core.services.mapperService.IMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class LessonCertificationService implements  ILessonCertificationService {

    @Autowired
    LessonCertificationResultRepository lessonCertificationResultRepository;
    @Autowired
    LessonCertificationTypeRepository lessonCertificationTypeRepository;
    @Autowired
    LessonCertificationRepository lessonCertificationRepository;
    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    LessonEventRepository lessonEventRepository;

    @Autowired
    IEducationPeriodService educationPeriodService;
    @Autowired
    IMapperService mapperService;
    @Autowired
    VisitationConfigDataService visitationConfigDataService;

    @Override
    public ResponseStatusDTO setLessonCertificationType(LessonCertificationDTO lessonCertificationDTO) {
        ResponseStatusDTO result=new ResponseStatusDTO(StatusTypes.OK);

        LessonCertificationEntity lessonCertificationEntity=new LessonCertificationEntity();
        try{
            lessonCertificationEntity=lessonCertificationRepository.save((LessonCertificationEntity)mapperService.toEntity(lessonCertificationDTO));
            lessonCertificationDTO.setId(lessonCertificationEntity.getId());
            result.setData(lessonCertificationDTO);
        }catch (Exception e){
            result.setStatus(StatusTypes.ERROR);
        }

        return result;
    }

    @Override
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

    @Override
    public List<LessonCertificationResultDTO> getLessonCertificationResultListByLesson(LessonDTO lessonDTO) {
        List<LessonCertificationResultDTO> lessonCertificationResultDTOS=new ArrayList<>();

        lessonCertificationResultRepository.getByLessonCertificationId(lessonDTO.getId()).forEach(el->
                lessonCertificationResultDTOS.add((LessonCertificationResultDTO)mapperService.toDto(el)));
        return lessonCertificationResultDTOS;
    }

    @Override
    public List<LessonCertificationTypeDTO> getLessonCertificationTypes() {
        List<LessonCertificationTypeDTO> lessonCertificationTypeDTOS=new ArrayList<>();
        lessonCertificationTypeRepository.findAll().forEach(el->lessonCertificationTypeDTOS.add((LessonCertificationTypeDTO) mapperService.toDto(el)));
        return lessonCertificationTypeDTOS;
    }


    @Override
    public ResponseStatusDTO getLessonsByLessonIdAndProfessor(long professorId,int semester,int year){
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
        }catch (Exception e){
            responseStatusDTO.setStatus(StatusTypes.ERROR);
        }
        responseStatusDTO.setData(lessonCertificationDTOS);
        return  responseStatusDTO;
    }

    @Override
    public ResponseStatusDTO setMaximumCertificationScore(LessonCertificationDTO lessonCertificationDTO){
        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        try {
            System.out.println(lessonCertificationDTO);
            lessonCertificationRepository.save((LessonCertificationEntity) mapperService.toEntity(lessonCertificationDTO));
            responseStatusDTO.setData(lessonCertificationDTO);
        }catch (Exception e){
            responseStatusDTO.setStatus(StatusTypes.ERROR);
            System.out.println(e.getMessage());
        }
        return responseStatusDTO;
    }

    public ResponseStatusDTO getLessonCertificationResult(LessonCertificationDTO lessonCertificationDTO){
        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        List<LessonCertificationResultEntity> list=lessonCertificationResultRepository.getByLessonCertificationId(lessonCertificationDTO.getId(),new Sort("studentEntity.user.userFIO"));
        List<LessonCertificationResultDTO> lessonCertificationResultDTOS=new ArrayList<>();

        if(!list.isEmpty()){
            for(LessonCertificationResultEntity entity:list){
                lessonCertificationResultDTOS.add((LessonCertificationResultDTO)mapperService.toDto(entity));
            }
            responseStatusDTO.setData(lessonCertificationResultDTOS);
            return  responseStatusDTO;
        }else{
            getLessonEvents(lessonCertificationDTO.getLesson().getId());

            studentRepository.findAllByGroupId(lessonCertificationDTO.getLesson().getGroup().getId()).forEach(
                    el->{
                        LessonCertificationResultDTO lessonCertificationResultDTO=new LessonCertificationResultDTO();
                        lessonCertificationResultDTO.setStudentDTO((StudentDTO) mapperService.toDto(el));
                        lessonCertificationResultDTO.setLessonCertificationId(lessonCertificationDTO.getId());
                        lessonCertificationResultDTO.setAbsence(false);
                        lessonCertificationResultDTOS.add(lessonCertificationResultDTO);
                    }
            );
            responseStatusDTO.setData(lessonCertificationResultDTOS);

        }
        return responseStatusDTO;
    }

    @Override
    public ResponseStatusDTO getLessonCertification(long lessonId) {
        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        try{
            responseStatusDTO.setData(mapperService.toDto(this.lessonCertificationRepository.getByLessonId(lessonId)));
        }catch (Exception e){
            responseStatusDTO.setStatus(StatusTypes.ERROR);
        }
        return responseStatusDTO;
    }

    public ResponseStatusDTO getLessonEvents(long lessonId){
        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        try{
            Map<String,Integer> map=new HashMap<>();
            lessonEventRepository.findByLessonEntityId(lessonId).forEach(el->{
                if(!map.keySet().contains(el.getType().getName())){
                    map.put(el.getType().getName(),el.getMaxValue());
                }
                else{
                    map.put(el.getType().getName(),map.get(el.getType().getName())+el.getMaxValue());
                }
            });
            responseStatusDTO.setData(map);
        }catch (Exception e){
            System.out.println(e.getMessage());
            responseStatusDTO.setStatus(StatusTypes.ERROR);
        }
        return  responseStatusDTO;
    }

    @Override
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

    @Override
    public LessonCertificationResultDTO getLessonCertificationResultByStudentIdAndLessonCertificationId(long studentId, long lessonCertificationId) {
        LessonCertificationEntity lessonCertificationEntity=new LessonCertificationEntity();
        LessonCertificationResultEntity lessonCertificationResultEntity = lessonCertificationResultRepository.getLessonCertificationResultByStudentEntityIdAndLessonCertificationId(studentId, lessonCertificationId);
        return (LessonCertificationResultDTO) mapperService.toDto(lessonCertificationResultEntity);
    }
}
