package dev.kavrin.note_app_kmm.android.note_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.kavrin.note_app_kmm.android.note_detail.components.TransparentHintTextField

@Composable
fun NoteDetailsScreen(
    noteId: Long,
    navController: NavHostController,
    viewModel: NoteDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val hasNoteBeenSaved by viewModel.hasNoteBeenSaved.collectAsState()

    LaunchedEffect(key1 = hasNoteBeenSaved) {
        if (hasNoteBeenSaved) navController.popBackStack()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::saveNote,
                backgroundColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "add note",
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color(state.noteColor))
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            TransparentHintTextField(
                text = state.noteTitle,
                hint = "Enter a title...",
                isHintVisible = state.isNoteTitleHintDisplayed,
                textStyle = TextStyle(fontSize = 20.sp),
                singleLine = true,
                onTextChange = viewModel::onNoteTitleChanged,
                onFocusChange = {
                    viewModel.onNoteTitleFocusChanged(it.isFocused)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.noteContent,
                hint = "Enter some content...",
                isHintVisible = state.isNoteContentTextFocused,
                textStyle = TextStyle(fontSize = 20.sp),
                singleLine = false,
                onTextChange = viewModel::onNoteContentChanged,
                onFocusChange = {
                    viewModel.onNoteContentFocusChanged(it.isFocused)
                },
                modifier = Modifier
                    .weight(1f)
            )
        }
    }

}