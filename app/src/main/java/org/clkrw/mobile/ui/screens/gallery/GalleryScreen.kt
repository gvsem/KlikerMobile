package org.clkrw.mobile.ui.screens.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.clkrw.mobile.R
import org.clkrw.mobile.domain.model.Presentation
import org.clkrw.mobile.ui.theme.Typography

@Composable
fun GalleryScreen(
    viewModel: GalleryViewModel,
    onClickEditPresentation: (Presentation) -> Unit,
    onClickStartPresentation: (Presentation) -> Unit,
    modifier: Modifier = Modifier,
) {
    val presentations by viewModel.state.collectAsState()
    PresentationsList(
        presentations = presentations,
        onClickEditPresentation = onClickEditPresentation,
        onClickStartPresentation = onClickStartPresentation,
        modifier = modifier
    )
}

@Composable
fun PresentationsList(
    presentations: List<Presentation>,
    onClickEditPresentation: (Presentation) -> Unit,
    onClickStartPresentation: (Presentation) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(presentations) { presentation ->
            PresentationCard(
                presentation = presentation,
                onClickEditPresentation = { onClickEditPresentation(presentation) },
                onClickStartPresentation = { onClickStartPresentation(presentation) }
            )
        }
    }
}

@Composable
fun PresentationCard(
    presentation: Presentation,
    onClickEditPresentation: () -> Unit,
    onClickStartPresentation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(text = presentation.name, style = Typography.titleLarge)
                Text(text = "Slides: ${presentation.slidesCount}", style = Typography.labelSmall)
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onClickEditPresentation) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(R.string.edit_presentation),
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(onClick = onClickStartPresentation) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = stringResource(R.string.start_presentation),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PresentationCardPreview() {
    PresentationCard(
        Presentation(0, "I want to sleep", 10),
        {},
        {},
    )
}