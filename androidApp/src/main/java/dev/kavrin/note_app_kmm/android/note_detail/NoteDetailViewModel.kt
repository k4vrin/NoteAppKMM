package dev.kavrin.note_app_kmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kavrin.note_app_kmm.domain.note.Note
import dev.kavrin.note_app_kmm.domain.note.NoteDataSource
import dev.kavrin.note_app_kmm.domain.time.DateTimeUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val savedState =
        savedStateHandle.getStateFlow(key = SavedState, initialValue = NoteDetailSaveState())
    val state = savedState.map { savedState ->
        NoteDetailState(
            noteTitle = savedState.noteTitle,
            isNoteTitleHintDisplayed = savedState.noteTitle.isEmpty() && !savedState.isNoteTitleTextFocused,
            noteContent = savedState.noteContent,
            isNoteContentTextFocused = savedState.noteContent.isEmpty() && !savedState.isNoteContentTextFocused,
            noteColor = savedState.noteColor
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = NoteDetailState()
    )
    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()
    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>(NoteId)?.let { existingNoteId ->
            // New note
            if (existingNoteId == -1L) return@let
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let { note ->
                    NoteDetailSaveState(
                        noteTitle = note.title,
                        noteContent = note.content,
                        noteColor = note.colorHex
                    ).also {
                        savedStateHandle[SavedState] = it
                    }
                }
            }
        }
    }

    fun onNoteTitleChanged(text: String) {
        savedStateHandle[SavedState] =
            savedStateHandle.get<NoteDetailSaveState>(SavedState)?.copy(noteTitle = text)
    }

    fun onNoteContentChanged(text: String) {
        savedStateHandle[SavedState] =
            savedStateHandle.get<NoteDetailSaveState>(SavedState)?.copy(noteContent = text)
    }

    fun onNoteTitleFocusChanged(isFocused: Boolean) {
        savedStateHandle[SavedState] =
            savedStateHandle.get<NoteDetailSaveState>(SavedState)
                ?.copy(isNoteTitleTextFocused = isFocused)
    }

    fun onNoteContentFocusChanged(isFocused: Boolean) {
        savedStateHandle[SavedState] =
            savedStateHandle.get<NoteDetailSaveState>(SavedState)
                ?.copy(isNoteContentTextFocused = isFocused)
    }

    fun saveNote() {
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = savedState.value.noteTitle,
                    content = savedState.value.noteContent,
                    colorHex = savedState.value.noteColor,
                    created = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }

    companion object {
        const val SavedState = "saved_state"
        const val NoteId = "note_id"
    }
}