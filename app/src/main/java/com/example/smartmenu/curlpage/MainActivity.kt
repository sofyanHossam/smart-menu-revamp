package com.example.smartmenu.curlpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.smartmenu.ui.theme.SmartMenuTheme
import eu.wewox.pagecurl.ExperimentalPageCurlApi
import eu.wewox.pagecurl.components.HowToPage
import eu.wewox.pagecurl.config.PageCurlConfig
import eu.wewox.pagecurl.page.PageCurl

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartMenuTheme {
                SimplePageCurlScreen()
            }
        }
    }
}

@OptIn(ExperimentalPageCurlApi::class)
@Composable
fun SimplePageCurlScreen() {
    Box(Modifier.fillMaxSize()) {
        val pages = remember { HowToPageData.simpleHowToPages }

        val config = PageCurlConfig(
            Color.White,
            0.1f,
            Color.Black,
            0.2f,
            15.dp,
            DpOffset((-5).dp, 0.dp),
            true,
            dragBackwardEnabled = true,
            tapForwardEnabled = false,
            tapBackwardEnabled = false,
            tapCustomEnabled = false,
            dragInteraction = PageCurlConfig.StartEndDragInteraction(),
            tapInteraction = PageCurlConfig.TargetTapInteraction(),
            onCustomTap = { _, _ -> false },
        )

        PageCurl(count = pages.size, config = config) { index ->
            HowToPage(index, pages[index])
        }
    }
}