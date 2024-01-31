package com.learning.common.util;

import com.alibaba.fastjson2.JSONObject;
import com.beust.jcommander.internal.Lists;
import com.google.zxing.WriterException;
import com.learning.logger.BaseTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static void main(String[] args) {
        List<Integer> hqIds = Arrays.asList(2856,
                5221, 17038, 5908, 2337, 28834, 6824, 4985, 4418, 3415, 28058, 23758, 25788, 14139, 7029, 3746, 24456, 323, 25275, 28511, 2382, 28426, 3880,
                15273, 14620, 28565, 14153, 14413, 4572, 15441, 27632, 4521, 27493, 2484, 3407, 5472, 3309, 6051, 3394, 4316, 27877, 13751, 17041, 4300, 4480, 13920, 14241, 1536, 2166, 14873, 14979, 1516,
                4926, 14696, 14783, 16703, 13738, 15003, 23882, 6963, 27994, 25790, 5269, 3051, 27792, 27802, 7125, 1919, 13911, 15421, 3057, 25798, 17039, 6046, 23876, 1783, 5227, 27233, 23797,
                27218, 6081, 5305, 13778, 24337, 3686, 14444, 15310, 15366, 337, 15757, 3861, 16189, 5499, 14042, 14521, 13814, 28882, 23695, 28892, 14434, 28017, 27631, 5780, 26372, 25362, 257, 26475, 199, 1439, 24543,
                4883, 4075, 5096, 26609, 4852, 26610, 24304, 27293, 27279, 24443, 27061, 29115, 28091, 28718, 15906, 7027, 2581, 28037, 5337, 24796, 26787);

        for (Integer hqId : hqIds) {
            // String url1 = "curl -v -X PUT" + "http://172.18.58.248:8003/order-item?start=2023-11-07%2000:00:01&end=2023-11-07%2023:59:59&hqId=" + hqId;
            // String url2 = "curl -v -X PUT" + "http://172.18.58.248:8003/order-item?start=2023-11-08%2000:00:01&end=2023-11-08%2023:59:59&hqId=" + hqId;
            // String url3 = "curl -v -X PUT" + "http://172.18.58.248:8003/order-item?start=2023-11-09%2000:00:01&end=2023-11-09%2023:59:59&hqId=" + hqId;
            // String url4 = "curl -v -X PUT" + "http://172.18.58.248:8003/order-item?start=2023-11-10%2000:00:01&end=2023-11-10%2023:59:59&hqId=" + hqId;
            // String url5 = "curl -v -X PUT" + "http://172.18.58.248:8003/order-item?start=2023-11-11%2000:00:01&end=2023-11-11%2023:59:59&hqId=" + hqId;
            // String url6 = "curl -v -X PUT" + "http://172.18.58.248:8003/order-item?start=2023-11-12%2000:00:01&end=2023-11-12%2023:59:59&hqId=" + hqId;
            // String url7 = "curl -v -X PUT" + "http://172.18.58.248:8003/order-item?start=2023-11-13%2000:00:01&end=2023-11-13%2023:59:59&hqId=" + hqId;
            // String url8 = "curl -v -X PUT" + "http://172.18.58.248:8003/order-item?start=2023-11-14%2000:00:01&end=2023-11-14%2023:59:59&hqId=" + hqId;

            for (int i = 7; i <= 14; i++) {
                String c1 = String.format("call p_create_finance_report_hq3(%d,'2023-11-%02d',0);", hqId, i);
                String c2 = String.format("call p_create_finance_report_hq3(%d,'2023-11-%02d',1);", hqId, i);
                System.out.println(c1);
                System.out.println(c2);
            }
        }
    }
}
