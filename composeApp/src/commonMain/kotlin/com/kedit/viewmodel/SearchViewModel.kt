package com.kedit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kedit.model.SearchMatch
import com.kedit.model.SearchState

class SearchViewModel {

    var state by mutableStateOf(SearchState())
        private set

    fun show() {

        state = state.copy(
            isVisible = true
        )
    }

    fun hide() {

        state = state.copy(
            isVisible = false,
            query = "",
            matches = emptyList(),
            currentIndex = 0
        )
    }

    fun toggle() {

        state =
            if (state.isVisible) {

                state.copy(
                    isVisible = false
                )

            } else {

                state.copy(
                    isVisible = true
                )
            }
    }

    fun updateQuery(
        query: String,
        content: String
    ) {

        val matches =
            findMatches(
                content = content,
                query = query,
                caseSensitive = state.caseSensitive
            )

        state = state.copy(
            query = query,
            matches = matches,
            currentIndex = 0
        )
    }

    fun searchText(
        query: String,
        content: String
    ) {

        val matches =
            findMatches(
                content = content,
                query = query,
                caseSensitive = state.caseSensitive
            )

        state = state.copy(
            isVisible = true,
            query = query,
            matches = matches,
            currentIndex = 0
        )
    }

    fun nextMatch() {

        if (state.matches.isEmpty())
            return

        val nextIndex =
            (state.currentIndex + 1) %
                    state.matches.size

        state = state.copy(
            currentIndex = nextIndex
        )
    }

    fun previousMatch() {

        if (state.matches.isEmpty())
            return

        val previousIndex =
            if (state.currentIndex - 1 < 0)
                state.matches.lastIndex
            else
                state.currentIndex - 1

        state = state.copy(
            currentIndex = previousIndex
        )
    }

    fun toggleCaseSensitive(
        content: String
    ) {

        val newCaseSensitive =
            !state.caseSensitive

        val matches =
            findMatches(
                content = content,
                query = state.query,
                caseSensitive = newCaseSensitive
            )

        state = state.copy(
            caseSensitive = newCaseSensitive,
            matches = matches,
            currentIndex = 0
        )
    }

    fun currentMatch(): SearchMatch? {

        if (state.matches.isEmpty())
            return null

        return state.matches[state.currentIndex]
    }

    private fun findMatches(
        content: String,
        query: String,
        caseSensitive: Boolean
    ): List<SearchMatch> {

        if (query.isBlank())
            return emptyList()

        val searchContent =
            if (caseSensitive)
                content
            else
                content.lowercase()

        val searchQuery =
            if (caseSensitive)
                query
            else
                query.lowercase()

        val result =
            mutableListOf<SearchMatch>()

        var index =
            searchContent.indexOf(searchQuery)

        while (index >= 0) {

            val match =
                buildMatch(
                    content = content,
                    index = index
                )

            result.add(match)

            index =
                searchContent.indexOf(
                    string = searchQuery,
                    startIndex = index + searchQuery.length
                )
        }

        return result
    }

    private fun buildMatch(
        content: String,
        index: Int
    ): SearchMatch {

        val beforeMatch =
            content.substring(
                startIndex = 0,
                endIndex = index
            )

        val line =
            beforeMatch.count {
                it == '\n'
            } + 1

        val lastLineBreak =
            beforeMatch.lastIndexOf('\n')

        val column =
            if (lastLineBreak == -1)
                index + 1
            else
                index - lastLineBreak

        val lineText =
            content
                .lines()
                .getOrNull(line - 1)
                ?: ""

        return SearchMatch(
            index = index,
            line = line,
            column = column,
            lineText = lineText
        )
    }

    fun updateReplacement(
        replacement: String
    ) {

        state = state.copy(
            replacement = replacement
        )
    }

    fun replaceCurrent(
        content: String
    ): String {

        val match =
            currentMatch() ?: return content

        if (state.query.isBlank())
            return content

        val newContent =
            content.replaceRange(
                startIndex = match.index,
                endIndex = match.index + state.query.length,
                replacement = state.replaceText
            )

        return newContent
    }

    fun replaceAll(
        content: String
    ): String {

        if (state.query.isBlank())
            return content

        return if (state.caseSensitive) {

            content.replace(
                oldValue = state.query,
                newValue = state.replaceText
            )

        } else {

            content.replace(
                Regex(
                    pattern = Regex.escape(state.query),
                    option = RegexOption.IGNORE_CASE
                ),
                state.replaceText
            )
        }
    }

    fun updateReplaceText(
        replaceText: String
    ) {

        state = state.copy(
            replaceText = replaceText
        )
    }
}