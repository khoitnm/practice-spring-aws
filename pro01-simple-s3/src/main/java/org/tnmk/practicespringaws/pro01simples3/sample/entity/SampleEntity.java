package org.tnmk.practicespringaws.pro01simples3.sample.entity;

import javax.persistence.*;

@Entity
@Table(name = "sample_entity", catalog = "sample_db")
public class SampleEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "sample_entity_id")
    private Long sampleEntityId;

    private String name;

    public Long getSampleEntityId() {
        return sampleEntityId;
    }

    public void setSampleEntityId(Long sampleEntityId) {
        this.sampleEntityId = sampleEntityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
