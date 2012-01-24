package org.springframework.social.dropbox.api.impl;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.social.dropbox.api.DropboxUserProfile;
import org.springframework.social.dropbox.api.FileUrl;
import org.springframework.social.dropbox.api.Metadata;

/**
 * @author Bryce Fischer
 * @author Robert Drysdale
 */
public class DropboxModule extends SimpleModule {
    public DropboxModule() {
        super("DropboxModule", new Version(1, 0, 0, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(DropboxUserProfile.class, DropboxUserProfileMixin.class);
        context.setMixInAnnotations(Metadata.class, MetadataMixin.class);
        context.setMixInAnnotations(FileUrl.class, FileUrlMixin.class);
    }
}
