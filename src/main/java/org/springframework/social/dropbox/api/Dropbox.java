package org.springframework.social.dropbox.api;

import java.util.List;


/**
 * Dropbox Interface
 * 
 * @author Bryce Fischer
 * @author Robert Drysdale
 */
public interface Dropbox {
	/**
	 * Retrieve Details for a Dropbox's Profile Details
	 * 
	 * @return Dropbox Users Profile Details
	 */
    DropboxUserProfile getUserProfile();

    /**
     * Get Metadata For a File or Directory
     * 
     * @param path
     * @return Metadata
     */
    Metadata getItemMetadata(String path);
    
    /**
     * Restore a file to a revision
     * Will also restore an deleted file
     * 
     * @param path
     * @param rev
     * @return Metadata
     */
    Metadata restore(String path, String rev) ;
    
    /**
     * Copy File to new file or directory
     * 
     * @param fromPath
     * @param toPath
     * @return Metadata
     */
    Metadata copy(String fromPath, String toPath);
    
    /**
     * Create a new folder
     * 
     * @param folder
     * @return Metadata
     */
    Metadata createFolder(String folder);
    
    /**
     * Delete a file
     * 
     * @param path
     * @return Metadata
     */
    Metadata delete(String path);
    
    /**
     * Move a File
     * 
     * @param fromPath
     * @param toPath
     * @return Metadata
     */
    Metadata move(String fromPath, String toPath);
    
    /**
     * Get List of Revisions for a File
     * @param path
     * @return List of Metadata
     */
    List<Metadata> getRevisions(String path);
    
    /**
     * Search for a File
     * 
     * @param path
     * @param query
     * @return List of Metadata
     */
    List<Metadata> search(String path, String query);
    
    /**
     * Get Thumbnail for  a file
     * 
     * @param path
     * @return Dropbox File
     */
    DropboxFile getThumbnail(String path);
    
    /**
     * Retrieve a File
     * @param path
     * @return Dropbox File
     */
    DropboxFile getFile(String path);
    
    /**
     * Get Url for a File that can be streamed as media
     * 
     * @param path
     * @return File Url with Expires Date
     */
    FileUrl getMedia(String path);
    
    /**
     * Get Url for a File that can be shared
     * 
     * @param path
     * @return File Url with Expires Date
     */
    FileUrl getShare(String path);
    
    /**
     * Push a File to Dropbox
     * 
     * @param path
     * @param file
     * @return Metadata
     */
    Metadata putFile(String path, byte[] file);
}
