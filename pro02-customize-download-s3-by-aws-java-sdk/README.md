pro01-simple-download-s3-by-resourceloader provides a very simple way to load resource from s3.
But when loading the resource, it doesn't provides any metadata.
So to get both binary data and metadata in the same request, we have to write our own code by using aws-java-sdk.