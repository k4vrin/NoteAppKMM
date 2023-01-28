package dev.kavrin.note_app_kmm.android.di

import android.app.Application
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.kavrin.note_app_kmm.data.local.DatabaseDriverFactory
import dev.kavrin.note_app_kmm.data.note.SqlDelightNoteDataSource
import dev.kavrin.note_app_kmm.database.NoteDatabase
import dev.kavrin.note_app_kmm.domain.note.NoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(
        app: Application
    ): SqlDriver = DatabaseDriverFactory(context = app).createDriver()

    @Provides
    @Singleton
    fun provideDatabase(
        driver: SqlDriver
    ): NoteDatabase = NoteDatabase(driver)

    @Provides
    @Singleton
    fun provideNoteDataSource(
        db: NoteDatabase
    ): NoteDataSource = SqlDelightNoteDataSource(db)

}