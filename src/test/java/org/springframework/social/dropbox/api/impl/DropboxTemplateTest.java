package org.springframework.social.dropbox.api.impl;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.dropbox.api.DropboxFile;
import org.springframework.social.dropbox.api.DropboxUserProfile;
import org.springframework.social.dropbox.api.FileUrl;
import org.springframework.social.dropbox.api.Metadata;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


/**
 * @author Bryce Fischer
 * @author Robert Drysdale
 */
public class DropboxTemplateTest extends AbstractDropboxApiTest {
    @Test
    public void getUserProfileValid() throws Exception {
        mockServer
                .expect(requestTo("https://api.dropbox.com/1/account/info"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("/profileValid"), MediaType.APPLICATION_JSON));

        DropboxUserProfile profile = dropbox.getUserProfile();
        assertEquals("US", profile.getCountry());
        assertEquals("Some User", profile.getDisplayName());
        assertEquals("some email", profile.getEmail());
        assertEquals(BigInteger.valueOf(10), profile.getUid());
        assertEquals("referralLink", profile.getReferralLink());
        assertEquals(BigInteger.valueOf(108179488768L), profile.getQuota());
        assertEquals(BigInteger.valueOf(44962990383L), profile.getSharedQuota());
        assertEquals(BigInteger.valueOf(46970996076L), profile.getNormalQuota());
    }
    
    @Test
	public void putFile() throws Exception {
    	mockServer
        .expect(requestTo(DropboxTemplate.FILE_PUT_URL
        		.replaceFirst("\\{appFolderUrl\\}", "dropbox")
        		.replaceFirst("\\{path\\}", "file.json")))
        .andExpect(method(PUT))
        .andRespond(withSuccess(jsonResource("/file_put_metadata"), MediaType.APPLICATION_JSON));
    	
		FileInputStream stream = new FileInputStream(jsonResource("metadata").getFile());
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		while(true) {
			int b = stream.read();
			if (b == -1) {
				break;
			}
			bytes.write(b);
		}
		
		Metadata metadata = dropbox.putFile("file.json", bytes.toByteArray());
		assertEquals("7702a9405f", metadata.getRev());
		assertEquals(false, metadata.isThumbExists());
		assertEquals(12265, metadata.getBytes());
		assertEquals(fromDropboxDate("Fri, 02 Dec 2011 11:27:27 +0000"), metadata.getModified());
		assertEquals("/file.json", metadata.getPath());
		assertEquals(false, metadata.isDir());
		assertEquals("page_white", metadata.getIcon());
		assertEquals("dropbox", metadata.getRoot());
		assertEquals("application/octet-stream", metadata.getMimeType());
		assertEquals("12KB", metadata.getSize());
	}
    
    @Test
	public void copyFile() throws Exception {
    	mockServer
        .expect(requestTo(DropboxTemplate.COPY_URL))
        .andExpect(method(POST))
        .andRespond(withSuccess(jsonResource("/copy"), MediaType.APPLICATION_JSON));
		
		Metadata metadata = dropbox.copy("file.json", "file3.json");
		assertEquals("8e02a9405f", metadata.getRev());
		assertEquals(false, metadata.isThumbExists());
		assertEquals(12265, metadata.getBytes());
		assertEquals(fromDropboxDate("Fri, 16 Dec 2011 12:24:07 +0000"), metadata.getModified());
		assertEquals("/file3.json", metadata.getPath());
		assertEquals(false, metadata.isDir());
		assertEquals("page_white", metadata.getIcon());
		assertEquals("dropbox", metadata.getRoot());
		assertEquals("application/octet-stream", metadata.getMimeType());
		assertEquals("12KB", metadata.getSize());
	}
    
    @Test
	public void moveFile() throws Exception {
    	mockServer
        .expect(requestTo(DropboxTemplate.MOVE_URL))
        .andExpect(method(POST))
        .andRespond(withSuccess(jsonResource("/file_moved"), MediaType.APPLICATION_JSON));
		
		Metadata metadata = dropbox.move("file.json", "file_moved.json");
		assertEquals("9202a9405f", metadata.getRev());
		assertEquals(false, metadata.isThumbExists());
		assertEquals(12265, metadata.getBytes());
		assertEquals(fromDropboxDate("Fri, 16 Dec 2011 14:39:42 +0000"), metadata.getModified());
		assertEquals("/file_moved.json", metadata.getPath());
		assertEquals(false, metadata.isDir());
		assertEquals("page_white", metadata.getIcon());
		assertEquals("dropbox", metadata.getRoot());
		assertEquals("application/octet-stream", metadata.getMimeType());
		assertEquals("12KB", metadata.getSize());
	}
    
