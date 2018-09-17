> If you find there are something which could be improved, please create a Merge Request. 
> Thank you so much!

# I. Introduction
You can import those modules independently or you can import all of them by importing the parent project.

1. `pro01-simple-download-s3-by-resourceloader`: Provides a very simple way to load file from AWS S3 which uses `ResourceLoader` of Spring Framework.
View more at `S3ResourceRetriever`.
2. `pro02-customize-download-s3-by-aws-java-sdk`: `pro01-simple-download-s3-by-resourceloader` does not provides any access to metadata of loaded file. 
So to get both binary data and metadata in the same request, we have to write our own code by using aws-java-sdk.
3. `pro03-customize-upload-s3-by-aws-java-sdk` : Upload S3 and related metadata in one request by using aws-java-sdk.
4. `pro04-simple-sqs-json` : send the SQS message as JSON format.
5. `pro05-simple-sqs-grpc` : send the SQS message as ProtoBuf format.
Note: If you want to see the sending message in the queue, please disable Listener when running test (Just comment out the @Component or @Service annotation in Listener).

It has 2 classes for transformation between SQS message with Grpc objects:
- `ProtobufMessageConverterByClassCanonicalName`: use when both SQS publisher and listener are Java codes.
- `ProtobufMessageConverterByClassSimpleName`: use when either SQS publisher or listener is NOT Java code.
 
# II. Build projects
Run the command line, it will compile the source code, build project, and then run tests.
```
mvn clean install 
```

If you want to build without testing, run the command line:
```
mvn clean install -DskipTests 
```

# III. Start Application.
Before running the application, you have to set the AWS connection information into `application.yml` file

Start the application inside a module:
```
mvn spring-boot:run 
``` 
Or you can use:
```
java -jar target/pro01-simple-download-s3-by-resourceloader-0.0.1-SNAPSHOT.jar 
```

To stop it, press `Ctrl-C`
