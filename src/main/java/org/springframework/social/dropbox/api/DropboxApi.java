package org.springframework.social.dropbox.api;

import java.math.BigInteger;
import java.util.List;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 8:57 AM
 */
public interface DropboxApi {
    DropboxUserProfile getUserProfile();

    List<DropboxItemMetadata> getItemMetadata(String path, boolean list, BigInteger maxFiles);
}
