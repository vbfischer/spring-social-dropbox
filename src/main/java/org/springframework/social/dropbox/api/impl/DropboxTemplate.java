package org.springframework.social.dropbox.api.impl;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.dropbox.api.DropboxApi;
import org.springframework.social.dropbox.api.DropboxItemMetadata;
import org.springframework.social.dropbox.api.DropboxUserProfile;
import org.springframework.social.oauth1.AbstractOAuth1ApiTemplate;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.List;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 9:22 AM
 */
public class DropboxTemplate extends AbstractOAuth1ApiTemplate implements DropboxApi {
    public DropboxTemplate(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        super(consumerKey, consumerSecret, accessToken, accessTokenSecret);

        registerDropboxJsonModule(getRestTemplate());
    }

    public DropboxTemplate() {
        super();
    }

    public DropboxUserProfile getUserProfile() {
        return getRestTemplate().getForObject("https://api.dropbox.com/0/account/info", DropboxUserProfile.class);
    }

    public List<DropboxItemMetadata> getItemMetadata(String path, boolean list, BigInteger maxFiles) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void registerDropboxJsonModule(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();

        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJacksonHttpMessageConverter) {
                MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new DropboxModule());
                jsonConverter.setObjectMapper(objectMapper);
            }
        }
    }
}
