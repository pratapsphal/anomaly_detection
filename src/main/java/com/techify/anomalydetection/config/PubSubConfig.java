package com.techify.anomalydetection.config;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

// This class configures the Pub/Sub message channel and inbound adapter.
@Configuration
public class PubSubConfig {

    // Define the input channel that will receive messages from Pub/Sub.
    @Bean
    public MessageChannel pubsubInputChannel() {
        return new DirectChannel();
    }

    // Configure the Pub/Sub inbound channel adapter.
    // It connects the subscription to the input channel.
    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("pubsubInputChannel") MessageChannel inputChannel,
            PubSubTemplate pubSubTemplate,
            @Value("${gcp.pubsub.subscription-name}") String subscriptionName) {

        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, subscriptionName);
        adapter.setOutputChannel(inputChannel);
        return adapter;
    }
}
