package com.learning.plugin.service.impl;

import com.learning.plugin.service.DataLoadService;
import com.learning.plugin.util.HttpUtil;

/**
 * @author over.li
 * @since 2023/3/10
 */
public class DataLoadServiceImpl implements DataLoadService {

    @Override
    public String loadWelcomeContent() {
        String content = "### 欢迎使用Learning插件\n" +
                "\n" +
                "希望大家能一同进步。\n" +
                "\n" +
                "版本：1.0.0\n" +
                "\n" +
                "开发者：\n" +
                "\n" +
                "邮件：xxplus@outlook.com\n" +
                "\n" +
                "GitHub: https://github.com/over-space";
        return content;
    }

    @Override
    public String randomLoadContent() {
        return HttpUtil.get("http://127.0.0.1:8081/question/random/text");
    }
}
