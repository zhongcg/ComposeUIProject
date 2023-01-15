package org.jiqiang.studycompose.ui.home

import android.content.Context
import android.os.Build
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guru.composecookbook.theme.*
import org.jiqiang.data.DemoDataProvider
import org.jiqiang.data.model.HomeScreenItems
import org.jiqiang.studycompose.R
import org.jiqiang.studycompose.theme.AppThemeState
import org.jiqiang.studycompose.ui.home.list.ListViewActivity
import org.jiqiang.studycompose.ui.home.list.ListViewType
import org.jiqiang.studycompose.utils.TestTags
import java.util.*

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    appThemeState: MutableState<AppThemeState>,
    chooseColorBottomModalState: ModalBottomSheetState
) {
    val showMenu = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(text = "Compose UI") },
                actions = {
                    IconButton(onClick = {
                        appThemeState.value =
                            appThemeState.value.copy(darkTheme = !appThemeState.value.darkTheme)
                    }) {
                        Icon(
                            painter = painterResource(id = if (appThemeState.value.darkTheme) R.drawable.ic_theme_dark else R.drawable.ic_theme_light),
                            contentDescription = "dark theme"
                        )
                    }
                }
            )
        },

        content = { paddingValues ->

            Column(modifier = Modifier.padding(paddingValues)) {

                HomeScreenContent(
                    isDarkTheme = appThemeState.value.darkTheme,
                    showMenu = showMenu,
                    modifier = Modifier.padding(paddingValues),
                    onPalletChange = { newPalletSelected ->
                        appThemeState.value = appThemeState.value.copy(pallet = newPalletSelected)
                        showMenu.value = false
                    }
                )
            }

        })
}

@Composable
fun HomeScreenContent(
    isDarkTheme: Boolean,
    showMenu: MutableState<Boolean>,
    onPalletChange: (ColorPallet) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val list = remember { DemoDataProvider.homeScreenListItems }
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val isWiderScreen = screenWidth > 550 // Random number for now
    Box(modifier = modifier.fillMaxSize()) {
        if (isWiderScreen) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(count = list.size) { index ->
//                    HomeScreenListView(list[index], context, isDarkTheme, isWiderScreen)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .testTag(TestTags.HOME_SCREEN_LIST)
                    .fillMaxWidth(),
            ) {
                items(
                    count = list.size,
                    itemContent = { index ->
                        HomeScreenListView(list[index], context, isDarkTheme, isWiderScreen)
                    }
                )
            }
        }
        if (showMenu.value) {
            PalletMenu(
                modifier = Modifier.align(Alignment.TopEnd),
                onPalletChange
            )
        }

    }
}

@Composable
fun HomeScreenListView(
    homeScreenItems: HomeScreenItems,
    context: Context,
    isDarkTheme: Boolean,
    isWiderScreen: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Button(
            onClick = { homeItemClicked(homeScreenItems, context, isDarkTheme) },
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(text = homeScreenItems.name)
        }


    }
}

fun homeItemClicked(homeScreenItems: HomeScreenItems, context: Context, isDarkTheme: Boolean) {
    val intent = when (homeScreenItems) {
        is HomeScreenItems.ListView -> {
            ListViewActivity.newIntent(
                context,
                homeScreenItems.type.uppercase(Locale.getDefault()),
                isDarkTheme = isDarkTheme
            )
        }
        else -> {
            ListViewActivity.newIntent(
                context,
                ListViewType.VERTICAL.name,
                isDarkTheme = isDarkTheme
            )
        }

    }
    context.startActivity(intent)
}

@Composable
fun PalletMenu(
    modifier: Modifier,
    onPalletChange: (ColorPallet) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .animateContentSize(),
        ) {
            MenuItem(green500, "Green") {
                onPalletChange.invoke(ColorPallet.GREEN)
            }
            MenuItem(purple, "Purple") {
                onPalletChange.invoke(ColorPallet.PURPLE)
            }
            MenuItem(orange500, "Orange") {
                onPalletChange.invoke(ColorPallet.ORANGE)
            }
            MenuItem(blue500, "Blue") {
                onPalletChange.invoke(ColorPallet.BLUE)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MenuItem(dynamicLightColorScheme(LocalContext.current).primary, "Dynamic") {
                    onPalletChange.invoke(ColorPallet.WALLPAPER)
                }
            }
        }
    }
}


@Composable
fun MenuItem(color: Color, name: String, onPalletChange: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onPalletChange),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.FiberManualRecord,
            tint = color,
            contentDescription = null
        )
        androidx.compose.material3.Text(text = name, modifier = Modifier.padding(8.dp))
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewHomeScreen() {
    val state = remember {
        mutableStateOf(AppThemeState(false, ColorPallet.BLUE))
    }
    val chooseColorBottomModalState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    HomeScreen(appThemeState = state, chooseColorBottomModalState = chooseColorBottomModalState)
}

