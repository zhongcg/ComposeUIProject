package org.jiqiang.studycompose.ui.home.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.jiqiang.data.model.Item

@Composable
fun VerticalListItemSmall(item: Item, modifier: Modifier = Modifier) {
    val typography = MaterialTheme.typography
    Row(
        modifier = Modifier
            .clickable(onClick = { })
            .padding(16.dp)
    ) {
        ItemImage(item, modifier = Modifier.padding(end = 16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, style = typography.titleMedium)
            Text(text = item.subTitle, style = typography.bodyMedium)
        }
    }
}

@Composable
fun ItemImage(item: Item, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = item.imageId),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = modifier
            .size(100.dp, 80.dp)
            .clip(MaterialTheme.shapes.medium)
    )
}