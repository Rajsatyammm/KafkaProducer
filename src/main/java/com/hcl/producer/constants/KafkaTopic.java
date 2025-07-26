package com.hcl.producer.constants;

public final class KafkaTopic {
    private KafkaTopic() {

    }

    public static final String PRODUCE_MESSAGE_TOPIC = "send-message";
    public static final String SEND_ACKNOWLEDGEMENT = "send-ack";
}
