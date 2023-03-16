package net.backlogic.persistence.client.proxy;

/*
 * Provide utility method to formulate service url
 */
public class UrlUtil {

    public static String getUrl(String interfaceUrl, String methodUrl) {
        return formatUrl(interfaceUrl) + formatUrl(methodUrl);
    }

    /*
     * Format URL
     * Expected to be  "" or "/abc.../xyz"
     * Could be null, "/", "abc.../xyz", "/abc.../xyz/", etc
     */
    public static String formatUrl(String url) {
        //null
        if (url == null) {
            url = "";
        }
        //missing start "/"
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        //extra end "/"
        if (url.endsWith("/")) {
            if (url.equals("/")) {
                url = "";
            } else {
                url = url.substring(0, url.length() - 1);
            }
        }
        return url;
    }

}
