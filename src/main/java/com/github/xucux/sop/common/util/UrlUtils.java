package com.github.xucux.sop.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class UrlUtils {

    private static Pattern patternForProtocal = Pattern.compile("[\\w]+://");

    public static String removeProtocol(String url) {
        return patternForProtocal.matcher(url).replaceAll("");
    }

    public static String getDomain(String url) {
        String domain = removeProtocol(url);
        int i = StringUtils.indexOf(domain, "/", 1);
        if (i > 0) {
            domain = StringUtils.substring(domain, 0, i);
        }
        return removePort(domain);
    }

    public static String removePort(String domain) {
        int portIndex = domain.indexOf(":");
        if (portIndex != -1) {
            return domain.substring(0, portIndex);
        }else {
            return domain;
        }
    }
}
