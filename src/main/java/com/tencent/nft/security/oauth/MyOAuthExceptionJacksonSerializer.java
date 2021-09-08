package com.tencent.nft.security.oauth;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class MyOAuthExceptionJacksonSerializer extends StdSerializer<MyOAuth2Exception> {

    protected MyOAuthExceptionJacksonSerializer() {
        super(MyOAuth2Exception.class);
    }

    protected MyOAuthExceptionJacksonSerializer(Class<MyOAuth2Exception> t) {
        super(t);
    }

    @Override
    public void serialize(MyOAuth2Exception e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("code", e.getHttpErrorCode());
        jsonGenerator.writeStringField("message", e.getSummary());
        jsonGenerator.writeEndObject();
    }
}
