package org.tnmk.practicespringaws.pro04simplesqsjson

import org.junit.Ignore
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.tnmk.practicespringaws.pro04simplesqsjson.Pro04SimpleSqsJson
import spock.lang.Specification

@Ignore
@ActiveProfiles("componenttest")
@SpringBootTest(classes = Pro04SimpleSqsJson.class)
abstract class BaseComponentSpecification extends Specification {

}