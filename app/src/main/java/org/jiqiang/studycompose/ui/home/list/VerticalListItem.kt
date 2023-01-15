package org.jiqiang.studycompose.ui.home.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guru.composecookbook.theme.ComposeCookBookTheme
import org.jiqiang.data.DemoDataProvider
import org.jiqiang.data.model.Item

@Composable
fun VerticalListItem(item: Item, modifier: Modifier = Modifier) {
    val typography = MaterialTheme.typography
    Column(
        modifier = Modifier
            .clickable(onClick = { })
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = item.imageId),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            modifier = modifier.fillMaxWidth()
                .clip(shape = androidx.compose.material.MaterialTheme.shapes.medium)
        )
        Spacer(Modifier.height(16.dp))
        Text(text = item.title, style = typography.titleLarge)
        Text(text = item.subTitle, style = typography.bodyMedium)
    }
}

@Preview()
@Composable
fun PreviewVerticalListItem() {
    ComposeCookBookTheme {
        val item = DemoDataProvider.item
        VerticalListItem(item)
    }
}
