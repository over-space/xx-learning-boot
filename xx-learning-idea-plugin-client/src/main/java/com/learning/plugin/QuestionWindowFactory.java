package com.learning.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.learning.plugin.ui.QuestionUI;
import com.learning.plugin.ui.UIHolder;
import org.jetbrains.annotations.NotNull;

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
                    TimeUnit.SECONDS.sleep(2);
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
