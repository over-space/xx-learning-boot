package com.learning.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author over.li
 * @since 2023/3/28
 */
public final class QRCodeUtil {

    protected static final Logger logger = LogManager.getLogger(QRCodeUtil.class);

    private QRCodeUtil() {
    }

    public static byte[] generateQRCode(String content, int margin) throws IOException, WriterException {
        return generateQRCode(content, 300, 300, margin, ErrorCorrectionLevel.H);
    }

    public static String generateQRCodeToBase64(String content) throws IOException, WriterException {
        byte[] bytes = generateQRCode(content, 5);
        return DigestUtil.base64(bytes);
    }

    public static byte[] generateQRCode(String content, int height, int width, int margin, ErrorCorrectionLevel level) throws IOException, WriterException {
        String format = "gif";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 内容编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, level);
        // 设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 3);
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            MatrixToImageWriter.writeToStream(updateBitMatrixMargin(bitMatrix, margin), format, bos);// 输出原图片

            return bos.toByteArray();
        }
    }

    private static BitMatrix updateBitMatrixMargin(BitMatrix matrix, int margin) {
        if(margin <= 0){
            return matrix;
        }

        int tempM = margin * 2;
        int[] rec = matrix.getEnclosingRectangle();   // 获取二维码图案的属性
        int resWidth = rec[2] + tempM;
        int resHeight = rec[3] + tempM;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
        resMatrix.clear();
        for (int i = margin; i < resWidth - margin; i++) {   // 循环，将二维码图案绘制到新的bitMatrix中
            for (int j = margin; j < resHeight - margin; j++) {
                if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

}
