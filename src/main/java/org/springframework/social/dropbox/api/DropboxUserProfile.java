package org.springframework.social.dropbox.api;

import java.math.BigInteger;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 9:15 AM
 */
public class DropboxUserProfile {
    private String country;
    private String displayName;
    private BigInteger sharedQuota;
    private BigInteger quota;
    private BigInteger normalQuota;

    public String getCountry() {
        return country;
    }

    public String getDisplayName() {
        return displayName;
    }

    public BigInteger getSharedQuota() {
        return sharedQuota;
    }

    public BigInteger getQuota() {
        return quota;
    }

    public BigInteger getNormalQuota() {
        return normalQuota;
    }
}
