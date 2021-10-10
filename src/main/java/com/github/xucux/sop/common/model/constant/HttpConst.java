package com.github.xucux.sop.common.model.constant;

/**
 * @descriptions: 通用常量
 * @author: xucl
 * @date: 2021/8/23
 * @version: 1.0
 */
public interface HttpConst {

    String DOT = ".";

    String HTTP = "http://";

    String HTTPS = "https://";

    String BACKSLASH = "//";

    String UTF_8 = "utf-8";

    abstract class Method {

        public static final String GET = "GET";

        public static final String HEAD = "HEAD";

        public static final String POST = "POST";

        public static final String PUT = "PUT";

        public static final String DELETE = "DELETE";

        public static final String TRACE = "TRACE";

        public static final String CONNECT = "CONNECT";

    }

    abstract class StatusCode {

        public static final int CODE_200 = 200;

    }

    abstract class Header {

        public static final String COOKIE = "Cookie";

        public static final String REFERER = "Referer";

        public static final String USER_AGENT = "User-Agent";
    }

    abstract class ContentType {

        public static final String KEY ="Content-Type";

        public static final String JSON_KEY = "json";

        public static final String FORM_KEY = "x-www-form-urlencoded";

        public static final String MULTIPART_KEY = "multipart/form-data";

        public static final String JSON = "application/json";

        public static final String FORM = "application/x-www-form-urlencoded";

        public static final String MULTIPART = "multipart/form-data";
    }
}
