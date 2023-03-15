package com.learning.plugin.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author over.li
 * @since 2023/3/10
 */
public final class FileUtil {

    public static String read(String path){
        return read(new File(path));
    }

    public static String read(File file) {
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")){
            randomAccessFile.seek(0);

            byte[] bytes = new byte[1024];

            int readSize = randomAccessFile.read(bytes);

            byte[] copy = new byte[readSize];
            System.arraycopy(bytes, 0, copy, 0, readSize);

            return new String(copy, StandardCharsets.UTF_8);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String read(InputStream in){
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            byte[] bytes = os.toByteArray();
            return new String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            }catch (Exception e){

            }
        }
    }
}
