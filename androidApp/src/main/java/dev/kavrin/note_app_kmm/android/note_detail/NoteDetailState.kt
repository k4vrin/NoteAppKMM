package dev.kavrin.note_app_kmm.android.note_detail

data class NoteDetailState(
    val noteTitle: String = "",
    val isNoteTitleHintDisplayed: Boolean = false,
    val noteContent: String = "",
    val isNoteContentTextFocused: Boolean = false,
    val noteColor: Long = 0xFFFFFFFF,
    val hasNoteBeenSaved: Boolean = false,
)