    @Test
	public void restoreFile() throws Exception {
    	mockServer
        .expect(requestTo(DropboxTemplate.RESTORE_URL
        		.replaceFirst("\\{appFolderUrl\\}", "dropbox")
        		.replaceFirst("\\{path\\}", "file3.json")
        		+ "?rev=8e02a9405f"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("/restored"), MediaType.APPLICATION_JSON));
		
		Metadata metadata = dropbox.restore("file3.json", "8e02a9405f");
		assertEquals("9302a9405f", metadata.getRev());
		assertEquals(false, metadata.isThumbExists());
		assertEquals(12265, metadata.getBytes());
		assertEquals(fromDropboxDate("Fri, 16 Dec 2011 14:48:58 +0000"), metadata.getModified());
		assertEquals("/file3.json", metadata.getPath());
		assertEquals(false, metadata.isDir());
		assertEquals("page_white", metadata.getIcon());
		assertEquals("dropbox", metadata.getRoot());
		assertEquals("application/octet-stream", metadata.getMimeType());
		assertEquals("12KB", metadata.getSize());
	}
    
    @Test
   	public void getMedia() throws Exception {
       	mockServer
           .expect(requestTo(DropboxTemplate.MEDIA_URL
           		.replaceFirst("\\{appFolderUrl\\}", "dropbox")
           		.replaceFirst("\\{path\\}", "file3.json")))
           .andExpect(method(GET))
           .andRespond(withSuccess(jsonResource("/media"), MediaType.APPLICATION_JSON));
   		
   		FileUrl url = dropbox.getMedia("file3.json");
   		assertEquals("https://dl.dropbox.com/0/view/6rcp09bdfz1kxfv/file3.json", url.getUrl());
   		assertEquals(fromDropboxDate("Sun, 15 Jan 2012 15:24:47 +0000"), url.getExpires());
   	}
    
    @Test
   	public void getShare() throws Exception {
       	mockServer
           .expect(requestTo(DropboxTemplate.SHARES_URL
           		.replaceFirst("\\{appFolderUrl\\}", "dropbox")
           		.replaceFirst("\\{path\\}", "file3.json")))
           .andExpect(method(GET))
           .andRespond(withSuccess(jsonResource("/share"), MediaType.APPLICATION_JSON));
   		
   		FileUrl url = dropbox.getShare("file3.json");
   		assertEquals("http://db.tt/LnS1qL1q", url.getUrl());
   		assertEquals(fromDropboxDate("Sun, 15 Jan 2012 15:24:47 +0000"), url.getExpires());
   	}
    
    @Test
	public void searchFile() throws Exception {
    	mockServer
        .expect(requestTo(DropboxTemplate.SEARCH_URL
        		.replaceFirst("\\{appFolderUrl\\}", "dropbox")
        		.replaceFirst("\\{path\\}", "")
        		+ "?query=json"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("/search"), MediaType.APPLICATION_JSON));
		
		List<Metadata> list = dropbox.search("", "json");
		assertEquals(3, list.size());
		Metadata metadata = list.get(0);
		
		assertEquals("8d02a9405f", metadata.getRev());
		assertEquals(false, metadata.isThumbExists());
		assertEquals(12265, metadata.getBytes());
		assertEquals(fromDropboxDate("Fri, 16 Dec 2011 12:23:30 +0000"), metadata.getModified());
		assertEquals("/file2.json", metadata.getPath());
		assertEquals(false, metadata.isDir());
		assertEquals("page_white", metadata.getIcon());
		assertEquals("dropbox", metadata.getRoot());
		assertEquals("application/octet-stream", metadata.getMimeType());
		assertEquals("12KB", metadata.getSize());
	}
    
    @Test
	public void createFolder() throws Exception {
    	mockServer
        .expect(requestTo(DropboxTemplate.CREATE_FOLDER_URL))
        .andExpect(method(POST))
        .andRespond(withSuccess(jsonResource("/create_folder"), MediaType.APPLICATION_JSON));
		
		Metadata metadata = dropbox.createFolder("test");
		assertEquals("8f02a9405f", metadata.getRev());
		assertEquals(false, metadata.isThumbExists());
		assertEquals(0, metadata.getBytes());
		assertEquals(fromDropboxDate("Fri, 16 Dec 2011 12:35:37 +0000"), metadata.getModified());
		assertEquals("/test", metadata.getPath());
		assertEquals(true, metadata.isDir());
		assertEquals("folder", metadata.getIcon());
		assertEquals("dropbox", metadata.getRoot());
		assertEquals("0 bytes", metadata.getSize());
	}
    
    @Test
   	public void delete() throws Exception {
       	mockServer
           .expect(requestTo(DropboxTemplate.DELETE_URL))
           .andExpect(method(POST))
           .andRespond(withSuccess(jsonResource("/delete"), MediaType.APPLICATION_JSON));
   		
   		Metadata metadata = dropbox.delete("file3.json");
   		assertEquals("9002a9405f", metadata.getRev());
   		assertEquals(false, metadata.isThumbExists());
   		assertEquals(0, metadata.getBytes());
   		assertEquals(fromDropboxDate("Fri, 16 Dec 2011 12:57:00 +0000"), metadata.getModified());
   		assertEquals("/file3.json", metadata.getPath());
   		assertEquals(false, metadata.isDir());
   		assertEquals("page_white", metadata.getIcon());
   		assertEquals("dropbox", metadata.getRoot());
   		assertEquals("0 bytes", metadata.getSize());
   		assertEquals(true, metadata.isDeleted());
		assertEquals("application/octet-stream", metadata.getMimeType());
   	}
    
