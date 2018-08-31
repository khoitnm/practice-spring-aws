# I. Introduction
You can import those modules independently or you can import all of them by importing the parent project.

1. `pro01-simple-s3`: Provides a very simple way to load file from AWS S3 which uses `ResourceLoader` of Spring Framework.
View more at `S3ResourceRetriever`.
2. `pro02-aws-java-sdk-s3`: `pro01-simple-s3` does not provides any access to metadata of loaded file. 
So to get both binary data and metadata in the same request, we have to write our own code by using aws-java-sdk.
 
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

Start the application inside a module:
```
mvn spring-boot:run 
``` 
Or you can use:
```
java -jar target/pro01-simple-s3-0.0.1-SNAPSHOT.jar 
```

To stop it, press `Ctrl-C`
