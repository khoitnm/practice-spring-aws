package org.tnmk.practicespringaws.pro01simpleentity.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tnmk.practicespringaws.pro01simpleentity.sample.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
