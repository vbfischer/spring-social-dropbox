package org.springframework.social.dropbox.api;

import java.io.Serializable;
import java.util.Date;

/**
 * File Url that can be shared or streamed as media
 * 
 * @author Robert Drysdale
 *
 */
public class FileUrl implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String url;
	private final Date expires;
	
	public FileUrl(String url, Date expires) {
		this.url = url;
		this.expires = expires;
	}
	
	public String getUrl() {
		return url;
	}
	
	public Date getExpires() {
		return expires;
	}

}
