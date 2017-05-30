package org.nmdp.servicekafkaproducermodel.models;

/**
 * Created by Andrew S. Brown, Ph.D., <andrew@nmdp.org>, on 5/19/17.
 * <p>
 * service-kafka-producer-model
 * Copyright (c) 2012-2017 National Marrow Donor Program (NMDP)
 * <p>
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 3 of the License, or (at
 * your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library;  if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.
 * <p>
 * > http://www.fsf.org/licensing/licenses/lgpl.html
 * > http://www.opensource.org/licenses/lgpl-license.php
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaMessage extends Message implements Serializable {

    private static final ThreadLocal<ObjectMapper> OBJECT_MAPPER = ThreadLocal.withInitial(ObjectMapper::new);
    private Date messageGenerationDateTime;
    private String messageId;
    private String messageProductionLocation;
    private JsonNode payload;

    public KafkaMessage(Date messageGenerationDateTime, String messageId,
        String messageProductionLocation, KafkaMessagePayload payload) throws IOException {
        this.messageGenerationDateTime = messageGenerationDateTime;
        this.messageId = messageId;
        this.messageProductionLocation = messageProductionLocation;
        this.payload = OBJECT_MAPPER.get().readTree(OBJECT_MAPPER.get().writeValueAsString(payload));
    }

    @JsonProperty("messageGenerationDateTime")
    public Date getMessageGenerationDateTime() {
        return messageGenerationDateTime;
    }

    @JsonProperty("messageId")
    public String getMessageId() {
        return messageId;
    }

    @JsonProperty("messageProductionLocation")
    public String getMessageProductionLocation() {
        return messageProductionLocation;
    }

    @JsonProperty("payload")
    public KafkaMessagePayload getPayload() throws JsonProcessingException, IOException {
        return (KafkaMessagePayload) OBJECT_MAPPER.get().reader(KafkaMessagePayload.class).readValue(payload);
    }

    public byte[] toBinary() {
        return super.toBinary(this);
    }

    public String toJson() {
        return super.toJson(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((messageGenerationDateTime == null) ? 0 : messageGenerationDateTime.hashCode());
        result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
        result = prime * result + ((messageProductionLocation == null) ? 0 : messageProductionLocation.hashCode());
        result = prime * result + ((payload == null) ? 0 : payload.hashCode());

        return result;
    }
}
