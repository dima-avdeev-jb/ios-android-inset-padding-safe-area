import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

val themeState = mutableStateOf(Theme.SystemTheme)
val topState = mutableStateOf(Top.TopBarWithGradient)
val bottomState = mutableStateOf(Bottom.Empty)

enum class Theme { SystemTheme, DarkTheme, LightTheme, }
enum class Top { Empty, TopBarBasic, TopBarWithGradient, CollapsingTopBar, }
enum class Bottom { Empty, Tabs, }

@Composable
fun WithMaterialThemeAndScaffold() {
    val isDarkTheme = when (themeState.value) {
        Theme.SystemTheme -> isSystemInDarkTheme()
        Theme.DarkTheme -> true
        Theme.LightTheme -> false
    }
    Box(Modifier.fillMaxSize()) {
        MaterialTheme(colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()) {
            WithScaffold()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithScaffold() {
    val isScrollableState = remember { mutableStateOf(false) }
    val isScaffoldPaddingState = remember { mutableStateOf(false) }
    val isKeyboardInsetState = remember { mutableStateOf(false) }
    val isSafeAreaInsetState = remember { mutableStateOf(false) }
    val isChatState = remember { mutableStateOf(true) }
    val isBigTextFieldState = remember { mutableStateOf(false) }

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)
    androidx.compose.material3.Scaffold(
        topBar = {
            when (topState.value) {
                Top.TopBarBasic -> TopBarBasic()
                Top.TopBarWithGradient -> TopBarWithGradient()
                Top.CollapsingTopBar -> MediumTopAppBar(
                    title = { Text("Collapsing with Scrollable content") },
                    scrollBehavior = scrollBehavior
                )

                else -> {}
            }
        },
        bottomBar = {
            when (bottomState.value) {
                Bottom.Empty -> {}
                Bottom.Tabs -> BottomAppBar() {
                    val tabState = remember { mutableStateOf(0) }
                    TabRow(selectedTabIndex = tabState.value) {
                        listOf(
                            "Home" to Icons.Default.Home,
                            "Star" to Icons.Default.Star,
                            "Settings" to Icons.Default.Settings,
                        ).forEachIndexed { index, pair ->
                            Tab(
                                text = { Text(pair.first) },
                                selected = tabState.value == index,
                                onClick = { tabState.value = index },
                                icon = { Icon(pair.second, null) }
                            )
                        }
                    }
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
        if (isScrollableState.value) {
            LazyColumn(contentPadding = innerPadding) {
                items(21) {
                    Box(
                        Modifier.height(100.dp).fillMaxWidth()
                            .background(Color(Random.nextInt()).copy(0.2f))
                    ) { Text("Lazy item $it") }
                }
            }
        }
        if (isScaffoldPaddingState.value) {
            ContentScaffoldPadding(innerPadding)
        }

        if (isChatState.value) {
            ContentChat(innerPadding)
        }

        if (isBigTextFieldState.value) {
            ContentBigText(innerPadding)
        }

        Box(
            Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.commonSafeArea)
                .padding(innerPadding)
        ) {
            SwitchEnumState(
                Top.values(),
                topState,
                Modifier.align(Alignment.TopEnd)
            )
            Column(
                Modifier.align(Alignment.CenterEnd).background(Color.Blue.copy(0.3f)).padding(2.dp)
                    .border(1.dp, Color.Blue).padding(2.dp),
                horizontalAlignment = Alignment.End
            ) {
                @Composable
                fun SwitchBooleanState(state: MutableState<Boolean>, text: String) =
                    Row(Modifier.height(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text)
                        Switch(state.value, { state.value = it }, Modifier.scale(0.5f))
                    }
                SwitchBooleanState(isScrollableState, "Scrollable")
                SwitchBooleanState(isScaffoldPaddingState, "ScaffoldPadding")
                SwitchBooleanState(isKeyboardInsetState, "KeyboardInset")
                SwitchBooleanState(isSafeAreaInsetState, "SafeAreaInset")
                SwitchBooleanState(isChatState, "Chat")
                SwitchBooleanState(isBigTextFieldState, "BigTextField")
            }
            SwitchEnumState(
                Bottom.values(),
                bottomState,
                Modifier.align(Alignment.BottomEnd)
            )
            SwitchEnumState(Theme.values(), themeState, Modifier.align(Alignment.CenterStart))
        }
    }

    if (isSafeAreaInsetState.value) {
        ContentSafeAreaInset()
    }
    if (isKeyboardInsetState.value) {
        ContentKeyboardInset()
    }

}

@Composable
fun ContentScaffoldPadding(innerPadding: PaddingValues) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentChat(innerPadding: PaddingValues) {
    Box(Modifier.fillMaxSize()) {
        val messagesState = remember {
            val messagesArray = buildList {
                repeat(20) {
                    add("Message $it")
                }
            }.toTypedArray()
            mutableStateListOf(*messagesArray)
        }
        LazyColumn(contentPadding = innerPadding) {
            items(messagesState) {
                Row(
                    Modifier.padding(5.dp).background(Color.Green.copy(0.3f)).padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.size(40.dp).clip(CircleShape).background(Color(Random.nextInt())))
                    Text(it, Modifier.padding(4.dp))
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentBigText(innerPadding: PaddingValues) {
    val textState = remember {
        mutableStateOf(
            buildString {
                appendLine("Begin")
                repeat(40) {
                    appendLine("Some big text $it")
                }
                appendLine("End")
            }
        )
    }
    TextField(
        textState.value,
        { textState.value = it },
        Modifier.fillMaxSize().padding(innerPadding)
    )
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
        "WindowInsets.ime",
        Modifier.align(Alignment.BottomStart)
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.ime)
            .background(Color.Green.copy(0.5f))
            .padding(bottom = 5.dp)
    )
}

@Composable
fun ContentSafeAreaInset() = Box(Modifier.fillMaxSize().background(Color.Red.copy(0.2f))) {
    Box(
        Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.commonSafeArea)
            .background(Color.Green.copy(0.4f))
    )
}

@Composable
fun <T> SwitchEnumState(values: Array<T>, state: MutableState<T>, modifier: Modifier) {
    Column(modifier.background(MaterialTheme.colorScheme.surface).width(IntrinsicSize.Min)) {
        values.forEach {
            Text(
                it.toString(),
                Modifier.clickable {
                    state.value = it
                }
                    .fillMaxWidth()
                    .padding(2.dp).border(1.dp, Color.Blue).padding(2.dp)
                    .background(if (state.value == it) Color.Gray else Color.Blue.copy(0.3f))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarBasic() {
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
fun TopBarWithGradient() {
    val bgColor = MaterialTheme.colorScheme.background
    val green = Color.Green.copy(0.5f).compositeOver(bgColor)
    val blue = Color.Blue.copy(0.5f).compositeOver(bgColor)
    Box(
        Modifier.background(Brush.horizontalGradient(listOf(green, blue)))
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
