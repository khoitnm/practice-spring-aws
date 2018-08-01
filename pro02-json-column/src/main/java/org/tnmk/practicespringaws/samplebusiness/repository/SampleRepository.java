package org.tnmk.practicespringaws.samplebusiness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringaws.samplebusiness.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
