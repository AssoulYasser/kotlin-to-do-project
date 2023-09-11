package com.example.roomtodolist.domain.set_up_activity

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.roomtodolist.R
import com.example.roomtodolist.ui.components.defaultButtonShape
import com.example.roomtodolist.ui.components.defaultFilledButtonColors
import kotlinx.coroutines.launch

@Composable
fun SetUpActivityScreen(
    setUpViewModel: SetUpViewModel
) {
    if (setUpViewModel.uiState.isFirstAccess)
        OnBoardingScreen(
            windowSizeClass = setUpViewModel.windowSizeClass,
            onBoardingDataList = setUpViewModel.onBoardingDataList,
            setFirstAccess = { setUpViewModel.setFirstAccess() }
        )
    else
        ProfileSetUpScreen()
}

@Composable
private fun ProfileSetUpScreen() {

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnBoardingScreen(
    windowSizeClass: WindowSizeClass,
    onBoardingDataList: List<OnBoardingData>,
    setFirstAccess: () -> Unit
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Log.d("DEBUGGING : ", pagerState.toString())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo_icon),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp),
            tint = Color.White
        )
        HorizontalPager(
            pageCount = onBoardingDataList.size,
            modifier = Modifier,
            state = pagerState,
            pageSize = PageSize.Fill,
            verticalAlignment = Alignment.CenterVertically,
            userScrollEnabled = false,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
            ) {
                Image(
                    painter = painterResource(id = onBoardingDataList[pagerState.currentPage].image),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize(0.7f)
                        .align(Alignment.TopCenter)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = onBoardingDataList[pagerState.currentPage].title,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Text(
                        text = onBoardingDataList[pagerState.currentPage].description,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }

            }
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in onBoardingDataList.indices) {
                Box(
                    modifier = Modifier
                        .background(
                            color =
                            if (i == pagerState.currentPage) Color.Black else Color.White,
                            shape = CircleShape
                        )
                        .size(10.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            if (pagerState.currentPage == onBoardingDataList.size - 1)
                Button(
                    onClick = setFirstAccess,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = defaultButtonShape()
                ) {
                    Text(text = "Get Started")
                }
            else {
                Button(
                    onClick = setFirstAccess,
                    colors = defaultFilledButtonColors(),
                    shape = defaultButtonShape()
                ) {
                    Text(text = "Skip")
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = defaultButtonShape()
                ) {
                    Text(text = "Next")
                }
            }

        }



    }

}
