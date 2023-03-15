package com.learning.plugin.ui;

import com.learning.plugin.service.DataLoadService;
import com.learning.plugin.service.impl.DataLoadServiceImpl;
import com.learning.plugin.util.HtmlRendererUtil;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author over.li
 * @since 2023/3/15
 */
public final class UIHolder {

    public static final QuestionUI questionUI = new QuestionUI();

    private static DataLoadService dataLoadService = new DataLoadServiceImpl();


    public static void displayWelcomeContent(){
        String content = dataLoadService.loadWelcomeContent();
        refreshViewContent(content);
    }

    public static void refreshViewContent(){
        String content = dataLoadService.randomLoadContent();
        refreshViewContent(content);
    }

    public static void refreshViewContent(String content) {
        questionUI.textContent.setMargin(new Insets(10, 5, 10, 5));

        String text = HtmlRendererUtil.parser(content);

        String[] lines = StringUtils.split(text, "\n");

        StringBuilder stringBuilder = new StringBuilder(text.length());
        stringBuilder.append("<h2>Redis为什么这么快?</h2>");
        stringBuilder.append("<hr/>");
        questionUI.textContent.setText(stringBuilder.toString());
        questionUI.textContent.setCaretPosition(10);

        for (String line : lines) {
            stringBuilder.append(line);
            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(500));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            questionUI.textContent.setText(stringBuilder.toString());
        }
    }

    public static void nextButtonListener(){
        questionUI.nextButton.addActionListener(e -> refreshViewContent());
    }

    public static void prevButtonListener(){
        questionUI.prevButton.addActionListener(e -> refreshViewContent());
    }

}
