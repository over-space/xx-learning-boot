package com.learning.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Base64;

/**
 * @author over.li
 * @since 2023/3/29
 */
public final class DigestUtil {

    protected static final Logger logger = LogManager.getLogger(DigestUtil.class);

    private DigestUtil() {
    }

    public static String md5(String content){
        return DigestUtils.md5Hex(content);
    }

    public static String base64(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }
}
