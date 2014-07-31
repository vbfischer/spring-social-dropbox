package org.springframework.social.dropbox.api.impl;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.social.dropbox.api.DropboxUserProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Bryce Fischer
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = DropboxUserProfileMixin.DropboxUserProfileDeserializer.class)
public class DropboxUserProfileMixin {
    static class DropboxUserProfileDeserializer extends JsonDeserializer<DropboxUserProfile>{
        @Override
        public DropboxUserProfile deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode tree = jp.readValueAsTree();

            String referralLink = tree.get("referral_link").asText();
            String country = tree.get("country").asText();
            String displayName = tree.get("display_name").asText();
            String email = tree.get("email").asText();
            BigInteger uid = tree.get("uid").bigIntegerValue();

            JsonNode quotaNode = tree.get("quota_info");
            BigInteger sharedQuota = quotaNode.get("shared").bigIntegerValue();
            BigInteger quota = quotaNode.get("quota").bigIntegerValue();
            BigInteger normalQuota = quotaNode.get("normal").bigIntegerValue();

            return new DropboxUserProfile(uid, displayName,  email,  country,  referralLink, sharedQuota,  quota, normalQuota);
        }
    }
}
