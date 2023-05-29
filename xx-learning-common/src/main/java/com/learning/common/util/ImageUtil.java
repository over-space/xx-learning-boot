package com.learning.common.util;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.imageio.ImageIO;

/**
 * @author over.li
 * @since 2023/5/29
 */
public class ImageUtil {

    protected static final Logger logger = LogManager.getLogger(com.learning.common.util.FileUtil.class);

    public static void main(String[] args) throws IOException {

        // pressImage("/Users/flipos/Downloads/LEE03999.JPG",
        //         "/Users/flipos/Downloads/WechatIMG705.png",
        //         10, 50,
        //         1F);

        // pressImage("/Users/flipos/Downloads/LEE03999.JPG",
        //         "/Users/flipos/Downloads/WechatIMG705.png",
        //         "/Users/flipos/Downloads/230528-LOGO/",
        //         10, 50,
        //         1F);

        File dir = new File("/Users/flipos/Downloads/230528");
        for (File file : dir.listFiles()) {

            logger.info("file : {}", file);

            pressImage(file.getPath(),
                    "/Users/flipos/Downloads/WechatIMG705.png",
                    "/Users/flipos/Downloads/230528-LOGO/",
                    10, 50,
                    1F);
        }

        // pressImage("/Users/flipos/Downloads/LEE03999.JPG",
        //         "/Users/flipos/Downloads/WechatIMG705.png",
        //         10, 50,
        //         1F);

    }

    public static void pressImage(String srcImageFilePath, String pressImgFilePath,
            String destPath, int x, int y, float alpha) throws IOException {
        File srcImageFile = FileUtil.file(srcImageFilePath);
        File pressImgFile = FileUtil.file(pressImgFilePath);

        // 读取图片和Logo
        BufferedImage srcImage = ImageIO.read(srcImageFile);
        BufferedImage pressImage = ImageIO.read(pressImgFile);

        float scale = srcImage.getWidth() / pressImage.getWidth() / 2.0F;

        logger.info("scale : {}", scale);

        // 水印图片大小
        File tempFile;
        if (scale >= 1) {
            tempFile = pressImgFile;
        } else if(scale > 0F){
            tempFile = File.createTempFile("tmp_" + UUID.randomUUID().toString(), ".png");
            ImgUtil.scale(pressImgFile, tempFile, scale);
            tempFile.deleteOnExit();
        } else {
            tempFile = File.createTempFile("tmp_" + UUID.randomUUID().toString(), ".png");
            ImgUtil.scale(pressImgFile, tempFile, 0.15F);
            tempFile.deleteOnExit();
        }

        BufferedImage tmpPressImage = ImageIO.read(tempFile);

        // 坐标从左上角开始计算。
        x = -srcImage.getWidth() / 2 + tmpPressImage.getWidth() / 2 + x;
        y = -srcImage.getHeight() / 2 + tmpPressImage.getHeight() / 2 + y;

        ImgUtil.pressImage(
                srcImageFile,
                cn.hutool.core.io.FileUtil.file(destPath + srcImageFile.getName()),
                ImgUtil.read(tempFile), // 水印图片
                x, // x坐标修正值。 默认在中间，偏移量相对于中间偏移
                y, // y坐标修正值。 默认在中间，偏移量相对于中间偏移
                alpha
        );

    }
}
