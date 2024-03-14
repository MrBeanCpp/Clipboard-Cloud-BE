package com.mrbeanc.util;

public final class Utils  {
    private Utils() {}
    /**
     * SHA256 hash is 64 characters long, we can omit some characters to make it more readable
     * @param hash SHA256 hash
     * @return SHA256 hash with omitted characters
     */
    static public String omitSHA256(String hash) {
        return hash.substring(0, 4) + "..." + hash.substring(hash.length() - 4);
    }
    /**
     * Truncate string to a certain length
     * @param str input string
     * @param len saved length
     * @return truncated string
     */
    static public String omit(String str, int len) {
        if (str.length() <= len) {
            return str;
        } else {
            return str.substring(0, len) + "...";
        }
    }
}
