package org.tnmk.practicespringaws.pro05.story

import org.junit.Ignore
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.tnmk.practicespringaws.pro05.story.Pro05Application
import spock.lang.Specification

@Ignore
@ActiveProfiles("componenttest")
@SpringBootTest(classes = Pro05Application.class)
abstract class BaseComponentSpecification extends Specification {

}