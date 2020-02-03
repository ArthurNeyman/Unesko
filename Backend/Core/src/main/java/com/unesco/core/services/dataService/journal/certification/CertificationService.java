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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

        for(CertificationEntity certificationEntity:certificationRepository.findByLessonId(lesson_id)) {
            List<CertificationValueDTO> certificationValueDTOS=new ArrayList<>();
            CertificationDTO certificationDTO=(CertificationDTO)mapperService.toDto(certificationEntity);

            for(CertificationValueEntity certificationValueEntity:certificationValueRepository.findByCertificationId(certificationEntity.getId(),new Sort("Student.user.userFIO")))
                certificationValueDTOS.add((CertificationValueDTO) mapperService.toDto(certificationValueEntity));

            certificationDTO.setCertificationValueDTOList(certificationValueDTOS);
            list.add(certificationDTO);
        }
        return list;
    }


    @Override
    public ResponseStatusDTO<CertificationDTO> saveCertification(CertificationDTO certification) {

        if(certification.getId()==0)
            certification.setId(certificationRepository.save((CertificationEntity)mapperService.toEntity(certification)).getId());

        List<CertificationValueDTO> certificationValueDTOS=new ArrayList<>();

        for(CertificationValueDTO cert:certification.getCertificationValueDTOList()){
            cert.setCertificationId(certification.getId());
            certificationValueDTOS.add((CertificationValueDTO)mapperService.toDto(certificationValueRepository.save((CertificationValueEntity) mapperService.toEntity(cert))));
        }
        certification.setCertificationValueDTOList(certificationValueDTOS);
        ResponseStatusDTO responseStatusDTO=new ResponseStatusDTO(StatusTypes.OK);
        responseStatusDTO.setData(certification);
        return responseStatusDTO;
    }

    @Override
    @Transactional
    public ResponseStatusDTO<CertificationDTO> deleteCertification(CertificationDTO certification) {
        certificationValueRepository.deleteByCertificationId(certification.getId());
        certificationRepository.delete(certification.getId());
        return new ResponseStatusDTO<>(StatusTypes.OK);
    }
}
