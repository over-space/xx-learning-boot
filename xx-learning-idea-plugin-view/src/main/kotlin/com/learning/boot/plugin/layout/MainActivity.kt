package com.learning.boot.plugin.layout

import androidx.compose.animation.animateContentSize
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import java.util.ArrayList
import javax.swing.JComponent

/**
 * @author over.li
 * @since 2024/2/1
 */
class MainActivity : AnAction() {

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

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Text(text = msg.author)
    Text(text = msg.body)
}

@Composable
fun PreviewMessageCard(msg: Message) {
    var isExpanded by remember { mutableStateOf(false) }

    Surface(
        shape = MaterialTheme.shapes.medium, // 使用 MaterialTheme 自带的形状
        elevation = 5.dp,
        modifier = Modifier.padding(all = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource("images/long.jpeg"),
                contentDescription = "Image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colors.secondary, shape = CircleShape)
            )
            Spacer(Modifier.padding(horizontal = 4.dp))
            Column {
                Text(
                    text = msg.author,
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    msg.body,
                    style = MaterialTheme.typography.body1,
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    modifier = Modifier.animateContentSize()
                )
            }
        }
    }
}

@Composable
fun PreviewMessageCard(msgList: List<Message>) {
    LazyColumn (
    ) {
        items(msgList) { message ->
            PreviewMessageCard(msg = message)
        }
    }
}

@Preview
@Composable
fun PreviewMessageCard() {
    var messages = ArrayList<Message>();
    for (i in 1..10) {
        messages.add(Message("Jetpack Compose 博物馆", "到目前为止，我们只有一个消息的卡片，看上去有点单调，所以让我们来改善它，让它拥有多条信息。我们需要创建一个能够显示多条消息的函数。对于这种情况，我们可以使用 Compose 的 LazyColumn 和 LazyRow."))
    }
    MaterialTheme {
        PreviewMessageCard(messages);
    }
}

