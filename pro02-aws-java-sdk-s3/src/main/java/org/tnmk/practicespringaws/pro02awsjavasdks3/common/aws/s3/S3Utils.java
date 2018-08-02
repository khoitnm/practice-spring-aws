package org.tnmk.practicespringaws.pro02awsjavasdks3.common.aws.s3;

import org.springframework.util.StringUtils;

public class S3Utils {
    private static final String S3_SCHEME = "s3://";
    private static final String PATH_DELIMITER = "/";
    private static final String VERSION_DELIMITER = "^";


    public static void validateS3Url(String location) {
        if (StringUtils.isEmpty(location)) {
            throw new IllegalArgumentException("S3Url is null");
        }

        if (!location.startsWith(S3_SCHEME)) {
            throw new IllegalArgumentException("S3Url is malformed, it must start with s3://");
        }
    }

    /**
     * If location is "s3://someBucketName/def/xyz", return "someBucketName"
     * @param location
     * @return
     */
    public static String getBucketNameFromLocation(String location) {
        validateS3Url(location);
        int bucketEndIndex = validateExistBucketNameFromLocation(location);
        return location.substring(S3_SCHEME.length(), bucketEndIndex);
    }

    /**
     * If success, return the bucketEndIndex.
     * @param location
     * @return
     */
    private static int validateExistBucketNameFromLocation(String location){
        int bucketEndIndex = location.indexOf(PATH_DELIMITER, S3_SCHEME.length());
        if (bucketEndIndex == -1 || bucketEndIndex == S3_SCHEME.length()) {
            throw new IllegalArgumentException("The location :'" + location + "' does not contain a valid bucket name");
        }
        return bucketEndIndex;
    }

    /**
     *
     * @param location If location is "s3://bucketName/def/xyz", return "def/xyz"
     * @return
     */
    public static String getObjectNameFromLocation(String location) {
        validateS3Url(location);
        
        int bucketEndIndex = validateExistBucketNameFromLocation(location);
        if (location.contains(VERSION_DELIMITER)) {
            return getObjectNameFromLocation(location.substring(0, location.indexOf(VERSION_DELIMITER)));
        }
        if (location.endsWith(PATH_DELIMITER)) {
            return location.substring(++bucketEndIndex, location.length() - 1);
        }
        return location.substring(++bucketEndIndex, location.length());
    }
}
