package org.tnmk.practicespringaws.samplebusiness.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringaws.samplebusiness.entity.ChildEntity;
import org.tnmk.practicespringaws.samplebusiness.entity.SampleEntity;
import org.tnmk.practicespringaws.samplebusiness.repository.SampleRepository;

import java.util.Arrays;

@Service
public class SampleStory {

    @Autowired
    private SampleRepository sampleRepository;

    public SampleEntity createSample() {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setName("Sample_" + System.nanoTime());
        sampleEntity.setMainChildEntity(constructChildEntity());
        sampleEntity.setOtherChildEntities(Arrays.asList(constructChildEntity(), constructChildEntity()));
        return sampleRepository.save(sampleEntity);
    }

    private ChildEntity constructChildEntity() {
        ChildEntity childEntity = new ChildEntity();
        childEntity.setName("Child_" + System.nanoTime());
        childEntity.setDescription("Description_" + System.nanoTime());
        return childEntity;
    }
}
