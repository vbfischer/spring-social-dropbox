package org.springframework.social.dropbox.api.impl;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileUrlMixin {
	@JsonCreator
	public FileUrlMixin(
			@JsonProperty("url") String url, 
			@JsonProperty("expires") Date expires) {}
}