    @Test
	public void getFile() throws Exception {
    	
    	HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_JSON);
        h.setContentLength(1234);
        
    	mockServer
        .expect(requestTo(DropboxTemplate.FILE_URL
        		.replaceFirst("\\{appFolderUrl\\}", "dropbox")
        		.replaceFirst("\\{path\\}", "Getting%20Started.pdf")))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("/metadata"), MediaType.APPLICATION_JSON).headers(h));
    	
		DropboxFile file = dropbox.getFile("Getting Started.pdf");
		byte[] bytes = file.getBytes();
		assertEquals(1234, bytes.length);
	}
    
    @Test
    public void getRevisions() throws Exception {
    	mockServer.expect(requestTo(DropboxTemplate.REVISIONS_URL.replaceFirst("\\{appFolderUrl\\}", "dropbox").replaceFirst("\\{path\\}", "file.json"))).andExpect(method(GET))
		.andRespond(withSuccess(jsonResource("/revisions"), MediaType.APPLICATION_JSON));
		List<Metadata> revisions = dropbox.getRevisions("file.json");
		
		Metadata file = revisions.get(0);
		assertEquals("7702a9405f", file.getRev());
		assertEquals(false, file.isThumbExists());
		assertEquals(12265, file.getBytes());
		assertEquals(fromDropboxDate("Fri, 02 Dec 2011 11:27:27 +0000"), file.getModified());
		assertEquals("/file.json", file.getPath());
		assertEquals(false, file.isDir());
		assertEquals("12KB", file.getSize());
		assertEquals("dropbox", file.getRoot());
		assertEquals("page_white", file.getIcon());
		assertEquals("application/octet-stream", file.getMimeType());
		
		file = revisions.get(1);
		assertEquals("7602a9405f", file.getRev());
		assertEquals(false, file.isThumbExists());
		assertEquals(556035, file.getBytes());
		assertEquals(fromDropboxDate("Fri, 02 Dec 2011 11:24:16 +0000"), file.getModified());
		assertEquals("/file.json", file.getPath());
		assertEquals(false, file.isDir());
		assertEquals("543KB", file.getSize());
		assertEquals("dropbox", file.getRoot());
		assertEquals("page_white", file.getIcon());
		assertEquals("application/octet-stream", file.getMimeType());
    }
    
    @Test
	public void getMetadata() throws Exception {
		mockServer.expect(requestTo(DropboxTemplate.METADATA_URL.replaceFirst("\\{appFolderUrl\\}", "dropbox").replaceFirst("\\{path\\}", ""))).andExpect(method(GET))
		.andRespond(withSuccess(jsonResource("/metadata"), MediaType.APPLICATION_JSON));
		Metadata metadata = dropbox.getItemMetadata("");
		
		assertEquals("0881bfe7f09e0fe856cf9a27000ac00c", metadata.getHash());
		assertEquals(false, metadata.isThumbExists());
		assertEquals(0, metadata.getBytes());
		assertEquals("/", metadata.getPath());
		assertEquals(true, metadata.isDir());
		assertEquals("0 bytes", metadata.getSize());
		assertEquals("dropbox", metadata.getRoot());
		assertEquals("folder", metadata.getIcon());
		
		List<Metadata> contents = metadata.getContents();
		assertEquals(9, contents.size());
		
		Metadata folder = contents.get(0);
		assertEquals("1202a9405f", folder.getRev());
		assertEquals(false, folder.isThumbExists());
		assertEquals(0, folder.getBytes());
		assertEquals(fromDropboxDate("Wed, 08 Jun 2011 20:58:29 +0000"), folder.getModified());
		assertEquals("/cv", folder.getPath());
		assertEquals(true, folder.isDir());
		assertEquals("0 bytes", folder.getSize());
		assertEquals("dropbox", folder.getRoot());
		assertEquals("folder", folder.getIcon());
		
		Metadata file = contents.get(1);
		assertEquals("702a9405f", file.getRev());
		assertEquals(false, file.isThumbExists());
		assertEquals(230783, file.getBytes());
		assertEquals(fromDropboxDate("Thu, 05 May 2011 14:28:24 +0000"), file.getModified());
		assertEquals("/Getting Started.pdf", file.getPath());
		assertEquals(false, file.isDir());
		assertEquals("225.4KB", file.getSize());
		assertEquals("dropbox", file.getRoot());
		assertEquals("page_white_acrobat", file.getIcon());
		assertEquals("application/pdf", file.getMimeType());
	}
}
