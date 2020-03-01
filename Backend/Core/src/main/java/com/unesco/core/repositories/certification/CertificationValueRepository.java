package com.unesco.core.repositories.certification;

import com.unesco.core.entities.certification.CertificationValueEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CertificationValueRepository extends CrudRepository<CertificationValueEntity, Long> {

    List<CertificationValueEntity> findByCertificationId(long certificationId, Sort sort);

    void deleteByCertificationId(long certificationId);
}
