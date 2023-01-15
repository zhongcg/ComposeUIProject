package org.jiqiang.studycompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.guru.composecookbook.theme.*
import com.guru.fontawesomecomposelib.FaIcon
import kotlinx.coroutines.launch
import org.jiqiang.studycompose.theme.AppThemeState
import org.jiqiang.studycompose.theme.SystemUiController
import org.jiqiang.studycompose.ui.home.HomeScreen
import org.jiqiang.studycompose.ui.home.PalletMenu
import org.jiqiang.studycompose.utils.RotateIcon
import org.jiqiang.studycompose.utils.TestTags

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val appTheme = remember { mutableStateOf(AppThemeState()) }
            BaseView(
                appThemeState = appTheme.value,
                systemUiController = systemUiController
            ) {
                MainAppContent(appTheme)
            }
        }
    }
}

/**
 * 主题颜色控制
 */
@Composable
fun BaseView(
    appThemeState: AppThemeState,
    systemUiController: SystemUiController?,
    content: @Composable () -> Unit
) {
    ComposeCookBookMaterial3Theme(
        darkTheme = appThemeState.darkTheme,
        colorPallet = appThemeState.pallet
    ) {
        systemUiController?.setStatusBarColor(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            darkIcons = appThemeState.darkTheme
        )
        content()
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainAppContent(appThemeState: MutableState<AppThemeState>) {
    //Default home screen state is always HOME
    val homeScreenState = rememberSaveable { mutableStateOf(BottomNavType.HOME) }
    val bottomNavBarContentDescription = "bottom Nav Bar"
    val chooseColorBottomModalState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = chooseColorBottomModalState,
        sheetContent = {
            //Modal used only when user use talkback for the sake of accessibility
            PalletMenu(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) { newPalletSelected ->
                appThemeState.value = appThemeState.value.copy(pallet = newPalletSelected)
                coroutineScope.launch {
                    chooseColorBottomModalState.hide()
                }
            }
        }
    ) {
        val config = LocalConfiguration.current
        val orientation = config.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Column {
                HomeScreenContent(
                    homeScreen = homeScreenState.value,
                    appThemeState = appThemeState,
                    chooseColorBottomModalState = chooseColorBottomModalState,
                    modifier = Modifier.weight(1f)
                )
                BottomNavigationContent(
                    modifier = Modifier
                        .semantics { contentDescription = bottomNavBarContentDescription }
                        .testTag(TestTags.BOTTOM_NAV_TEST_TAG),
                    homeScreenState = homeScreenState
                )
            }
        } else {
            Row(modifier = Modifier.fillMaxSize()) {
                NavigationRailContent(
                    modifier = Modifier
                        .semantics { contentDescription = bottomNavBarContentDescription }
                        .testTag(TestTags.BOTTOM_NAV_TEST_TAG),
                    homeScreenState = homeScreenState
                )
//                HomeScreenContent(
//                    homeScreen = homeScreenState.value,
//                    appThemeState = appThemeState,
//                    chooseColorBottomModalState = chooseColorBottomModalState,
//                    modifier = Modifier.weight(1f)
//                )
            }
        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenContent(
    homeScreen: BottomNavType,
    appThemeState: MutableState<AppThemeState>,
    chooseColorBottomModalState: ModalBottomSheetState, //use for a11y
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Crossfade(homeScreen) { screen ->
            Surface(color = MaterialTheme.colorScheme.background) {
                when (screen) {
                    BottomNavType.HOME -> HomeScreen(appThemeState, chooseColorBottomModalState)
                    BottomNavType.WIDGETS -> HomeScreen(appThemeState, chooseColorBottomModalState)
                    BottomNavType.ANIMATION -> HomeScreen(
                        appThemeState,
                        chooseColorBottomModalState
                    )
                    BottomNavType.DEMOUI -> HomeScreen(appThemeState, chooseColorBottomModalState)
                    BottomNavType.TEMPLATE -> HomeScreen(appThemeState, chooseColorBottomModalState)

                }
            }
        }
    }
}


@Composable
fun BottomNavigationContent(
    modifier: Modifier = Modifier,
    homeScreenState: MutableState<BottomNavType>
) {
    var animate by remember { mutableStateOf(false) }
    NavigationBar(
        modifier = modifier,
    ) {
        NavigationBarItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Home,
                    tint = androidx.compose.material3.LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                )
            },
            selected = homeScreenState.value == BottomNavType.HOME,
            onClick = {
                homeScreenState.value = BottomNavType.HOME
                animate = false
            },
            label = {
                androidx.compose.material3.Text(
                    text = "Home",
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_HOME_TEST_TAG)
        )
        NavigationBarItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Tools, tint = androidx.compose.material3.LocalContentColor
                        .current.copy(
                            alpha =
                            LocalContentAlpha.current
                        )
                )
            },
            selected = homeScreenState.value == BottomNavType.WIDGETS,
            onClick = {
                homeScreenState.value = BottomNavType.WIDGETS
                animate = false
            },
            label = {
                androidx.compose.material3.Text(
                    text = "widgets",
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_WIDGETS_TEST_TAG)
        )
        NavigationBarItem(
            icon = {
                RotateIcon(
                    state = animate,
                    asset = Icons.Default.PlayArrow,
                    angle = 720f,
                    duration = 2000
                )
            },
            selected = homeScreenState.value == BottomNavType.ANIMATION,
            onClick = {
                homeScreenState.value = BottomNavType.ANIMATION
                animate = true
            },
            label = {
                androidx.compose.material3.Text(
                    text = "animation",
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_ANIM_TEST_TAG)

        )
        NavigationBarItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.LaptopCode,
                    tint = androidx.compose.material3.LocalContentColor.current.copy(
                        alpha =
                        LocalContentAlpha.current
                    )
                )
            },
            selected = homeScreenState.value == BottomNavType.DEMOUI,
            onClick = {
                homeScreenState.value = BottomNavType.DEMOUI
                animate = false
            },
            label = {
                androidx.compose.material3.Text(
                    text = "demo",
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_DEMO_UI_TEST_TAG)
        )
        NavigationBarItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.LayerGroup, tint = androidx.compose.material3.LocalContentColor
                        .current.copy(
                            alpha =
                            LocalContentAlpha.current
                        )
                )
            },
            selected = homeScreenState.value == BottomNavType.TEMPLATE,
            onClick = {
                homeScreenState.value = BottomNavType.TEMPLATE
                animate = false
            },
            label = {
                androidx.compose.material3.Text(
                    text = "profile",
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_TEMPLATE_TEST_TAG)

        )
    }
}


