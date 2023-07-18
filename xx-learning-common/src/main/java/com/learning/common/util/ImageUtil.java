package com.learning.common.util;

import cn.hutool.core.io.FileUtil;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author over.li
 * @since 2023/5/29
 */
public class ImageUtil {

    protected static final Logger logger = LogManager.getLogger(com.learning.common.util.FileUtil.class);


    public static void main(String[] args) throws IOException {

        final String sourceImageFileDir = "/Users/flipos/Desktop/230715";
        final String destImageFileDir = "/Users/flipos/Desktop/230715-水印版";

        final String pressImgFilePath = "/Users/flipos/Desktop/workspace/xx-learning-boot/xx-learning-common/src/main/resources/WechatIMG705.png";

        batchPressImage(sourceImageFileDir, destImageFileDir, pressImgFilePath, 1F);
    }
    
    public static void batchPressImage(String sourceImageFileDir,
                                       String destImageFileDir,
                                       String pressImgFilePath,
                                       float alpha) throws IOException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        File pressImgFile = FileUtil.file(pressImgFilePath);
        File tempFile = File.createTempFile("tmp_" + UUID.randomUUID(), ".png");
        Thumbnails.of(pressImgFile).scale(0.6F).toFile(tempFile);

        BufferedImage pressImg = ImageIO.read(tempFile);

        int index = 1;
        File dir = new File(sourceImageFileDir);
        for (File file : dir.listFiles()) {

            if(!StringUtils.endsWithIgnoreCase(file.getPath(), ".JPG")){
                logger.warn("file:{}, error:{}", file, "不支持的图片类型。");
                continue;
            }

            logger.info("file : {}, index: {}", file, index);

            CompletableFuture.runAsync(() -> {
                try {
                    pressImage(file.getPath(),
                            pressImg,
                            destImageFileDir,
                            alpha);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, executorService);

            index++;
        }

        FileUtils.delete(tempFile);
    }

    public static void pressImage(String srcImageFilePath,
                                  BufferedImage pressImg,
                                  String destPath,
                                  float alpha) throws IOException {
        File srcImageFile = FileUtil.file(srcImageFilePath);

        Thumbnails.Builder<File> srcImageBuilder = Thumbnails.of(srcImageFile).scale(1);

        float scale = 0.6F;// Math.min((float) srcImageBuilder.asBufferedImage().getWidth() / (float)pressBufferedImage.getWidth(), 1.0F);

        logger.info("scale : {}", scale);

        File outFile = FileUtil.file(destPath + srcImageFile.getName());

        FileUtils.forceMkdirParent(outFile);

        srcImageBuilder.watermark(TopLeft.TOP_LEFT_100, pressImg, alpha)
                .toFile(outFile);

    }


    public static void pressImage(String srcImageFilePath,
                                  File pressImgFile,
                                  String destPath,
                                  float alpha) throws IOException {
        pressImage(srcImageFilePath, ImageIO.read(pressImgFile), destPath, alpha);
    }

    public static void pressImage(String srcImageFilePath,
                                  String pressImgFilePath,
                                  String destPath,
                                  float alpha) throws IOException {
        File srcImageFile = FileUtil.file(srcImageFilePath);
        File pressImgFile = FileUtil.file(pressImgFilePath);

        Thumbnails.Builder<File> srcImageBuilder = Thumbnails.of(srcImageFile).scale(1);
        BufferedImage pressBufferedImage = Thumbnails.of(pressImgFile).scale(1).asBufferedImage();

        logger.info("image width:{}, height:{}", srcImageBuilder.asBufferedImage().getWidth(), srcImageBuilder.asBufferedImage().getHeight());
        logger.info("press width:{}, height:{}", pressBufferedImage.getWidth(), pressBufferedImage.getHeight());

        float scale = 0.6F;// Math.min((float) srcImageBuilder.asBufferedImage().getWidth() / (float)pressBufferedImage.getWidth(), 1.0F);

        logger.info("scale : {}", scale);

        File tempFile = File.createTempFile("tmp_" + UUID.randomUUID(), ".png");
        Thumbnails.of(pressImgFile).scale(scale).toFile(tempFile);

        File outFile = FileUtil.file(destPath + srcImageFile.getName());

        srcImageBuilder.watermark(TopLeft.TOP_LEFT_100, ImageIO.read(tempFile), alpha)
                .toFile(outFile);

        FileUtils.delete(tempFile);
    }

    static class TopLeft implements Position {

        public static final Position TOP_LEFT_100 = new TopLeft(100, 100);

        private int x;

        private int y;

        public TopLeft(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public Point calculate(int enclosingWidth, int enclosingHeight, int width, int height, int insetLeft, int insetRight, int insetTop, int insetBottom) {
            return new Point(insetLeft + x, insetTop + y);
        }
    }
}
