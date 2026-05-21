package com.kedit.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle

@Composable
fun highlightKotlinSyntax(
    content: String
): AnnotatedString {

    val keywordColor =
        MaterialTheme.colorScheme.primary

    val typeColor =
        Color(0xFF4FC3F7)

    val stringColor =
        Color(0xFFFFB86C)

    val commentColor =
        Color(0xFF6A9955)

    val numberColor =
        Color(0xFFB5CEA8)

    val textColor =
        MaterialTheme.colorScheme.onBackground

    val keywords =
        setOf(
            "package",
            "import",
            "class",
            "object",
            "data",
            "fun",
            "val",
            "var",
            "if",
            "else",
            "when",
            "return",
            "private",
            "public",
            "actual",
            "expect",
            "override",
            "constructor",
            "init",
            "null",
            "true",
            "false"
        )

    val types =
        setOf(
            "String",
            "Int",
            "Long",
            "Float",
            "Double",
            "Boolean",
            "List",
            "MutableList",
            "Map",
            "MutableMap",
            "Set",
            "Unit"
        )

    val builder =
        AnnotatedString.Builder()

    var index = 0

    while (index < content.length) {

        val char =
            content[index]

        if (
            char == '/' &&
            index + 1 < content.length &&
            content[index + 1] == '/'
        ) {

            val end =
                content.indexOf(
                    '\n',
                    startIndex = index
                ).let {
                    if (it == -1)
                        content.length
                    else
                        it
                }

            builder.pushStyle(
                SpanStyle(
                    color = commentColor
                )
            )

            builder.append(
                content.substring(
                    index,
                    end
                )
            )

            builder.pop()

            index = end

        } else if (char == '"') {

            val end =
                findStringEnd(
                    content = content,
                    start = index
                )

            builder.pushStyle(
                SpanStyle(
                    color = stringColor
                )
            )

            builder.append(
                content.substring(
                    index,
                    end
                )
            )

            builder.pop()

            index = end

        } else if (char.isDigit()) {

            val end =
                findNumberEnd(
                    content = content,
                    start = index
                )

            builder.pushStyle(
                SpanStyle(
                    color = numberColor
                )
            )

            builder.append(
                content.substring(
                    index,
                    end
                )
            )

            builder.pop()

            index = end

        } else if (
            char.isLetter() ||
            char == '_'
        ) {

            val end =
                findWordEnd(
                    content = content,
                    start = index
                )

            val word =
                content.substring(
                    index,
                    end
                )

            val color =
                when {
                    keywords.contains(word) ->
                        keywordColor

                    types.contains(word) ->
                        typeColor

                    else ->
                        textColor
                }

            builder.pushStyle(
                SpanStyle(
                    color = color
                )
            )

            builder.append(word)

            builder.pop()

            index = end

        } else {

            builder.pushStyle(
                SpanStyle(
                    color = textColor
                )
            )

            builder.append(char)

            builder.pop()

            index++
        }
    }

    return builder.toAnnotatedString()
}

private fun findWordEnd(
    content: String,
    start: Int
): Int {

    var index = start

    while (
        index < content.length &&
        (
                content[index].isLetterOrDigit() ||
                        content[index] == '_'
                )
    ) {
        index++
    }

    return index
}

private fun findNumberEnd(
    content: String,
    start: Int
): Int {

    var index = start

    while (
        index < content.length &&
        (
                content[index].isDigit() ||
                        content[index] == '.'
                )
    ) {
        index++
    }

    return index
}

private fun findStringEnd(
    content: String,
    start: Int
): Int {

    var index =
        start + 1

    while (index < content.length) {

        if (
            content[index] == '"' &&
            content[index - 1] != '\\'
        ) {
            return index + 1
        }

        index++
    }

    return content.length
}