package com.unesco.core.entities.certification;

import javax.persistence.*;

@Entity
@Table(name="un_intermediate_certification_type")
public class IntermediateCertificationTypeEntity {

    @Id
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
