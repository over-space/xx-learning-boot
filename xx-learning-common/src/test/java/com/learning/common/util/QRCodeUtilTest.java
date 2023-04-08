package com.learning.common.util;

import com.alibaba.fastjson2.JSONObject;
import com.google.zxing.WriterException;
import com.learning.logger.BaseTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author over.li
 * @since 2023/3/29
 */
public class QRCodeUtilTest extends BaseTest {

    @Test
    void testQRCode() throws IOException, WriterException {
        JSONObject qrData = new JSONObject();
        qrData.put("createTime", System.currentTimeMillis());
        qrData.put("expireIn", 7200); // 有效期2小时
        qrData.put("tpNumber", "00002");
        qrData.put("couponNumber", "20230328194625460CBC0069");
        qrData.put("md5", generateMD5(qrData));

        for (int i = 0; i <= 10; i++) {
            byte[] bytes = QRCodeUtil.generateQRCode(qrData.toString(), i);
            FileUtil.writeByteArrayToFile(String.format("/Users/flipos/Desktop/logs/%d.jpg", i), bytes);
        }
    }

    private static String generateMD5(JSONObject qrData) {
        try {
            Long createTime = qrData.getLong("createTime");
            Integer expireIn = qrData.getInteger("expireIn");
            String tpNumber = qrData.getString("tpNumber");
            String couponNumber = qrData.getString("couponNumber");

            String str = new StringBuilder().append("createTime=").append(createTime).append("&")
                    .append("expireIn=").append(expireIn).append("&")
                    .append("tpNumber=").append(tpNumber).append("&")
                    .append("couponNumber=").append(couponNumber).append("&")
                    .append("salt=").append("flipos.2023")
                    .toString();
            return DigestUtil.md5(str);
        } catch (Exception e) {
        }
        return "";
    }
}
