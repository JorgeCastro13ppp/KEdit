package com.kedit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kedit.model.FileItem

import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import org.jetbrains.compose.resources.painterResource
import kedit.composeapp.generated.resources.Res
import kedit.composeapp.generated.resources.ic_file
import kedit.composeapp.generated.resources.ic_folder
import kedit.composeapp.generated.resources.ic_folder_up

@Composable
fun FileExplorer(
    modifier: Modifier = Modifier,
    currentDirectory: String?,
    files: List<FileItem>,
    onOpenItem: (FileItem) -> Unit,
    onGoBack: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .padding(8.dp)
    ) {

        Text(
            text = "Explorador",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )

        if (currentDirectory != null) {

            Text(
                text = currentDirectory,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.65f
                    ),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .horizontalScroll(rememberScrollState())
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = 6.dp
                    )
                    .clickable {
                        onGoBack()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(Res.drawable.ic_folder_up),
                    contentDescription = "Subir carpeta",
                    modifier = Modifier.size(16.dp)
                )

                Spacer(
                    modifier = Modifier.width(6.dp)
                )

                Text(
                    text = "Subir carpeta",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.primary
                )
            }

        } else {

            Text(
                text = "No hay carpeta abierta",
                fontSize = 12.sp,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.7f
                    ),
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        LazyColumn {

            items(files) { file ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onOpenItem(file)
                        }
                        .padding(
                            horizontal = 8.dp,
                            vertical = 6.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Image(
                        painter =
                            painterResource(
                                if (file.isDirectory)
                                    Res.drawable.ic_folder
                                else
                                    Res.drawable.ic_file
                            ),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = file.name,
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Monospace,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}