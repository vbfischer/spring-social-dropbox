package org.springframework.social.dropbox.api.impl;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.dropbox.api.Metadata;

import java.util.Date;
import java.util.List;

/**
 * @author Robert Drysdale
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetadataMixin {
	@JsonCreator
	public MetadataMixin(
			@JsonProperty("size") String size, 
			@JsonProperty("bytes") int bytes, 
			@JsonProperty("is_dir") boolean isDir, 
			@JsonProperty("is_deleted") boolean isDeleted,
			@JsonProperty("rev") String rev, 
			@JsonProperty("hash") String hash, 
			@JsonProperty("thumb_exists") boolean thumbExists, 
			@JsonProperty("icon") String icon,
			@JsonProperty("modified") Date modified, 
			@JsonProperty("root") String root,
			@JsonProperty("path") String path,
			@JsonProperty("mime_type") String mimeType,
			@JsonProperty("contents") List<Metadata> contents) {}
}
