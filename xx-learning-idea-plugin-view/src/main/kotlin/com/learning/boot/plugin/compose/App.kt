
package com.learning.boot.plugin.compose

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun App() {

    Surface {

        Box(contentAlignment = Alignment.Center) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // 声明 count state，接收读写
                var count by remember { mutableStateOf(0) }

                // Text 的内容跟随 count 变化
                Text("Count: $count")

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                        onClick = {
                            // 每点击一次，count 加一
                            count += 1
                        }
                ) {

                    Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.padding(4.dp)
                    )

                    Text("Hello JetBrains!")
                }
            }

        }
    }
}