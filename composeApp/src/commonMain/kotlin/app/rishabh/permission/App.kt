package app.rishabh.permission

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin


@Composable
fun App() {

    val koin = getKoin()
    var isContactPermissionGranted by remember {
        mutableStateOf(
            koin.get<PermissionBridge>().isContactPermissionGranted()
        )
    }

    fun requestPermission() {
        koin.get<PermissionBridge>()
            .requestContactPermission(object : PermissionResultCallback {
                override fun onPermissionGranted() {
                    isContactPermissionGranted =
                        koin.get<PermissionBridge>().isContactPermissionGranted()
                }

                override fun onPermissionDenied(
                    isPermanentDenied: Boolean
                ) {
                    isContactPermissionGranted =
                        koin.get<PermissionBridge>().isContactPermissionGranted()
                }
            })
    }

    MaterialTheme {
        Scaffold(
            Modifier.background(Color.Black)
                .windowInsetsPadding(WindowInsets.statusBars)
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {

            Column(
                modifier = Modifier.fillMaxSize().background(Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                val title =
                    if (isContactPermissionGranted) "Permission is granted" else "Permission not granted"

                Text(
                    title,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (!isContactPermissionGranted) {
                    Box(
                        modifier = Modifier.width(200.dp).height(55.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color.White).clickable {
                                requestPermission()
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            "Request Permission",
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )

                    }
                }

            }
        }
    }

}


