package main.java.org.springframework.social.dropbox.api;

import java.math.BigInteger;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 9:15 AM
 */
public class DropboxUserProfile {
    private String country;
    private final String referralLink;
    private String displayName;
    private final String email;
    private BigInteger uid;
    private BigInteger sharedQuota;
    private BigInteger quota;
    private BigInteger normalQuota;

    public DropboxUserProfile(BigInteger uid, String displayName, String email, String country, String referralLink, BigInteger sharedQuota, BigInteger quota, BigInteger normalQuota) {
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
        this.country = country;
        this.referralLink = referralLink;
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

    public BigInteger getUid() {
        return uid;
    }

    public String getReferralLink() {
        return referralLink;
    }

    public String getEmail() {
        return email;
    }
}
