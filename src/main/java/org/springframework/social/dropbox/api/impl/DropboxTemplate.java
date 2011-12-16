package org.springframework.social.dropbox.api.impl;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.dropbox.api.Dropbox;
import org.springframework.social.dropbox.api.DropboxFile;
import org.springframework.social.dropbox.api.DropboxUserProfile;
import org.springframework.social.dropbox.api.FileUrl;
import org.springframework.social.dropbox.api.Metadata;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 9:22 AM
 */
public class DropboxTemplate extends AbstractOAuth1ApiBinding implements Dropbox {
	private final String appFolderUrl;
	private ObjectMapper objectMapper;
    public DropboxTemplate(String appKey, String appSecret, String accessToken, String accessTokenSecret, boolean appFolder) {
        super(appKey, appSecret, accessToken, accessTokenSecret);
        appFolderUrl = appFolder ? "sandbox" : "dropbox";
        registerDropboxJsonModule(getRestTemplate());
    }

    public DropboxTemplate() {
        super();
        appFolderUrl = null;
    }

    public DropboxUserProfile getUserProfile() {
        return getRestTemplate().getForObject(ACCOUNT_INFO_URL, DropboxUserProfile.class);
    }

    public Metadata getItemMetadata(String path) {
        return getRestTemplate().getForObject(METADATA_URL, Metadata.class, appFolderUrl, path); 
    }
    
    public Metadata restore(String path, String rev) {
        return getRestTemplate().getForObject(RESTORE_URL + "?rev=" + rev, Metadata.class, appFolderUrl, path); 
    }
    
    public Metadata copy(String fromPath, String toPath) {
    	MultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
    	vars.add("root", appFolderUrl);
    	vars.add("from_path", fromPath);
    	vars.add("to_path", toPath);
        return getRestTemplate().postForObject(COPY_URL, vars, Metadata.class); 
    }
    
    public Metadata createFolder(String folder) {
    	MultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
    	vars.add("root", appFolderUrl);
    	vars.add("path", folder);
        return getRestTemplate().postForObject(CREATE_FOLDER_URL, vars, Metadata.class); 
    }
    
    public Metadata delete(String path) {
    	MultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
    	vars.add("root", appFolderUrl);
    	vars.add("path", path);
        return getRestTemplate().postForObject(DELETE_URL, vars, Metadata.class); 
    }
    
    public Metadata move(String fromPath, String toPath) {
    	MultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
    	vars.add("root", appFolderUrl);
    	vars.add("from_path", fromPath);
    	vars.add("to_path", toPath);
        return getRestTemplate().postForObject(MOVE_URL, vars, Metadata.class); 
    }
    
    public List<Metadata> getRevisions(String path) {
        JsonNode node = getRestTemplate().getForObject(REVISIONS_URL, JsonNode.class, appFolderUrl, path);
        
        try {
        	return objectMapper.readValue(node, new TypeReference<List<Metadata>>() {});
        }
        catch (Exception e) {
        	throw new RuntimeException(e);
        }
    }
    
    public List<Metadata> search(String path, String query) {
        JsonNode node = getRestTemplate().getForObject(SEARCH_URL + "?query=" + query, JsonNode.class, appFolderUrl, path);
        
        try {
        	return objectMapper.readValue(node, new TypeReference<List<Metadata>>() {});
        }
        catch (Exception e) {
        	throw new RuntimeException(e);
        }
    }
    
    public DropboxFile getThumbnail(String path) {
    	try {
	    	UriTemplate uriTemplate = new UriTemplate(THUMBNAILS_URL);
			URI uri = uriTemplate.expand(appFolderUrl, path);
	    	ClientHttpResponse response = getRestTemplate().getRequestFactory().createRequest(uri, HttpMethod.GET).execute();
	    	HttpHeaders headers = response.getHeaders();
	    	
	    	return new DropboxFile(
	    			headers.getContentType().toString(), 
	    			headers.getContentLength(),
	    			response.getBody());
    	}
    	catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    public DropboxFile getFile(String path) {
    	try {
    	UriTemplate uriTemplate = new UriTemplate(FILE_URL);
		URI uri = uriTemplate.expand(appFolderUrl, path);
    	ClientHttpResponse response = getRestTemplate().getRequestFactory().createRequest(uri, HttpMethod.GET).execute();
    	HttpHeaders headers = response.getHeaders();
    	
    	return new DropboxFile(
    			headers.getContentType().toString(), 
    			headers.getContentLength(),
    			response.getBody());
    	}
    	catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    public FileUrl getMedia(String path) {
    	return getRestTemplate().getForObject(MEDIA_URL, FileUrl.class, appFolderUrl, path); 
    }
    
    public FileUrl getShare(String path) {
    	return getRestTemplate().getForObject(SHARES_URL, FileUrl.class, appFolderUrl, path); 
    }
    
    public Metadata putFile(String path, byte[] file) {
    	UriTemplate uriTemplate = new UriTemplate(FILE_PUT_URL);
		URI uri = uriTemplate.expand(appFolderUrl, path);
    	
		try {
	    	ClientHttpRequest request = getRestTemplate().getRequestFactory().createRequest(uri, HttpMethod.PUT);
	    	request.getBody().write(file);
	    	
	    	ClientHttpResponse response = request.execute();
	    	ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();
	    	if (errorHandler.hasError(response)) {
	    		errorHandler.handleError(response);
	    		return null;
	    	}
	    	else {
	    		InputStream stream = response.getBody();
	    		return objectMapper.readValue(stream, Metadata.class);
	    	}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    private void registerDropboxJsonModule(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();

        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJacksonHttpMessageConverter) {
                MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;

                objectMapper = new ObjectMapper();
                objectMapper.registerModule(new DropboxModule());
                jsonConverter.setObjectMapper(objectMapper);
            }
        }
    }
    
    public static final String BASE_URL = "https://api.dropbox.com/1/";
    public static final String BASE_CONTENT_URL = "https://api-content.dropbox.com/1/";
    
    public static final String ACCOUNT_INFO_URL = BASE_URL + "account/info";
    public static final String COPY_URL = BASE_URL + "fileops/copy";
    public static final String CREATE_FOLDER_URL = BASE_URL + "fileops/create_folder";
    public static final String DELETE_URL = BASE_URL + "fileops/delete";
    public static final String FILE_URL = BASE_CONTENT_URL + "files/{appFolderUrl}/{path}";
    public static final String FILE_POST_URL = BASE_CONTENT_URL + "files/{appFolderUrl}/{path}";
    public static final String FILE_PUT_URL = BASE_CONTENT_URL + "files_put/{appFolderUrl}/{path}";
    public static final String MEDIA_URL = BASE_URL + "media/{appFolderUrl}/{path}";
    public static final String METADATA_URL = BASE_URL + "metadata/{appFolderUrl}/{path}";
    public static final String MOVE_URL = BASE_URL + "fileops/move";
    public static final String RESTORE_URL = BASE_URL + "restore/{appFolderUrl}/{path}";
    public static final String REVISIONS_URL = BASE_URL + "revisions/{appFolderUrl}/{path}";
    public static final String SEARCH_URL = BASE_URL + "search/{appFolderUrl}/{path}";
    public static final String SHARES_URL = BASE_URL + "shares/{appFolderUrl}/{path}";
    public static final String THUMBNAILS_URL = BASE_CONTENT_URL + "thumbnails/{appFolderUrl}/{path}";
}
