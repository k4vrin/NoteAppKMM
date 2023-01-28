package dev.kavrin.note_app_kmm.android.note_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    isHintVisible: Boolean,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    singleLine: Boolean = false,
    onTextChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
) {

    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged(onFocusChange)
        )
        if (isHintVisible) {
            Text(
                text = hint,
                style = textStyle,
                color = Color.DarkGray
            )
        }
    }

}