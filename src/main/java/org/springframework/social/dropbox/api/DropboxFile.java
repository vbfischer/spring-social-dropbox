package org.springframework.social.dropbox.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Dropbox File Details
 * 
 * @author Robert Drysdale
 *
 */
public class DropboxFile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String contentType;
	private final long size;
	private final InputStream inputStream;
	
	public DropboxFile(String contentType, long size, InputStream inputStream) {
		this.contentType = contentType;
		this.size = size;
		this.inputStream = inputStream;
	}

	public String getContentType() {
		return contentType;
	}

	public long getSize() {
		return size;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
	
	public byte[] getBytes() throws IOException {
		byte[] ret = new byte[(int)size];
		for (int i = 0; i < size; i++) {
			ret[i] = (byte)inputStream.read();
		}
		
		return ret;
	}
}
