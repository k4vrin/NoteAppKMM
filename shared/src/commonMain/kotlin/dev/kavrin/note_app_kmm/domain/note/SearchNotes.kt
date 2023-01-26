package dev.kavrin.note_app_kmm.domain.note

import dev.kavrin.note_app_kmm.domain.time.DateTimeUtil.toEpochMillis

class SearchNotes {

    fun execute(notes: List<Note>, query: String): List<Note> {
        if (query.isBlank()) return notes
        return notes.filter { note ->
            note.title.trim().lowercase().contains(query) ||
                    note.content.trim().lowercase().contains(query)
        }.sortedBy { note -> note.created.toEpochMillis() }
    }
}