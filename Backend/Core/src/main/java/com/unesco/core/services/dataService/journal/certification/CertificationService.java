package com.unesco.core.services.dataService.journal.certification;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.CertificationDTO;
import com.unesco.core.dto.certification.CertificationValueDTO;
import com.unesco.core.dto.enums.StatusTypes;
import com.unesco.core.entities.certification.CertificationEntity;
import com.unesco.core.entities.certification.CertificationValueEntity;
import com.unesco.core.repositories.certification.CertificationRepository;
import com.unesco.core.repositories.certification.CertificationValueRepository;
import com.unesco.core.services.mapperService.IMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CertificationService implements ICertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private CertificationValueRepository certificationValueRepository;

    @Autowired
    private IMapperService mapperService;

    @Override
    public List<CertificationDTO> getCertificationListByLessonId(long lesson_id) {
        List<CertificationDTO> list=new ArrayList<CertificationDTO>();
        for(CertificationEntity c:certificationRepository.findByLessonId(lesson_id))
                list.add((CertificationDTO) mapperService.toDto(c));

        return list;
    }

    @Override
    public ResponseStatusDTO<CertificationDTO> saveCertification(CertificationDTO certification) {

        CertificationEntity certificationEntity=certificationRepository.save((CertificationEntity)mapperService.toEntity(certification));

        for(CertificationValueEntity c:certificationEntity.getCertificationValueList()){
            c.setCertificationId(certificationEntity.getId());
            this.certificationValueRepository.save(c);
        }
        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        responseStatusDTO.setData(mapperService.toDto(certificationEntity));
        return responseStatusDTO;
    }

    @Override
    public ResponseStatusDTO<CertificationDTO> deleteCertification(CertificationDTO certification) {
        certificationRepository.delete((CertificationEntity)mapperService.toEntity(certification));
        return new ResponseStatusDTO<>(StatusTypes.OK);
    }
}
