package dev.kavrin.note_app_kmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.kavrin.note_app_kmm.android.note_detail.NoteDetailViewModel.Companion.NoteId
import dev.kavrin.note_app_kmm.android.note_detail.NoteDetailsScreen
import dev.kavrin.note_app_kmm.android.note_list.NoteListScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "note_list") {
                    composable(route = "note_list") {
                        NoteListScreen(navController = navController)
                    }
                    composable(
                        route = "note_detail/{$NoteId}",
                        arguments = listOf(
                            navArgument(name = NoteId) {
                                type = NavType.LongType
                                defaultValue = -1L
                            }
                        )
                    ) {
                        val noteId = it.arguments?.getLong(NoteId) ?: -1
                        NoteDetailsScreen(noteId = noteId, navController = navController)
                    }
                }
            }
        }
    }
}
