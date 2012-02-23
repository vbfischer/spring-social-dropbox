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
     * Retrieve Details for a Dropbox's Profile Details
     * 
     * @param locale
     * @return Dropbox Users Profile Details
     */
    DropboxUserProfile getUserProfile(String locale);

    /**
     * Get Metadata For a File or Directory
     * 
     * @param path
     * @return Metadata
     */
    Metadata getItemMetadata(String path);
    
    /**
     * Get Metadata For a File or Directory
     * 
     * @param path
     * @param hash

     * @return Metadata
     */
    Metadata getItemMetadata(String path, String hash);
    
    /**
     * Get Metadata For a File or Directory
     * 
     * @param path
     * @param file_limit
     * @param hash
     * @param list
     * @param include_deleted
     * @param rev
     * @param locale
     * @return Metadata
     */
    Metadata getItemMetadata(String path, int file_limit, String hash, boolean list, boolean includeDeleted, String rev, String locale);
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
     * Restore a file to a revision
     * Will also restore an deleted file
     * 
     * @param path
     * @param rev
     * @param locale
     * @return Metadata
     */
    Metadata restore(String path, String rev, String locale) ;
    
    /**
     * Copy File to new file or directory
     * 
     * @param fromPath
     * @param toPath
     * @return Metadata
     */
    Metadata copy(String fromPath, String toPath);
    
    /**
     * Copy File to new file or directory
     * 
     * @param fromPath
     * @param toPath
     * @param locale
     * @return Metadata
     */
    Metadata copy(String fromPath, String toPath, String locale);
    
    /**
     * Create a new folder
     * 
     * @param folder
     * @return Metadata
     */
    Metadata createFolder(String folder);
    
    /**
     * Create a new folder
     * 
     * @param folder
     * @param locale
     * @return Metadata
     */
    Metadata createFolder(String folder, String locale);
    
    /**
     * Delete a file
     * 
     * @param path
     * @return Metadata
     */
    Metadata delete(String path);
    
    /**
     * Delete a file
     * 
     * @param path
     * @param locale
     * @return Metadata
     */
    Metadata delete(String path, String locale);
    
    /**
     * Move a File
     * 
     * @param fromPath
     * @param toPath
     * @return Metadata
     */
    Metadata move(String fromPath, String toPath);
    
    /**
     * Move a File
     * 
     * @param fromPath
     * @param toPath
     * @param locale
     * @return Metadata
     */
    Metadata move(String fromPath, String toPath, String locale);
    
    /**
     * Get List of Revisions for a File
     * @param path
     * @return List of Metadata
     */
    List<Metadata> getRevisions(String path);
    
    /**
     * Get List of Revisions for a File
     * @param path
     * @param rev_limit
     * @param locale
     * @return List of Metadata
     */
    List<Metadata> getRevisions(String path, int rev_limit, String locale);
    
    /**
     * Search for a File
     * 
     * @param path
     * @param query
     * @return List of Metadata
     */
    List<Metadata> search(String path, String query);
    
    /**
     * Search for a File
     * 
     * @param path
     * @param query
     * @param file_limit
     * @param include_deleted
     * @param locale
     * @return List of Metadata
     */
    List<Metadata> search(String path, String query, int file_limit, boolean include_deleted, String locale);
    
    /**
     * Get Thumbnail for  a file
     * 
     * @param path
     * @return Dropbox File
     */
    DropboxFile getThumbnail(String path);
    
    /**
     * Get Thumbnail for  a file
     * 
     * @param path
     * @param format
     * @param size
     * @return Dropbox File
     */
    DropboxFile getThumbnail(String path, String format, String size);
    
    /**
     * Retrieve a File
     * @param path
     * @return Dropbox File
     */
    DropboxFile getFile(String path);
    
    /**
     * Retrieve a specific revision of a File
     * @param path
     * @param rev
     * @return Dropbox File
     */
    DropboxFile getFile(String path, String rev);
    
    /**
     * Get Url for a File that can be streamed as media
     * 
     * @param path
     * @return File Url with Expires Date
     */
    FileUrl getMedia(String path);
    
    /**
     * Get Url for a File that can be streamed as media
     * 
     * @param path
     * @param locale
     * @return File Url with Expires Date
     */
    FileUrl getMedia(String path, String locale);
    
    /**
     * Get Url for a File that can be shared
     * 
     * @param path
     * @return File Url with Expires Date
     */
    FileUrl getShare(String path);
    
    /**
     * Get Url for a File that can be shared
     * 
     * @param path
     * @param locale
     * @return File Url with Expires Date
     */
    FileUrl getShare(String path, String locale);
    
    /**
     * Push a File to Dropbox
     * 
     * @param path
     * @param file
     * @return Metadata
     */
    Metadata putFile(String path, byte[] file);
    
    /**
     * Push a File to Dropbox
     * 
     * @param path
     * @param file
     * @param locale
     * @param overwrite
     * @param parent_rev
     * @return Metadata
     */
    Metadata putFile(String path, byte[] file, String locale, boolean overwrite, String parent_rev);
}
