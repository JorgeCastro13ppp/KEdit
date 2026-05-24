package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MobileEditorArea(
    content: String,
    onContentChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    targetLine: Int? = null,
    targetSearchIndex: Int = 0
) {

    val lineCount =
        content.lines().size.coerceAtLeast(1)

    val editorBackground =
        MaterialTheme.colorScheme.background

    val editorTextColor =
        MaterialTheme.colorScheme.onBackground

    val verticalScrollState =
        rememberScrollState()

    val horizontalScrollState =
        rememberScrollState()

    val highlightedText =
        highlightKotlinSyntax(content)

    LaunchedEffect(
        targetLine,
        targetSearchIndex
    ) {

        if (targetLine != null) {

            val approximateLineHeightPx =
                22

            val visibleOffsetLines =
                4

            val targetScroll =
                ((targetLine - visibleOffsetLines) * approximateLineHeightPx)
                    .coerceAtLeast(0)

            verticalScrollState.animateScrollTo(
                targetScroll
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(editorBackground)
            .padding(
                horizontal = 10.dp,
                vertical = 8.dp
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(horizontalScrollState)
                .verticalScroll(verticalScrollState)
        ) {

            Column(
                modifier = Modifier
                    .padding(top = 2.dp),

                horizontalAlignment =
                    Alignment.End
            ) {

                for (i in 1..lineCount) {

                    Text(
                        text = i.toString(),
                        fontSize = 12.sp,
                        color =
                            MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.35f
                            ),
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            BasicTextField(
                value = content,

                onValueChange = {
                    onContentChange(it)
                },

                modifier = Modifier
                    .widthIn(min = 320.dp)
                    .fillMaxHeight()
                    .padding(top = 2.dp),

                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    color = editorTextColor.copy(alpha = 0f),
                    lineHeight = 22.sp,
                    fontSize = 14.sp
                ),

                cursorBrush =
                    SolidColor(editorTextColor),

                decorationBox = { innerTextField ->

                    Box {

                        Text(
                            text = highlightedText,
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                                color = editorTextColor
                            )
                        )

                        innerTextField()
                    }
                }
            )
        }
    }
}