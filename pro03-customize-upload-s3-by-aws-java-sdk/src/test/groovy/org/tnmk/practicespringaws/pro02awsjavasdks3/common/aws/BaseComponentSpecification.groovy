package org.tnmk.practicespringaws.pro02awsjavasdks3.common.aws

import org.junit.Ignore
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.tnmk.practicespringaws.pro03.Pro03Application
import spock.lang.Specification

@Ignore
@ActiveProfiles("componenttest")
@SpringBootTest(classes = Pro03Application.class)
abstract class BaseComponentSpecification extends Specification {

}