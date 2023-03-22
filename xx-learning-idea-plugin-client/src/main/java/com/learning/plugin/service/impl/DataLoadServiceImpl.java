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
        StringBuffer sb  = new StringBuffer();
        sb.append("### Redis为什么这么快？");
        sb.append("####Redis 之所以这么快，主要是由于以下几个方面的设计和实现：");
        sb.append("1. 内存存储：Redis 将数据存储在内存中，而非磁盘上，因此访问数据的速度非常快。");
        sb.append("2. 单线程模型：Redis 是单线程模型的，这意味着 Redis 可以避免多线程并发操作时的锁竞争和上下文切换等开销，从而大大提高了性能。");
        sb.append("3. 非阻塞 I/O：Redis 使用了非阻塞 I/O 模型，能够高效地处理大量的并发连接请求。");
        sb.append("4. 数据结构优化：Redis 对各种数据结构进行了优化，例如对于字符串类型的数据，Redis 使用了简单动态字符串，对于哈希类型的数据，Redis 使用了哈希表等等，这些优化都能够提高 Redis 的性能。");
        sb.append("5. 持久化机制：Redis 提供了多种持久化机制，包括 RDB 和 AOF，能够将内存中的数据保存到磁盘中，从而保证数据的安全性。");
        return sb.toString();
        // return HttpUtil.get("http://127.0.0.1:8081/question/random/text");
    }
}
