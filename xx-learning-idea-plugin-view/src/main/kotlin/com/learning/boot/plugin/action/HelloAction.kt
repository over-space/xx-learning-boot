package com.learning.boot.plugin.action

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.awt.ComposePanel
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.learning.boot.plugin.compose.App
import com.learning.boot.plugin.layout.PreviewMessageCard
import javax.swing.JComponent

/**
 * @author over.li
 * @since 2024/1/31
 */
class HelloAction  : DumbAwareAction() {

    override fun actionPerformed(p0: AnActionEvent) {
        HelloWorldAction(p0.project).show()
    }

    class HelloWorldAction(project: Project?) : DialogWrapper(project) {

        init {
            title = "Compose Sample"
            init()
        }

        override fun createCenterPanel(): JComponent? {
            return ComposePanel().apply {
                setBounds(0, 0, 800, 600)
                setContent {
                    // 这里嵌入我们之前写好的计数器界面
                    MaterialTheme {
                        PreviewMessageCard()
                    }
                }
            }
        }
    }
}