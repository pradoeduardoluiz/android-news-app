package com.prado.eduardo.luiz.newsapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.prado.eduardo.luiz.newsapp.R
import com.prado.eduardo.luiz.newsapp.ui.theme.NewsAppTheme
import com.prado.eduardo.luiz.newsapp.ui.theme.SpacingMiddle
import com.prado.eduardo.luiz.newsapp.ui.theme.SpacingNormal

@Composable
fun CardLarge(
    modifier: Modifier = Modifier,
    author: String,
    title: String,
    description: String,
    imageUrl: String?,
    isCaptionVisible: Boolean = true,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding()
            .clickable { onClick() }
            .padding(start = 18.dp, end = 18.dp, bottom = 32.dp)
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Column {
            imageUrl?.let { imageUrl ->
                Box(
                    contentAlignment = Alignment.BottomStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(184.dp)
                ) {
                    if (LocalInspectionMode.current) {
                        Image(
                            painter = painterResource(id = R.drawable.image_placeholder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        AsyncImage(
                            model = imageUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(
                        start = SpacingMiddle,
                        end = SpacingMiddle
                    )
            ) {
                if (isCaptionVisible) {
                    Row(
                        modifier = Modifier.padding(top = SpacingMiddle),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .height(18.dp)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                        Text(
                            text = author,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier =
                                Modifier
                                    .padding(start = SpacingNormal)
                                    .weight(1f)
                        )
                    }
                }
                Column {
                    Row(
                        modifier = Modifier.padding(top = SpacingNormal)
                    ) {
                        Column {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = title,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                    if (description.isEmpty().not()) {
                        Row(
                            modifier = Modifier
                                .padding(top = SpacingMiddle)
                                .height(IntrinsicSize.Min)
                        ) {
                            Text(
                                text = description,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f, true),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(top = SpacingMiddle))
            }
        }
    }
}

@PreviewLightDark
@Composable
fun CardLargeBoxPreview() {
    NewsAppTheme {
        Column {
            CardLarge(
                author = "author",
                title = "title",
                description = "description",
                imageUrl = "https://via.placeholder.com/150",
            )
        }
    }
}
