package com.unesco.core.repositories.certification;

import com.unesco.core.entities.certification.CertificationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CertificationRepository  extends CrudRepository<CertificationEntity, Long> {

    List<CertificationEntity> findByGroupIdAndEducationPeriodId(long groupId,long educationPeriodId);
}
