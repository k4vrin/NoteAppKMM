package dev.kavrin.note_app_kmm.android.note_list

import dev.kavrin.note_app_kmm.domain.note.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)
