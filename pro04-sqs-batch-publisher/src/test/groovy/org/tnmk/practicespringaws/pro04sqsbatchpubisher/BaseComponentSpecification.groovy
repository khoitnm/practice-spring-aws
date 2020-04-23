package org.tnmk.practicespringaws.pro04sqsbatchpubisher

import org.junit.Ignore
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@Ignore
@ActiveProfiles("personallocal")
@SpringBootTest(classes = Pro04SqsBatchPublisherApplication.class)
abstract class BaseComponentSpecification extends Specification {

}