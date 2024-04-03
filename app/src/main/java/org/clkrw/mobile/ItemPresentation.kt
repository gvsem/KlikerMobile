package org.clkrw.mobile

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat


@Composable
fun ItemPresentation(previewId: Int, title: String, author: String, date: String, link: String) {
    val context = LocalContext.current

    Column(modifier = Modifier.padding(all = 16.dp)) {
        Text(
            text = title,
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            val previewPainter: Painter? =
                if (isDrawableResourceValid(context, previewId)) {
                    painterResource(id = previewId)
                } else {
                    null
                }

            Box(
                modifier = Modifier
                    .fillMaxWidth(.4f)
                    .aspectRatio(ratio = 16f / 9f),
                contentAlignment = Alignment.Center
            ) {
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

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(id = R.string.author),
                            tint = colorResource(id = R.color.black)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = author,
                            fontSize = 12.sp
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
                            text = date,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.brand),
                            contentColor = colorResource(id = R.color.white)
                        )
                    ) {
                        Text(text = stringResource(id = R.string.item_presentation_open_clicker))
                    }

                    val shareLinkViaStr = stringResource(id = R.string.share_link_via)
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_TEXT, link)
                        context.startActivity(Intent.createChooser(intent, shareLinkViaStr))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = stringResource(id = R.string.share),
                            tint = colorResource(id = R.color.brand)
                        )
                    }
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
