package dev.kavrin.note_app_kmm.android.note_detail

import android.os.Parcelable
import dev.kavrin.note_app_kmm.domain.note.Note
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteDetailSaveState(
    var noteTitle: String = "",
    var isNoteTitleTextFocused: Boolean = false,
    var noteContent: String = "",
    var isNoteContentTextFocused: Boolean = false,
    var noteColor: Long = Note.generateRandomColor()
) : Parcelable