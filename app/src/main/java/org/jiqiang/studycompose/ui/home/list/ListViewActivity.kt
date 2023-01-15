package org.jiqiang.studycompose.ui.home.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guru.composecookbook.theme.ComposeCookBookMaterial3Theme
import com.guru.composecookbook.theme.ComposeCookBookTheme
import org.jiqiang.data.DemoDataProvider
import java.sql.Driver

class ListViewActivity : ComponentActivity() {

    private val listType: String by lazy {
        intent?.getStringExtra(TYPE) ?: ListViewType.VERTICAL.name
    }

    private val isDarkTheme: Boolean by lazy {
        intent?.getBooleanExtra(DARK_THEME, false) ?: false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseView(isDarkTheme = isDarkTheme) {
                ListViewContent(listType = listType) {
                    onBackPressed()
                }
            }
        }
    }

    companion object {
        const val TYPE = "type"
        const val DARK_THEME = "darkTheme"
        fun newIntent(context: Context, listViewType: String, isDarkTheme: Boolean) =
            Intent(context, ListViewActivity::class.java).apply {
                putExtra(TYPE, listViewType)
                putExtra(DARK_THEME, isDarkTheme)
            }
    }

}


@Composable
fun BaseView(isDarkTheme: Boolean, content: @Composable () -> Unit) {
    ComposeCookBookMaterial3Theme(isDarkTheme) {
        content()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListViewContent(listType: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Column(modifier = Modifier.padding(4.dp)) {
                        Text(text = "ListView")
                        Text(
                            text = listType.lowercase(),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = {
            ListViewScreen(
                listType = listType,
                modifier = Modifier.padding(it)
            )
        }
    )
}


@Composable
fun ListViewScreen(listType: String, modifier: Modifier) {
    Box(modifier = modifier) {
        when (listType) {
            ListViewType.VERTICAL.name -> {
                VerticalListView()
            }
            ListViewType.HORIZONTAL.name -> {
                HorizontalListView()
            }
        }
    }
}

@Composable
fun VerticalListView() {

    val list = DemoDataProvider.itemList
    LazyColumn {
        items(
            count = list.size,
            itemContent = { index ->
                val item = list[index]
                if (index % 3 == 0) {
                    VerticalListItemSmall(item = item)
                } else {
                    VerticalListItem(item = item)
                }
            }
        )
    }
}

@Composable
fun HorizontalListView() {
    val list = DemoDataProvider.itemList
    Column {
        Text(
            text = "Good Food",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.labelMedium
        )
        LazyRow(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            items(
                count = list.size,
                itemContent = { index ->
                    val item = list[index]
                    var endPadding = if (index != list.size - 1) 16.dp else 0.dp
                    HorizontalListItem(
                        item = item,
                        modifier = Modifier.padding(end = endPadding)
                    )
                }
            )
        }
        Divider(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListViewActivity() {
    ComposeCookBookTheme {
        ListViewContent(listType = ListViewType.HORIZONTAL.name, onBack = {})
    }
}