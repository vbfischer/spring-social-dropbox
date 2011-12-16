package org.springframework.social.dropbox.api;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Metadata which describes a directory or file
 * in Dropbox
 * 
 * @author Robert Drysdale
 *
 */
public class Metadata implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String size;
	private final int bytes;
	private final boolean isDir;
	private final boolean isDeleted;
	private final String rev;
	private final String hash;
	private final boolean thumbExists;
	private final String icon;
	private final Date modified;
	private final String root;
	private final String path;
	private final String mimeType;
	private final List<Metadata> contents;
	
	public Metadata(String size, int bytes, boolean isDir, boolean isDeleted,
			String rev, String hash, boolean thumbExists, String icon,
			Date modified, String root, String path, String mimeType, List<Metadata> contents) {
		super();
		this.size = size;
		this.bytes = bytes;
		this.isDir = isDir;
		this.isDeleted = isDeleted;
		this.rev = rev;
		this.hash = hash;
		this.thumbExists = thumbExists;
		this.icon = icon;
		this.modified = modified;
		this.root = root;
		this.path = path;
		this.mimeType = mimeType;
		this.contents = contents;
	}

	public String getSize() {
		return size;
	}

	public int getBytes() {
		return bytes;
	}

	public boolean isDir() {
		return isDir;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public String getRev() {
		return rev;
	}

	public String getHash() {
		return hash;
	}

	public boolean isThumbExists() {
		return thumbExists;
	}

	public String getIcon() {
		return icon;
	}

	public Date getModified() {
		return modified;
	}

	public String getRoot() {
		return root;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public List<Metadata> getContents() {
		return contents;
	}
}
