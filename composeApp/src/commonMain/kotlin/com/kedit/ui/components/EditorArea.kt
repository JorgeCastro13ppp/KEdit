package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.TextRange

@Composable
fun EditorArea(
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
                24

            val visibleOffsetLines =
                5

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
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(horizontalScrollState)
                .verticalScroll(verticalScrollState)
        ) {

            // Line numbers

            Column(
                modifier = Modifier
                    .padding(top = 3.dp),

                horizontalAlignment =
                    Alignment.End
            ) {

                for (i in 1..lineCount) {

                    Text(
                        fontSize = 14.sp,

                        text = i.toString(),

                        color =
                            MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.30f
                            ),

                        fontFamily =
                            FontFamily.Monospace,

                        lineHeight =
                            24.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(24.dp)
            )

            // Editor

            BasicTextField(
                value = content,

                onValueChange = {
                    onContentChange(it)
                },

                modifier = Modifier
                    .widthIn(min = 700.dp)
                    .fillMaxHeight()
                    .padding(top = 3.dp),

                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    color = editorTextColor.copy(alpha = 0f),
                    lineHeight = 24.sp,
                    fontSize = 15.sp
                ),

                cursorBrush =
                    SolidColor(editorTextColor),

                decorationBox = { innerTextField ->

                    Box {

                        Text(
                            text = highlightedText,
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 15.sp,
                                lineHeight = 24.sp,
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