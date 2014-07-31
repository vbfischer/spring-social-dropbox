package org.springframework.social.dropbox.api.impl;

import org.springframework.social.dropbox.api.DropboxUserProfile;
import org.springframework.social.dropbox.api.FileUrl;
import org.springframework.social.dropbox.api.Metadata;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author Bryce Fischer
 * @author Robert Drysdale
 */
@SuppressWarnings("serial")
public class DropboxModule extends SimpleModule {
    public DropboxModule() {
        super("DropboxModule");
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(DropboxUserProfile.class, DropboxUserProfileMixin.class);
        context.setMixInAnnotations(Metadata.class, MetadataMixin.class);
        context.setMixInAnnotations(FileUrl.class, FileUrlMixin.class);
    }
}
