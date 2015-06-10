package org.springframework.social.dropbox.api.impl;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Robert Drysdale
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileUrlMixin {
	@JsonCreator
	public FileUrlMixin(@JsonProperty("url") String url,
			@JsonProperty("expires") Date expires) {
	}
}
