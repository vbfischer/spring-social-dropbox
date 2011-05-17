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
    private String uid;
    private BigInteger sharedQuota;
    private BigInteger quota;
    private BigInteger normalQuota;

    public DropboxUserProfile(String country, String displayName, String uid, BigInteger sharedQuota, BigInteger quota, BigInteger normalQuota) {

        this.country = country;
        this.displayName = displayName;
        this.uid = uid;
        this.sharedQuota = sharedQuota;
        this.quota = quota;
        this.normalQuota = normalQuota;
    }

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

    public String getUid() {
        return uid;
    }
}
