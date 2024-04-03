package org.clkrw.mobile.ui.screens.gallery

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
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
    val context = LocalContext.current

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(IntrinsicSize.Min)
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(text = presentation.title, style = Typography.titleLarge)

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .aspectRatio(ratio = 16f / 9f),
                        contentAlignment = Alignment.Center
                    ) {
                        val previewPainter: Painter? =
                            if (isDrawableResourceValid(context, presentation.previewId)) {
                                painterResource(id = presentation.previewId)
                            } else {
                                null
                            }

                        if (previewPainter != null) {
                            Image(
                                painter = previewPainter,
                                contentDescription = stringResource(id = R.string.preview),
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = colorResource(id = R.color.gray))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(horizontalAlignment = Alignment.Start) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = stringResource(id = R.string.author),
                                tint = colorResource(id = R.color.black)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = presentation.author,
                                style = Typography.bodyMedium,
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = stringResource(id = R.string.date),
                                tint = colorResource(id = R.color.black)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = presentation.date,
                                style = Typography.bodyMedium,
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = presentation.link,
                        style = Typography.bodyMedium,
                    )
                    
                    val shareLinkViaStr = stringResource(id = R.string.share_link_via)
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_TEXT, presentation.link)
                        context.startActivity(Intent.createChooser(intent, shareLinkViaStr))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = stringResource(id = R.string.share),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
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
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = stringResource(R.string.start_presentation),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}


private fun isDrawableResourceValid(context: Context, resourceId: Int): Boolean {
    return try {
        ContextCompat.getDrawable(context, resourceId) != null
    } catch (e: Resources.NotFoundException) {
        false
    }
}

@Preview
@Composable
fun PresentationCardPreview() {
    PresentationCard(
        Presentation(0, 0, "I want to sleep", "Me", "11.12.2023", "https://clkr.me", 10),
        {},
        {},
    )
}