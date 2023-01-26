package dev.kavrin.note_app_kmm.data.note

import dev.kavrin.note_app_kmm.database.NoteDatabase
import dev.kavrin.note_app_kmm.domain.note.Note
import dev.kavrin.note_app_kmm.domain.note.NoteDataSource
import dev.kavrin.note_app_kmm.domain.time.DateTimeUtil.toEpochMillis

class SqlDelightNoteDataSource(
    db: NoteDatabase
) : NoteDataSource {

    private val queries = db.noteQueries

    override suspend fun insertNote(note: Note) {
        with(note) {
            queries.insertNote(
                id = id,
                title = title,
                content = content,
                colorHex = colorHex,
                created = created.toEpochMillis()
            )
        }
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries.getNoteById(id = id)
            .executeAsOneOrNull()
            ?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries.getAllNotes()
            .executeAsList()
            .map { noteEntity -> noteEntity.toNote() }
    }

    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id = id)
    }
}