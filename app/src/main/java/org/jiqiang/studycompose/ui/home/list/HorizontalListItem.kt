package org.jiqiang.studycompose.ui.home.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guru.composecookbook.theme.ComposeCookBookTheme
import org.jiqiang.data.DemoDataProvider
import org.jiqiang.data.model.Item

@Composable
fun HorizontalListItem(
    item: Item,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.size(200.dp, 200.dp)) {
        Column(modifier = Modifier.clickable(onClick = {})) {
            Image(
                painter = painterResource(id = item.imageId), contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = item.title, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                Text(
                    text = item.subTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
                Text(text = item.source, style = MaterialTheme.typography.titleSmall, maxLines = 1)
            }
        }
    }
}


@Preview()
@Composable
fun PreviewHorizontalListItem() {
    ComposeCookBookTheme {
        val item = DemoDataProvider.item
        HorizontalListItem(item = item)
    }
}