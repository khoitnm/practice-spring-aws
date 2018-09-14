package org.tnmk.practicespringaws.pro05

import org.tnmk.practicespringaws.pro05.grpc.serialization.ProtobufMessageConverter
import spock.lang.Specification

import javax.jms.Message

class ProtobufMessageConverterSpec extends Specification {


    private ProtobufMessageConverter protobufMessageConverter = new ProtobufMessageConverter();




    def 'Publish Sqs message successfully'() {
        given:
        SampleMessageProto sampleMessageProto = SampleMessageProto.newBuilder()
            .setValue("sample message value "+System.nanoTime())
            .build();

        when:
        Message message = protobufMessageConverter.toMessage(sampleMessageProto, null);

        SampleMessageProto parsedSampleMessageProto = protobufMessageConverter.fromMessage(message);

        then:
        parsedSampleMessageProto.value == sampleMessageProto.value;
    }
}
