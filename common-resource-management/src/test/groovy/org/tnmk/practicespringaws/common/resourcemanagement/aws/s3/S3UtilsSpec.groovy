package org.tnmk.practicespringaws.common.resourcemanagement.aws.s3

import org.tnmk.practicespringaws.common.resourcemanagement.aws.s3.S3Utils
import spock.lang.Specification
import spock.lang.Unroll

class S3UtilsSpec extends Specification {

    @Unroll
    def 'Validate S3URL "#s3Url" will success'() {
        when:
        S3Utils.validateS3Url(s3Url)

        then:
        noExceptionThrown()

        where:
        s3Url << ["s3://abc/def", "s3://abc", "s3://"]
    }

    @Unroll
    def 'Validate S3URL "#s3Url" will fail'() {
        when:
        S3Utils.validateS3Url(s3Url)

        then:
        thrown(IllegalArgumentException.class)

        where:
        s3Url << [null, "", " ", "http://s3", "s3:/abc/def",]
    }

    @Unroll
    def 'Get ObjectName from S3URL "#s3Url" will successfully return "#expectedObjectName" '() {
        when:
        String objectName = S3Utils.getObjectNameFromLocation(s3Url)

        then:
        objectName == expectedObjectName

        where:
        s3Url              || expectedObjectName
        "s3://abc/def"     || "def"
        "s3://abc/def/xyz" || "def/xyz"
    }

    @Unroll
    def 'Get ObjectName from S3URL "#s3Url" will fail'() {
        when:
        S3Utils.getObjectNameFromLocation(s3Url)

        then:
        thrown(IllegalArgumentException.class)

        where:
        s3Url << [
                "s3://objectNameWithoutBucketName"
                , "s3:///objectNameWithoutBucketName"
                , "http://s3:///wrongProtocol"
                , "abc/wrongProtocol"
        ]
    }

    @Unroll
    def 'Get BucketName from S3URL "#s3Url" will successfully return "#expectedBucketName" '() {
        when:
        String result = S3Utils.getBucketNameFromLocation(s3Url)

        then:
        result == expectedBucketName

        where:
        s3Url              || expectedBucketName
        "s3://abc/def"     || "abc"
        "s3://abc/def/xyz" || "abc"
    }

    @Unroll
    def 'Get BucketName from S3URL "#s3Url" will fail'() {
        when:
        S3Utils.getBucketNameFromLocation(s3Url)

        then:
        thrown(IllegalArgumentException.class)

        where:
        s3Url << [
                "s3://objectNameWithoutBucketName"
                , "s3:///objectNameWithoutBucketName"
                , "http://s3:///wrongProtocol"
                , "abc/wrongProtocol"
        ]
    }
}
