package com.unesco.core.services.dataService.journal.certification;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.CertificationDTO;

import java.util.List;

public interface ICertificationService {

    List<CertificationDTO> getCertificationListByGroupIdAndEducationPeriodId(long group_id, long education_period_id);
    ResponseStatusDTO<CertificationDTO> saveCertification(CertificationDTO certificationDTO);
    ResponseStatusDTO<CertificationDTO> deleteCertification(CertificationDTO certification);
}
