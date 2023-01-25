package dev.kavrin.note_app_kmm.domain.note

class SqlDelightNoteDataSource(
//    db: NoteDatabase
) : NoteDataSource {

//    private val queries = db

    override suspend fun insertNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteById(id: Long): Note? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllNotes(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNoteById(id: Long) {
        TODO("Not yet implemented")
    }
}