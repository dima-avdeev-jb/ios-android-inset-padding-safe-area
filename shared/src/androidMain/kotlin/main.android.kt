import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable

@Composable
fun MainView() {
    ApplicationLayoutExamples()
//    SampleInsets()
//    SampleImeNestedScroll()
}

val WindowInsets.Companion.commonSafeArea: WindowInsets
    @Composable
    get() = systemBars
