package com.unesco.core.services.dataService.journal.certification;

import com.unesco.core.dto.additional.ResponseStatusDTO;
import com.unesco.core.dto.certification.CurrentCertificationDTO;

import java.util.List;

public interface ICurrentCertificationDataService {

    List<CurrentCertificationDTO> getCurrentCertifications(long lessonId);
    ResponseStatusDTO<CurrentCertificationDTO> saveCurrentCertification(CurrentCertificationDTO currentCertificationDTO);
    ResponseStatusDTO<CurrentCertificationDTO> deleteCurrentCertification(CurrentCertificationDTO certification);
}
