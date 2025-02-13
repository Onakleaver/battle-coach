package com.battlecoach.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.Dialog

@Composable
fun ImportGameDialog(
    onDismiss: () -> Unit,
    onImport: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var isUrl by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium
        ) {
            Column {
                Text("Import Game")
                
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text(if (isUrl) "URL" else "PGN") }
                )

                Switch(
                    checked = isUrl,
                    onCheckedChange = { isUrl = it }
                )

                Button(
                    onClick = { onImport(text) }
                ) {
                    Text("Import")
                }
            }
        }
    }
} 