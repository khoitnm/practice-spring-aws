package org.tnmk.practicespringaws.common.resourcemanagement.aws.s3

import com.amazonaws.services.s3.AmazonS3
import org.tnmk.practicespringaws.common.resourcemanagement.aws.AwsProperties
import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.AmazonS3SimpleBuilder
import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.S3ResourceRetriever
import org.tnmk.practicespringaws.common.resourcemanagement.resource.exception.ResourceRetrieverException
import spock.lang.Specification
import spock.lang.Unroll

/**
 * It requires the real connection to S3 to test the successful cases.<br/>
 * So cannot do that with UnitTest (it should be done in Integration Test).<br/>
 * We can mock every S3 object to do UnitTest, but then it cannot make sure that whether we are really able to connect to S3 in the real application.<br/>
 * So there's no point to write UnitTest with mock AWS S3 objects.<br/>
 * <p/>
 * More detail why we should not mock AwsS3 objects: <br/>
 * https://softwareengineering.stackexchange.com/questions/299249/how-to-test-code-that-depends-on-complex-apis-amazon-s3-for-instance
 */
class S3ResourceRetrieverSpec extends Specification {

    @Unroll
    def 'S3ResourceRetrieverSpec should throw exception when cannot connect to S3'() {
        when:
        AwsProperties awsProperties = new AwsProperties(
                region: "sample-region"
                , accessKey: "sample-accesskey"
                , secretKey: "sample-secretkey")
        AmazonS3 amazonS3 = new AmazonS3SimpleBuilder().build(awsProperties)
        S3ResourceRetriever s3ResourceRetriever = new S3ResourceRetriever(amazonS3)
        s3ResourceRetriever.retrieve("s3://testdata/test-sample-file.csv")

        then:
        thrown(ResourceRetrieverException.class)
    }
}
