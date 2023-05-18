import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MainView() {
    WithMaterialThemeAndScaffold()
//    SampleInsets()
//    SampleImeNestedScroll()
}

val WindowInsets.Companion.commonSafeArea: WindowInsets
    @Composable
    get() = systemBars
