package com.example.roomtodolist.domain.set_up_activity

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.TextField
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.roomtodolist.R
import com.example.roomtodolist.ui.components.defaultButtonShape
import com.example.roomtodolist.ui.components.defaultFilledButtonColors
import com.example.roomtodolist.ui.components.defaultTextFieldColors
import com.example.roomtodolist.ui.components.defaultTextFieldShape
import kotlinx.coroutines.launch

@Composable
fun SetUpActivityScreen(
    setUpViewModel: SetUpViewModel
) {
    if (setUpViewModel.uiState.isFirstAccess) {

        val isDarkMode = isSystemInDarkTheme()
        setUpViewModel.setMode(isDarkMode)

        OnBoardingScreen(
            windowSizeClass = setUpViewModel.windowSizeClass,
            onBoardingDataList = setUpViewModel.onBoardingDataList,
            setFirstAccess = { setUpViewModel.setFirstAccess() }
        )
    }
    else {
        SetProfileScreen(
            setProfilePicture = { setUpViewModel.setProfilePicture(it) },
            setUsername = { setUpViewModel.setUserName(it) }
        ) { setUpViewModel.setUpProfile() }
    }
}

@Composable
private fun BoxScope.UsernameScreen(username: MutableState<String?>) {
    TextField(
        value = if (username.value == null) "" else username.value!!,
        onValueChange = { username.value = it },
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth(),
        placeholder = {
            Text(text = "Username")
        },
        singleLine = true,
        shape = defaultTextFieldShape(),
        colors = defaultTextFieldColors()
    )
    Spacer(modifier = Modifier.height(20.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create your username",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "It is cool to know how we call you",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

}

@Composable
private fun BoxScope.ProfilePictureScreen(
    profilePicture: MutableState<String?>
) {
    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful)
            profilePicture.value = result.uriContent.toString()

        else
            profilePicture.value = null
    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            if (it == null)
                return@rememberLauncherForActivityResult
            val cropImageOptions = CropImageOptions()
            cropImageOptions.cropShape = CropImageView.CropShape.OVAL
            cropImageOptions.fixAspectRatio = true
            val cropOptions = CropImageContractOptions(it, cropImageOptions)
            imageCropLauncher.launch(cropOptions)
        }
    )

    Button(
        onClick = {
            photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        border = BorderStroke(5.dp, MaterialTheme.colorScheme.onBackground),
        modifier = Modifier
            .fillMaxSize(0.7f)
            .aspectRatio(1f)
            .align(Alignment.TopCenter),
        contentPadding = PaddingValues(8.dp)
    ) {
        if (profilePicture.value == null)
            Text(
                text = "+",
                textAlign = TextAlign.Center,
                fontSize = 32.sp
            )
        else
            AsyncImage(
                model = profilePicture.value,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .aspectRatio(1f)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
    }
    Spacer(modifier = Modifier.height(20.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choose your picture",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "You can change your picture in Settings anytime",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }


}

@Composable
private fun BoxScope.ExplainScreen() {
    Image(
        painter = painterResource(id = R.drawable.personal_settings_asset),
        contentDescription = null,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(0.7f)
            .align(Alignment.TopCenter)
    )
    Spacer(modifier = Modifier.height(20.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create your profile",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Let's create your profile with a few easy steps",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SetProfileScreen(
    setProfilePicture: (String) -> Unit,
    setUsername: (String) -> Unit,
    finish: () -> Unit
) {
    val profilePicture = remember {
        mutableStateOf<String?>(null)
    }
    val username = remember {
        mutableStateOf<String?>(null)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()
        val pageCount = 3
        Icon(
            painter = painterResource(id = R.drawable.logo_icon),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
        HorizontalPager(
            pageCount = pageCount,
            state = pagerState,
            userScrollEnabled = false,
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                when (pagerState.currentPage) {
                    0 -> ExplainScreen()
                    1 -> ProfilePictureScreen(profilePicture)
                    2 -> UsernameScreen(username)
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until pageCount) {
                Box(
                    modifier = Modifier
                        .background(
                            color =
                            if (i == pagerState.currentPage) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.2f
                            ),
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

            if (pagerState.currentPage == pageCount - 1)
                Button(
                    onClick = {
                        setProfilePicture(profilePicture.value!!)
                        setUsername(username.value!!)
                        finish()
                    },
                    colors = defaultFilledButtonColors(),
                    shape = defaultButtonShape(),
                    enabled = username.value != null
                ) {
                    Text(text = "Ready To Add Task")
                }
            else {
                Button(
                    onClick = {
                        finish()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
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
                    colors = defaultFilledButtonColors(),
                    shape = defaultButtonShape(),
                    enabled = pagerState.currentPage != 1 || profilePicture.value != null
                ) {
                    Text(text = "Next")
                }
            }

        }


    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnBoardingScreen(
    windowSizeClass: WindowSizeClass,
    onBoardingDataList: List<OnBoardingData>,
    setFirstAccess: () -> Unit
) {
    val isNotCompact = windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
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
                if (isNotCompact) {
                    Image(
                        painter = painterResource(id = onBoardingDataList[pagerState.currentPage].image),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxSize(0.7f)
                            .align(Alignment.TopCenter)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(if (isNotCompact) Alignment.BottomCenter else Alignment.Center),
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