@Composable
private fun NavigationRailContent(
    modifier: Modifier,
    homeScreenState: MutableState<BottomNavType>,
) {
    var animate by remember { mutableStateOf(false) }
    androidx.compose.material3.NavigationRail(
        modifier = modifier,
    ) {
        androidx.compose.material3.NavigationRailItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Home,
                    tint = androidx.compose.material3.LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                )
            },
            selected = homeScreenState.value == BottomNavType.HOME,
            onClick = {
                homeScreenState.value = BottomNavType.HOME
                animate = false
            },
            label = {
                androidx.compose.material3.Text(
                    text = "home",
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_HOME_TEST_TAG)
        )
        androidx.compose.material3.NavigationRailItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.Tools, tint = androidx.compose.material3.LocalContentColor
                        .current.copy(
                            alpha =
                            LocalContentAlpha.current
                        )
                )
            },
            selected = homeScreenState.value == BottomNavType.WIDGETS,
            onClick = {
                homeScreenState.value = BottomNavType.WIDGETS
                animate = false
            },
            label = {
                androidx.compose.material3.Text(
                    text = "widgets",
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_WIDGETS_TEST_TAG)
        )
        androidx.compose.material3.NavigationRailItem(
            icon = {
                RotateIcon(
                    state = animate,
                    asset = Icons.Default.PlayArrow,
                    angle = 720f,
                    duration = 2000
                )
            },
            selected = homeScreenState.value == BottomNavType.ANIMATION,
            onClick = {
                homeScreenState.value = BottomNavType.ANIMATION
                animate = true
            },
            label = {
                androidx.compose.material3.Text(
                    text = "animation",
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_ANIM_TEST_TAG)

        )
        androidx.compose.material3.NavigationRailItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.LaptopCode,
                    tint = androidx.compose.material3.LocalContentColor.current.copy(
                        alpha =
                        LocalContentAlpha.current
                    )
                )
            },
            selected = homeScreenState.value == BottomNavType.DEMOUI,
            onClick = {
                homeScreenState.value = BottomNavType.DEMOUI
                animate = false
            },
            label = {
                androidx.compose.material3.Text(
                    text = "demo",
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_DEMO_UI_TEST_TAG)
        )
        androidx.compose.material3.NavigationRailItem(
            icon = {
                FaIcon(
                    faIcon = FaIcons.LayerGroup, tint = androidx.compose.material3.LocalContentColor
                        .current.copy(
                            alpha =
                            LocalContentAlpha.current
                        )
                )
            },
            selected = homeScreenState.value == BottomNavType.TEMPLATE,
            onClick = {
                homeScreenState.value = BottomNavType.TEMPLATE
                animate = false
            },
            label = {
                androidx.compose.material3.Text(
                    text = "profile",
                    style = TextStyle(fontSize = 12.sp)
                )
            },
            modifier = Modifier.testTag(TestTags.BOTTOM_NAV_TEMPLATE_TEST_TAG)

        )
    }
}

