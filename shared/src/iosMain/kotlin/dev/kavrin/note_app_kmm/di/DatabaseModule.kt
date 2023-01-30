package dev.kavrin.note_app_kmm.di

import dev.kavrin.note_app_kmm.data.local.DatabaseDriverFactory
import dev.kavrin.note_app_kmm.data.note.SqlDelightNoteDataSource
import dev.kavrin.note_app_kmm.database.NoteDatabase
import dev.kavrin.note_app_kmm.domain.note.NoteDataSource

class DatabaseModule {
    private val factory by lazy { DatabaseDriverFactory() }
    val noteDataSource: NoteDataSource by lazy {
        SqlDelightNoteDataSource(db = NoteDatabase(factory.createDriver()))
    }
}