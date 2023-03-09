package com.learning.plugin;

import com.alibaba.fastjson2.JSON;
import com.intellij.ide.plugins.cl.PluginClassLoader;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.lang.UrlClassLoader;
import com.learning.plugin.ui.QuestionUI;
import com.learning.plugin.util.HtmlRendererUtil;
import com.learning.plugin.util.HttpUtil;
import com.learning.plugin.vo.QuestionVO;
import com.learning.plugin.vo.ResponseResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class QuestionWindowFactory implements ToolWindowFactory {

    private static Logger logger = LogManager.getLogger(QuestionWindowFactory.class);

    private static QuestionUI readUI = new QuestionUI();

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        // 获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        // 获取 ToolWindow 显示的内容
        Content content = contentFactory.createContent(readUI.getComponent(), "", false);

        // 设置 ToolWindow 显示的内容
        toolWindow.getContentManager().addContent(content);

        // 全局使用
        // Config.readUI = readUI;

        try {
            QuestionVO questionVO = get();

            String text = questionVO.getTitle() + "\n" + questionVO.getContent();

            String html = HtmlRendererUtil.parser(text);

            readUI.getTextContent().setText(html);

            readUI.getNextButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String html = HtmlRendererUtil.parser(text);
                    readUI.getTextContent().setText(html);
                }
            });

            readUI.getPrevButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String html = HtmlRendererUtil.parser(text);
                    readUI.getTextContent().setText(html);
                }
            });

        }catch (Exception e){
            e.printStackTrace();;
        }
    }

    private QuestionVO get(){
        ResponseResult<QuestionVO> responseResult = HttpUtil.get("http://127.0.0.1:8081/question/random", QuestionVO.class);
        return responseResult.getData();
    }

    private String readFile(File file) throws IOException {

        byte[] bytes = new byte[1024 * 1024];

        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        randomAccessFile.seek(0);
        int readSize = randomAccessFile.read(bytes);

        byte[] copy = new byte[readSize];
        System.arraycopy(bytes, 0, copy, 0, readSize);

        return new String(copy, StandardCharsets.UTF_8);
    }

}
