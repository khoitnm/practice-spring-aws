package org.tnmk.practicespringaws.pro02awsjavasdks3.common.aws

import org.junit.Ignore
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.tnmk.practicespringaws.pro02awsjavasdks3.AwsJavaSdkS3Application
import spock.lang.Specification

@Ignore
@ActiveProfiles("componenttest")
@SpringBootTest(classes = AwsJavaSdkS3Application.class)
abstract class BaseComponentSpecification extends Specification {

}