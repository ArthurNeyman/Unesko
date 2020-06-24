package com.unesco.core.repositories.certification;

import com.unesco.core.entities.certification.CurrentCertificationValueEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrentCertificationValueRepository extends CrudRepository<CurrentCertificationValueEntity, Long> {

    List<CurrentCertificationValueEntity> findByCurrentCertificationId(long certificationId, Sort sort);

    void deleteByCurrentCertificationId(long certificationId);
}
