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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun CardSmall(
    modifier: Modifier = Modifier,
    author: String,
    title: String,
    description: String,
    imageUrl: String?,
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
        Column(
            modifier = Modifier
                .padding(
                    start = SpacingMiddle,
                    end = SpacingMiddle
                )
        ) {

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
            Column {
                Row(
                    modifier = Modifier.padding(top = SpacingNormal)
                ) {
                    imageUrl?.let { imageUrl ->
                        Box(
                            contentAlignment = Alignment.BottomStart,
                            modifier = Modifier.padding(end = SpacingNormal)
                        ) {
                            if (LocalInspectionMode.current) {
                                Image(
                                    painter = painterResource(id = R.drawable.image_placeholder),
                                    modifier = Modifier.size(80.dp),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                AsyncImage(
                                    model = imageUrl,
                                    modifier = Modifier
                                        .width(76.dp)
                                        .height(76.dp),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
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

@PreviewLightDark
@Composable
fun CardSmallBoxPreview() {
    NewsAppTheme {
        Column {
            CardSmall(
                author = "author",
                title = "title",
                description = "description",
                imageUrl = "https://via.placeholder.com/150",
            )
        }
    }
}
