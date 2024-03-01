package com.learning.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.fastjson2.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author over.li
 * @since 2023/3/28
 */
public final class QRCodeUtil {

    private static final Logger logger = LogManager.getLogger(QRCodeUtil.class);

    private QRCodeUtil() {
    }

    public static void main(String[] args) throws IOException, WriterException {

        final String filePath = "/Users/flipos/Desktop/【她力量】优惠券";

        List<String> excelFileList = Arrays.asList(
                "300元她力量礼品卡赠送-风味加料兑换券.xlsx",
                "300元她力量礼品卡赠送-满88元-10元优惠券.xlsx",
                "300元她力量礼品卡赠送-沃乐送满80元减6元优惠券.xlsx",
                "500元她力量礼品卡赠送-满88元-10元优惠券.xlsx",
                "500元她力量礼品卡赠送-满158元-28元优惠券.xlsx",
                "500元她力量礼品卡赠送-沃乐送满80元减6元优惠券.xlsx",
                "500元她力量礼品卡赠送-指定中杯鲜榨果汁兑换券.xlsx",
                "1000元她力量礼品卡赠送-满88元-10元优惠券.xlsx",
                "1000元她力量礼品卡赠送-满158元-28元优惠券.xlsx",
                "1000元她力量礼品卡赠送-指定中杯鲜榨果汁兑换券.xlsx"
        );

        for (String fileName : excelFileList) {
            generateQRCodeByExcel(1, filePath, fileName);
        }
    }

    private static void generateQRCodeByExcel(int couponColumnIndex, String filePath, String fileName) throws IOException, WriterException {
        EasyExcel.read(new File(String.format("%s/%s", filePath, fileName)), new AnalysisEventListener<Map<Integer, String>>() {

            private final static List<String> couponList = new ArrayList<>(512);

            @Override
            public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
                super.invokeHead(headMap, context);
            }

            @Override
            public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
                String coupon = StringUtils.trim(integerStringMap.get(couponColumnIndex));
                if(StringUtils.isNotBlank(coupon) && StringUtils.length(coupon) >= 5) {
                    couponList.add(coupon);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                try {
                    generateQRCode(filePath, fileName, couponList);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    couponList.clear();
                }
            }

        }).sheet().doRead();
    }

    private static void generateQRCodeByText(String filePath, String fileName) throws IOException, WriterException {
        List<String> contentList = FileUtils.readLines(new File(String.format("%s/%s.txt", filePath, fileName)), "UTF-8");

        int index = 1;
        for (String content : contentList) {

            byte[] bytes = generateQRCode(content, 5);

            FileUtil.base64ToImage(bytes, String.format("%s/%s/%s.png", filePath, fileName, content));

            index++;
        }
        logger.info("fileName : {}, total: {}", fileName, index - 1);
    }

    private static void generateQRCode(String filePath, String fileName, List<String> contentList) throws IOException, WriterException {

        fileName = StringUtils.substringBeforeLast(fileName, ".");

        int index = 1;
        for (String content : contentList) {

            byte[] bytes = generateQRCode(content, 5);

            FileUtil.base64ToImage(bytes, String.format("%s/%s/%s.png", filePath, fileName, content));

            index++;
        }
        logger.info("fileName : {}, total: {}", fileName, index - 1);
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
        if (margin <= 0) {
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
