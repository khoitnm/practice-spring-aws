package org.tnmk.practicespringaws.pro02awsjavasdks3.common.aws.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.tnmk.practicespringaws.pro02awsjavasdks3.common.aws.AwsProperties;

/**
 * This is just a simplified wrapper of {@link AmazonS3ClientBuilder}.
 */
public class AmazonS3SimpleBuilder {

    public AmazonS3 build(AwsProperties awsProperties) {
        return AmazonS3ClientBuilder.standard()
            .withRegion(awsProperties.getRegion())
            .withCredentials(
                new AWSStaticCredentialsProvider(
                    new BasicAWSCredentials(
                        awsProperties.getAccessKey(),
                        awsProperties.getSecretKey()
                    )
                )
            )
            .withRegion(awsProperties.getRegion())
            .build();
    }
}
