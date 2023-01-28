package dev.kavrin.note_app_kmm.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kavrin.note_app_kmm.domain.note.Note
import dev.kavrin.note_app_kmm.domain.note.NoteDataSource
import dev.kavrin.note_app_kmm.domain.note.SearchNotes
import dev.kavrin.note_app_kmm.domain.time.DateTimeUtil
import dev.kavrin.note_app_kmm.presentation.VioletHex
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchNotes = SearchNotes()

    // Surviving process death
    private val notes = savedStateHandle.getStateFlow(key = Notes, initialValue = emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow(key = SearchText, initialValue = "")
    private val isSearchActive = savedStateHandle.getStateFlow(
        key = IsSearchActive,
        initialValue = false
    )

    val state = combine(notes, searchText, isSearchActive) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = searchNotes.execute(notes, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = NoteListState()
    )

    init {
        viewModelScope.launch {
            repeat(10) { index ->
                noteDataSource.insertNote(
                    Note(
                        id = null,
                        title = "Title ${index + 1}",
                        content = "Lorem ipsum dollar sit amet ${index + 1}",
                        colorHex = VioletHex,
                        created = DateTimeUtil.now()
                    )
                )
            }
        }
    }

    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle[Notes] = noteDataSource.getAllNotes()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle[SearchText] = text
    }

    fun onToggleSearch() {
        savedStateHandle[IsSearchActive] = !isSearchActive.value
        if (!isSearchActive.value) savedStateHandle[SearchText] = ""
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id)
            loadNotes()
        }
    }

    companion object {
        const val Notes = "notes"
        const val SearchText = "search_text"
        const val IsSearchActive = "is_search_active"
    }
}









