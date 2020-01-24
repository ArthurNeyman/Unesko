package com.unesco.core.services.dataService.journal.certification;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.CertificationDTO;
import com.unesco.core.dto.enums.StatusTypes;
import com.unesco.core.entities.certification.CertificationEntity;
import com.unesco.core.repositories.certification.CertificationRepository;
import com.unesco.core.repositories.certification.CertificationValueRepository;
import com.unesco.core.services.mapperService.IMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CertificationService implements ICertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private CertificationValueRepository certificationValueRepository;

    @Autowired
    private IMapperService mapperService;

    @Override
    public List<CertificationDTO> getCertificationListByGroupIdAndEducationPeriodId(long group_id, long education_period_id) {
        List<CertificationDTO> list=new ArrayList<CertificationDTO>();
        for(CertificationEntity c:certificationRepository.findByGroupIdAndEducationPeriodId(group_id,education_period_id))
                list.add((CertificationDTO) mapperService.toDto(c));
        return list;
    }

    @Override
    public ResponseStatusDTO<CertificationDTO> saveCertification(CertificationDTO certification) {
        certificationRepository.save((CertificationEntity)mapperService.toEntity(certification));
        return new ResponseStatusDTO<>(StatusTypes.OK);
    }

    @Override
    public ResponseStatusDTO<CertificationDTO> deleteCertification(CertificationDTO certification) {
        certificationRepository.delete((CertificationEntity)mapperService.toEntity(certification));
        return new ResponseStatusDTO<>(StatusTypes.OK);
    }
}
