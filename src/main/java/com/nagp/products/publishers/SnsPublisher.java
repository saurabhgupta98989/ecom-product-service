package com.nagp.products.publishers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagp.products.events.EventEnvelope;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SnsPublisher {

    private final SnsClient snsClient;

    @Value("${aws.sns.productServiceTopicArn}")
    private String topicArn;

    private final ObjectMapper mapper = new ObjectMapper();
    public <T> void publish(String eventType, T payload) {
        try {
            EventEnvelope<T> envelope = EventEnvelope.<T>builder()
                    .eventId(UUID.randomUUID().toString())
                    .eventType(eventType)
                    .source("product-service")
                    .timestamp(System.currentTimeMillis())
                    .data(payload)
                    .build();
            String message = mapper.writeValueAsString(envelope);
            PublishRequest request = PublishRequest.builder()
                    .topicArn(topicArn)
                    .message(message)
                    .messageAttributes(Map.of(
                            "eventType", MessageAttributeValue.builder()
                                    .dataType("String")
                                    .stringValue(eventType)
                                    .build()
                    ))
                    .build();
            PublishResponse response = snsClient.publish(request);
        } catch (Exception e) {
            throw new RuntimeException("SNS publish failed", e);
        }
    }
//    public void publish(Object event){
//        try{
//            String body = mapper.writeValueAsString(event);
//
//            snsClient.publish(PublishRequest.builder()
//                    .topicArn(topicArn)
//                    .message(body)
//                    .build());
//
//        }catch(Exception e){
//            throw new RuntimeException("SNS publish failed", e);
//        }
//    }
}
