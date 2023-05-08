package com.learning.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

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

    public static byte[] readFile(File file) throws IOException {
        return FileUtils.readFileToByteArray(file);
    }

    public static String base64ToImage(String base64, String targetFilePath){

        String fileName = targetFilePath + "/1.jpg";

        try {
            byte[] imageByteArray = Base64.decodeBase64(base64);
            FileOutputStream imageOutFile = new FileOutputStream(fileName);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        } catch (IOException e) {
            System.out.println("Exception occurred while converting the image.");
        }



        // try {
        //     writeByteArrayToFile(fileName, Base64.decodeBase64(base64));
        // } catch (IOException e) {
        //     logger.error("Base64 to image fail, error:{}", e.getMessage(),e);
        // }

        return fileName;
    }


}
