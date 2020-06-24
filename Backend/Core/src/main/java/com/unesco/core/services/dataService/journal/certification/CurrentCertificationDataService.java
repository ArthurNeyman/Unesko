package com.unesco.core.services.dataService.journal.certification;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.CurrentCertificationDTO;
import com.unesco.core.dto.certification.CurrentCertificationValueDTO;
import com.unesco.core.dto.enums.StatusTypes;
import com.unesco.core.entities.certification.CurrentCertificationEntity;
import com.unesco.core.entities.certification.CurrentCertificationValueEntity;
import com.unesco.core.repositories.certification.CurrentCertificationRepository;
import com.unesco.core.repositories.certification.CurrentCertificationValueRepository;
import com.unesco.core.services.mapperService.IMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CurrentCertificationDataService implements ICurrentCertificationDataService {

    @Autowired
    private CurrentCertificationRepository currentCertificationRepository;

    @Autowired
    private CurrentCertificationValueRepository currentCertificationValueRepository;

    @Autowired
    private IMapperService mapperService;

    @Override
    public List<CurrentCertificationDTO> getCurrentCertifications(long lesson_id) {

        List<CurrentCertificationDTO> list=new ArrayList<CurrentCertificationDTO>();
        List<CurrentCertificationEntity> certificationEntities= currentCertificationRepository.findByLessonId(lesson_id);

        for(CurrentCertificationEntity currentCertificationEntity :certificationEntities) {
            CurrentCertificationDTO currentCertificationDTO =(CurrentCertificationDTO)mapperService.toDto(currentCertificationEntity);
            list.add(currentCertificationDTO);
        }
        return list;
    }

    @Override
    public ResponseStatusDTO<CurrentCertificationDTO> saveCurrentCertification(CurrentCertificationDTO certification) {

        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        CurrentCertificationEntity currentCertificationEntity=currentCertificationRepository.save((CurrentCertificationEntity) mapperService.toEntity(certification));
        currentCertificationEntity.getCurrentCertificationValueEntities().forEach(el->{
            el.setCurrentCertification(currentCertificationEntity);
            currentCertificationValueRepository.save(el);
        });
        responseStatusDTO.setData(mapperService.toDto(currentCertificationEntity));

        return responseStatusDTO;
    }

    @Override
    @Transactional
    public ResponseStatusDTO<CurrentCertificationDTO> deleteCurrentCertification(CurrentCertificationDTO certification) {
        currentCertificationValueRepository.deleteByCurrentCertificationId(certification.getId());
        currentCertificationRepository.delete(certification.getId());
        return new ResponseStatusDTO<>(StatusTypes.OK);
    }
}
