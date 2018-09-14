package org.tnmk.practicespringaws.pro02awsjavasdks3.common.aws

import org.junit.Ignore
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import Pro03Application
import org.tnmk.practicespringaws.pro04.Pro04Application
import spock.lang.Specification

@Ignore
@ActiveProfiles("componenttest")
@SpringBootTest(classes = Pro04Application.class)
abstract class BaseComponentSpecification extends Specification {

}