package com.learning.plugin.ui;

import javax.swing.*;

public class ReadUI {

    private JPanel mainPanel;
    private JTextPane textContent;
    private JButton nextButton;
    private JButton prevButton;

    public JComponent getComponent() {
        return mainPanel;
    }

    public JTextPane getTextContent() {
        return textContent;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public void setNextButton(JButton nextButton) {
        this.nextButton = nextButton;
    }

    public JButton getPrevButton() {
        return prevButton;
    }

    public void setPrevButton(JButton prevButton) {
        this.prevButton = prevButton;
    }
}
