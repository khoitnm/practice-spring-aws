package org.tnmk.practicespringaws.pro01simples3.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringaws.pro01simples3.sample.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
