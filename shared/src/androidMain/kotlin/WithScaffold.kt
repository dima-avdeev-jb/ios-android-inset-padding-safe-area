import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

enum class Theme {
    SystemTheme,
    DarkTheme,
    LightTheme,
}

enum class Top {
    Empty,
    WhatsApp,
    Telegram,
    CollapsingTopBar,
}

enum class Content {
    Empty,
    Scrollable,
    ScaffoldPadding,
    KeyboardInset,
    SafeAreaInset,
    Chat,
    BigTextField,
}

enum class Bottom {
    Empty,
    Tabs,
}

@Composable
fun WithMaterialThemeAndScaffold() {
    val themeState = remember { mutableStateOf(Theme.SystemTheme) }
    val isDarkTheme = when (themeState.value) {
        Theme.SystemTheme -> isSystemInDarkTheme()
        Theme.DarkTheme -> true
        Theme.LightTheme -> false
    }

    Box(Modifier.fillMaxSize()) {
        MaterialTheme(colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()) {
            WithScaffold()
        }
        Box(Modifier.fillMaxSize()) {
            SwitchStates(Theme.values(), themeState.value, Modifier.align(Alignment.CenterStart)) {
                themeState.value = it
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithScaffold() {
    val topState = remember { mutableStateOf(Top.Telegram) }
    val contentState = remember { mutableStateOf(Content.KeyboardInset) }
    val bottomState = remember { mutableStateOf(Bottom.Empty) }

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)
    androidx.compose.material3.Scaffold(
        topBar = {
            when (topState.value) {
                Top.WhatsApp -> WhatsAppTop()
                Top.Telegram -> TelegramTop()
                Top.CollapsingTopBar -> MediumTopAppBar(
                    title = { Text("Collapsing with Scrollable") },
                    scrollBehavior = scrollBehavior
                )

                else -> {}
            }
        },
        bottomBar = {
            when (bottomState.value) {
                Bottom.Empty -> {}
                Bottom.Tabs -> BottomAppBar {
                    Box(
                        Modifier.height(50.dp)
                            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
                            .fillMaxWidth().background(Color.White.copy(0.4f))
                    )
                }
            }
        },
        modifier = Modifier.run {
            if (topState.value == Top.CollapsingTopBar) {
                nestedScroll(scrollBehavior.nestedScrollConnection)
            } else {
                this
            }
        },
    ) { innerPadding ->
        when (contentState.value) {
            Content.Scrollable -> {
                LazyColumn(contentPadding = innerPadding) {
                    items(21) {
                        Box(
                            Modifier.height(100.dp).fillMaxWidth()
                                .background(Color(Random.nextInt()).copy(0.2f))
                        ) { Text("Lazy item $it") }
                    }
                }
            }

            Content.ScaffoldPadding -> {
                Box(Modifier.fillMaxSize()) {
                    Text(
                        "Scaffold innerPadding from top",
                        Modifier.align(Alignment.TopStart)
                            .padding(innerPadding)
                            .background(Color.Yellow.copy(0.5f))
                    )
                    Text(
                        "Scaffold innerPadding from bottom",
                        Modifier.align(Alignment.BottomStart)
                            .padding(innerPadding)
                            .background(Color.Yellow.copy(0.5f))
                    )
                }
            }

            Content.Chat -> {}
            Content.BigTextField -> {}
            else -> {}
        }

        Box(
            Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.commonSafeArea)
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            SwitchStates(Top.values(), topState.value, Modifier.align(Alignment.TopEnd)) {
                topState.value = it
            }
            SwitchStates(
                Content.values(),
                contentState.value,
                Modifier.align(Alignment.CenterEnd)
            ) {
                contentState.value = it
            }
            SwitchStates(Bottom.values(), bottomState.value, Modifier.align(Alignment.BottomEnd)) {
                bottomState.value = it
            }
        }
    }

    if (contentState.value == Content.KeyboardInset) {
        ContentKeyboardInset()
    }
    if (contentState.value == Content.SafeAreaInset) {
        ContentSafeAreaInset()
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContentKeyboardInset() = Box(Modifier.fillMaxSize()) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(Modifier.align(Alignment.Center)) {
        BasicText(
            "Hide keyboard",
            Modifier.clickable { keyboardController?.hide() }.focusable(true)
                .background(Color.Red.copy(0.5f))
        )
        BasicTextField("Show keyboard", {}, Modifier.background(Color.Green.copy(0.3f)))
    }
    Text(
        "ime (keyboard) inset",
        Modifier.align(Alignment.BottomCenter)
            .windowInsetsPadding(WindowInsets.ime)
            .background(Color.Green.copy(0.5f))
    )
}

@Composable
fun ContentSafeAreaInset() = Box(Modifier.fillMaxSize().background(Color.Red.copy(0.3f))) {
    Box(
        Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.commonSafeArea)
            .background(Color.Green.copy(0.5f))
    )
}

@Composable
fun <T> SwitchStates(states: Array<T>, current: T, modifier: Modifier, onChange: (T) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
    Column(modifier.background(Color.LightGray).width(IntrinsicSize.Min)) {
        states.forEach {
            Text(
                it.toString(),
                Modifier.clickable {
                    expanded.value = false
                    onChange(it)
                }
                    .fillMaxWidth()
                    .padding(2.dp).border(1.dp, Color.Blue).padding(2.dp)
                    .background(if (current == it) Color.Gray else Color.Blue.copy(0.3f))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhatsAppTop() {
    CenterAlignedTopAppBar(
        navigationIcon = {
            Text("Edit", color = Color.Blue)
        },
        title = { Text("Chats", fontWeight = FontWeight.Bold) },
        actions = {
            val modifier = Modifier.padding(horizontal = 10.dp)
            Icon(Icons.Outlined.Phone, null, modifier, Color.Blue)
            Icon(Icons.Outlined.Edit, null, modifier, Color.Blue)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelegramTop() {
    Box(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .background(
                Brush.horizontalGradient(listOf(Color.Green.copy(0.5f), Color.Blue.copy(0.5f)))
            )
    ) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                Row {
                    Icon(Icons.Default.ArrowBack, null, tint = Color.Blue)
                    Text("Back", color = Color.Blue)
                }
            },
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Some Name", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("last seen 3 hours ago", fontSize = 10.sp)
                }
            },
            actions = {
                val modifier = Modifier.padding(horizontal = 10.dp)
                Icon(Icons.Outlined.Phone, null, modifier, Color.Blue)
                Icon(Icons.Outlined.Edit, null, modifier, Color.Blue)
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent
            )
        )
    }
}
