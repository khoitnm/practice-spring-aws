package org.tnmk.practicespringaws.pro04

import org.junit.Ignore
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.tnmk.practicespringaws.pro04.Pro04Application
import spock.lang.Specification

@Ignore
@ActiveProfiles("personallocal")
@SpringBootTest(classes = Pro04Application.class)
abstract class BaseComponentSpecification extends Specification {

}