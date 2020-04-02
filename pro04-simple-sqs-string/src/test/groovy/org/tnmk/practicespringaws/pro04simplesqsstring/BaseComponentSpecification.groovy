package org.tnmk.practicespringaws.pro04simplesqsstring

import org.junit.Ignore
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.tnmk.practicespringaws.pro04simplesqsstring.Pro04SimpleSqsString
import spock.lang.Specification

@Ignore
@ActiveProfiles("componenttest")
@SpringBootTest(classes = Pro04SimpleSqsString.class)
abstract class BaseComponentSpecification extends Specification {

}