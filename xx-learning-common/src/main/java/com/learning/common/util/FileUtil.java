package com.learning.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * @author over.li
 * @since 2023/3/29
 */
public final class FileUtil {

    protected static final Logger logger = LogManager.getLogger(FileUtil.class);


    private FileUtil() {
    }

    public static void writeByteArrayToFile(String path, byte[] bytes) throws IOException {
        writeByteArrayToFile(new File(path), bytes);
    }

    public static void writeByteArrayToFile(File file, byte[] bytes) throws IOException {
        FileUtils.writeByteArrayToFile(file, bytes);
        if(logger.isDebugEnabled()){
            logger.info("file path : {}", file.getPath());
        }
    }
}
