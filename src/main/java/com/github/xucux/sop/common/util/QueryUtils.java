package com.github.xucux.sop.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @descriptions: query参数分割组装工具
 * @author: xucl
 * @date: 2021/8/24
 * @version: 1.0
 */
public class QueryUtils {

    public static Map<String,String> split(String query){
        Map<String,String> map = new HashMap<>();
        if (StringUtils.isBlank(query)){
            return map;
        }
        String[] param = query.split("&");
        for(String keyValue:param){
            String[] pair = keyValue.split("=");
            if( pair.length == 2){
                try{
                    final String key = URLDecoder.decode( pair[0],"utf8");
                    final String val = URLDecoder.decode( pair[1],"utf8");
                    map.put(key,val);
                }catch (UnsupportedEncodingException e) {}
            }
        }
        return map;
    }
}
