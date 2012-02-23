package org.springframework.social.dropbox.api.impl;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.dropbox.api.*;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

/**
 * @author Bryce Fischer
 * @author Robert Drysdale
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
        return getUserProfile(null);
    }
    
    public DropboxUserProfile getUserProfile(String locale) {
        
        String queryString = "";
        if (locale != null){queryString = addParam(queryString, "locale=" + locale);}
        return getRestTemplate().getForObject(ACCOUNT_INFO_URL + queryString, DropboxUserProfile.class);
    }

    public Metadata getItemMetadata(String path) {
        return getItemMetadata(path, 10000, null, true, false, null, null); 
    }
    
    public Metadata getItemMetadata(String path, String hash) {
        return getItemMetadata(path, 10000, hash, true, false, null, null); 
    }
    
    public Metadata getItemMetadata(String path, int fileLimit, String hash, boolean list, boolean includeDeleted, String rev,
            String locale)
    {
        String queryString=""; 
        if (fileLimit != 10000){queryString = addParam(queryString, "file_limit=" + fileLimit);}
        if (hash != null){queryString = addParam(queryString, "hash=" + hash);}
        if (!list){queryString = addParam(queryString, "list=" + list);}
        if (includeDeleted){queryString = addParam(queryString, "include_deleted=" + includeDeleted);}
        if (rev != null){queryString = addParam(queryString, "rev=" + rev);}
        if (locale != null){queryString = addParam(queryString, "locale=" + locale);}
        return getRestTemplate().getForObject(METADATA_URL + queryString, Metadata.class, appFolderUrl, path); 
    }
    
    public Metadata restore(String path, String rev) {
        return restore(path, rev, null); 
    }
    
    public Metadata restore(String path, String rev, String locale)
    {
        String queryString = "";
        if (rev != null){queryString = addParam(queryString, "rev=" + rev);}
        if (locale != null){queryString = addParam(queryString, "locale=" + locale);}
        return getRestTemplate().getForObject(RESTORE_URL + queryString, Metadata.class, appFolderUrl, path);
    }
    
    public Metadata copy(String fromPath, String toPath) {
        return copy(fromPath, toPath, null); 
    }
    
    public Metadata copy(String fromPath, String toPath, String locale)
    {
        MultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
        vars.add("root", appFolderUrl);
        vars.add("from_path", fromPath);
        vars.add("to_path", toPath);
        if (locale != null){vars.add("locale", locale);}
        return getRestTemplate().postForObject(COPY_URL, vars, Metadata.class); 
    }
    
    public Metadata createFolder(String folder) {
    	return createFolder(folder, null); 
    }
    
    public Metadata createFolder(String folder, String locale)
    {
        MultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
        vars.add("root", appFolderUrl);
        vars.add("path", folder);
        if (locale != null){vars.add("locale", locale);}
        return getRestTemplate().postForObject(CREATE_FOLDER_URL, vars, Metadata.class);
    }
    
    public Metadata delete(String path) {
    	return delete(path, null); 
    }
    
    public Metadata delete(String path, String locale)
    {
        MultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
        vars.add("root", appFolderUrl);
        vars.add("path", path);
        if (locale != null){vars.add("locale", locale);}
        return getRestTemplate().postForObject(DELETE_URL, vars, Metadata.class);
    }
    
    public Metadata move(String fromPath, String toPath) {
    	return move(fromPath, toPath, null); 
    }
    
    public Metadata move(String fromPath, String toPath, String locale)
    {
        MultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
        vars.add("root", appFolderUrl);
        vars.add("from_path", fromPath);
        vars.add("to_path", toPath);
        if (locale != null){vars.add("locale", locale);}
        return getRestTemplate().postForObject(MOVE_URL, vars, Metadata.class);
    }
    
    public List<Metadata> getRevisions(String path) {
        return getRevisions(path, 10, null);
    }
    
    public List<Metadata> getRevisions(String path, int revLimit, String locale)
    {
        String queryString = "";
        if (revLimit != 10){queryString = addParam(queryString, "rev_limit=" + revLimit);}
        if (locale != null){queryString = addParam(queryString, "locale=" + locale);}
        
        JsonNode node = getRestTemplate().getForObject(REVISIONS_URL + queryString, JsonNode.class, appFolderUrl, path);
        
        try {
            return objectMapper.readValue(node, new TypeReference<List<Metadata>>() {});
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Metadata> search(String path, String query) {
        return search(path, query, 1000, false, null);
    }
    
    public List<Metadata> search(String path, String query, int fileLimit, boolean includeDeleted, String locale)
    {
        String queryString = "";
        addParam(queryString, "query=" + query);
        if (fileLimit != 1000){queryString = addParam(queryString, "file_limit=" + fileLimit);}
        if (includeDeleted){queryString = addParam(queryString, "include_deleted=" + includeDeleted);}
        if (locale != null){queryString = addParam(queryString, "locale=" + locale);}
        
        JsonNode node = getRestTemplate().getForObject(SEARCH_URL + queryString, JsonNode.class, appFolderUrl, path);
        
        try {
            return objectMapper.readValue(node, new TypeReference<List<Metadata>>() {});
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public DropboxFile getThumbnail(String path) {
    	return getThumbnail(path, null, null);
    }
    
    public DropboxFile getThumbnail(String path, String format, String size)
    {
        String queryString = "";
        if(format != null){queryString = addParam(queryString, "format="+format);}
        if(size != null){queryString = addParam(queryString, "size="+size);}
        
        try {
            UriTemplate uriTemplate = new UriTemplate(THUMBNAILS_URL + queryString);
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
    	return getFile(path, null);
    }
    
    public DropboxFile getFile(String path, String rev)
    {
        String queryString = "";
        if(rev != null){queryString = addParam(queryString, "rev="+rev);}
        
        try {
            UriTemplate uriTemplate = new UriTemplate(FILE_URL + queryString);
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
    	return getMedia(path, null); 
    }
    
    public FileUrl getMedia(String path, String locale)
    {
        String queryString = "";
        if(locale != null){queryString = addParam(queryString, "locale=" + locale);}
        return getRestTemplate().getForObject(MEDIA_URL + queryString, FileUrl.class, appFolderUrl, path);
    }
    
    public FileUrl getShare(String path) {
    	return getShare(path, null); 
    }
    
    public FileUrl getShare(String path, String locale)
    {
        String queryString = "";
        if(locale != null){queryString = addParam(queryString, "locale=" + locale);}
        return getRestTemplate().getForObject(SHARES_URL, FileUrl.class, appFolderUrl, path);
    }
    
    public Metadata putFile(String path, byte[] file) {
        return putFile(path, file, null, true, null);
    }
    
    public Metadata putFile(String path, byte[] file, String locale, boolean overwrite, String parentRev)
    {
        String queryString = "";
        if(locale != null){queryString = addParam(queryString, "locale=" + locale);}
        if(overwrite != true){queryString = addParam(queryString, "overwrite=" + overwrite);}
        if(parentRev != null){queryString = addParam(queryString, "parentRev=" + parentRev);}
        
        UriTemplate uriTemplate = new UriTemplate(FILE_PUT_URL + queryString);
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
    
    /**
     * Add parameters to queryString. If null or empty the parameter is the
     * first to be added and will be prepended by '?'. If there are existing
     * parameters a new parameter is appended with an '&'
     * 
     * @param queryString original querystring
     * @param param parameter to be added
     * @return Updated querystring
     */
    public static String addParam(String queryString, String param)
    {

        // if null create an empty string
        if (queryString == null)
        {
            queryString = "";
        }

        // first element starts with ? all else are added with &
        if (queryString.length() == 0)
        {
            queryString = "?" + param;
        }
        else
        {
            queryString = queryString + "&" + param;
        }

        return queryString;
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
