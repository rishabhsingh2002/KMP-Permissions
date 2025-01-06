package app.rishabh.permission

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIWindow


data class ComposeWindow(val width: Int = 0, val height: Int = 0)

val LocalComposeWindow: ProvidableCompositionLocal<ComposeWindow> = compositionLocalOf { ComposeWindow() }

@OptIn(ExperimentalForeignApi::class)
@Suppress("unused", "FunctionNaming", "FunctionName")
fun MainViewController(window: UIWindow) = ComposeUIViewController {

    var safePaddingValues by remember { mutableStateOf(PaddingValues()) }

    LaunchedEffect(window.safeAreaInsets) {
        window.safeAreaInsets.useContents {
            safePaddingValues = PaddingValues(
                top = this.top.dp,
                bottom = this.bottom.dp,
                start = this.left.dp,
                end = this.right.dp,
            )
        }
    }

    val composeWindow by remember(window) {
        val windowInfo = window.frame.useContents {
            ComposeWindow(this.size.width.toInt(), this.size.height.toInt())
        }
        mutableStateOf(windowInfo)
    }

    CompositionLocalProvider(
        LocalComposeWindow provides composeWindow
    ) {
        App()
    }

}
