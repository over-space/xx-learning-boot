package com.learning.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.learning.plugin.service.DataLoadService;
import com.learning.plugin.service.impl.DataLoadServiceImpl;
import com.learning.plugin.ui.QuestionUI;
import com.learning.plugin.ui.UIHolder;
import com.learning.plugin.util.HtmlRendererUtil;
import com.learning.plugin.util.HttpUtil;
import com.learning.plugin.vo.QuestionVO;
import com.learning.plugin.vo.ResponseResult;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class QuestionWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        QuestionUI holder = UIHolder.questionUI;

        // 获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        // 获取 ToolWindow 显示的内容
        Content content = contentFactory.createContent(holder.textContent, "", true);

        // 设置 ToolWindow 显示的内容
        toolWindow.getContentManager().addContent(content);

        try {

            CompletableFuture.runAsync(() -> {
                UIHolder.displayWelcomeContent();
            }).thenApply((result) -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                }catch (Exception e){
                    e.printStackTrace();
                }
                UIHolder.refreshViewContent();
                return null;
            });

            UIHolder.nextButtonListener();

            UIHolder.prevButtonListener();

        }catch (Exception e){
            e.printStackTrace();;
        }
    }
}
