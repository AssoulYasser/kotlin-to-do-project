package com.example.roomtodolist.domain.set_up_activity

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
import androidx.compose.foundation.pager.PagerState
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
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.roomtodolist.ui.components.rememberImeState
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
            setUsername = { setUpViewModel.setUserName(it) },
            windowSizeClass = setUpViewModel.windowSizeClass,
        ) { setUpViewModel.setUpProfile() }
    }
}

@Composable
private fun Logo() {
    Icon(
        painter = painterResource(id = R.drawable.logo_icon),
        contentDescription = null,
        modifier = Modifier
            .size(100.dp),
        tint = Color.White
    )
}

@Composable
private fun BoxScope.UsernameScreen(username: MutableState<String?>, isLandscape: Boolean = false) {
    TextField(
        value = if (username.value == null) "" else username.value!!,
        onValueChange = { username.value = it },
        modifier = Modifier
            .align(if (isLandscape) Alignment.Center else Alignment.TopCenter)
            .fillMaxWidth(),
        placeholder = {
            Text(text = "Username", style = MaterialTheme.typography.bodyMedium)
        },
        singleLine = true,
        shape = defaultTextFieldShape(),
        colors = defaultTextFieldColors()
    )
}

@Composable
private fun BoxScope.ProfilePictureScreen(
    profilePicture: MutableState<String?>,
    isLandscape: Boolean = false
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
        modifier =
        if (isLandscape)
            Modifier
                .aspectRatio(1f)
        else
            Modifier
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
}

@Composable
private fun BoxScope.ExplainScreen(isLandscape: Boolean = false) {
    Image(
        painter = painterResource(id = R.drawable.personal_settings_asset),
        contentDescription = null,
        modifier =
        if (isLandscape)
            Modifier.fillMaxSize()
        else
            Modifier
                .padding(20.dp)
                .fillMaxSize(0.7f)
                .align(Alignment.TopCenter)
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SetProfileScreen(
    setProfilePicture: (String) -> Unit,
    setUsername: (String) -> Unit,
    windowSizeClass: WindowSizeClass,
    finish: () -> Unit
) {
    val profilePicture = remember {
        mutableStateOf<String?>(null)
    }
    val username = remember {
        mutableStateOf<String?>(null)
    }
    val pagerState = rememberPagerState()
    val pageCount = 3
    val configuration = LocalConfiguration.current
    val isNotCompact = windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact
    val isLandScape = configuration.screenWidthDp > configuration.screenHeightDp

    if (isLandScape)
        LandscapeSetProfilePager(
            pageCount = pageCount,
            pagerState = pagerState,
            profilePicture = profilePicture,
            username = username,
            setProfilePicture = setProfilePicture,
            setUsername = setUsername,
            isNotCompact = isNotCompact,
            finish = finish
        )
    else
        PortraitSetProfilePager(
            pageCount = pageCount,
            pagerState = pagerState,
            profilePicture = profilePicture,
            username = username,
            setProfilePicture = setProfilePicture,
            setUsername = setUsername,
            finish = finish
        )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PortraitSetProfilePager(
    pageCount: Int,
    pagerState: PagerState,
    profilePicture: MutableState<String?>,
    username: MutableState<String?>,
    setProfilePicture: (String) -> Unit,
    setUsername: (String) -> Unit,
    finish: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
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
                    0 -> {
                        ExplainScreen()
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
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "Let's create your profile with a few easy steps",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    1 -> {
                        ProfilePictureScreen(profilePicture)
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
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "You can change your picture in Settings anytime",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    2 -> {
                        UsernameScreen(username)
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
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "It is cool to know how we call you",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
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
private fun LandscapeSetProfilePager(
    pageCount: Int,
    pagerState: PagerState,
    profilePicture: MutableState<String?>,
    username: MutableState<String?>,
    setProfilePicture: (String) -> Unit,
    setUsername: (String) -> Unit,
    isNotCompact: Boolean,
    finish: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalPager(
            modifier = Modifier.weight(1f),
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
                    0 -> ExplainScreen(isLandscape = true)
                    1 -> ProfilePictureScreen(profilePicture = profilePicture, isLandscape = true)
                    2 -> UsernameScreen(username, isLandscape = true)
                }
            }
        }
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
        ) {
            if (!rememberImeState().value) {
                Box(modifier = Modifier.align(Alignment.TopCenter)) {
                    Logo()
                }
                Box(modifier = Modifier.align(Alignment.Center)) {
                    when (pagerState.currentPage) {
                        0 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Create your profile",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "Let's create your profile with a few easy steps",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                        1 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Choose your picture",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "You can change your picture in Settings anytime",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                        2 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Create your username",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "It is cool to know how we call you",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
            }
            Box(modifier = Modifier.align(if (rememberImeState().value) Alignment.Center else Alignment.BottomCenter)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (isNotCompact) {
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
    val configuration = LocalConfiguration.current
    val isNotCompact = windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact
    val isLandScape = configuration.screenWidthDp > configuration.screenHeightDp
    val pagerState = rememberPagerState()

    if (isLandScape)
        LandscapeOnBoardingPager(
            onBoardingDataList = onBoardingDataList,
            pagerState = pagerState,
            isNotCompact = isNotCompact,
            setFirstAccess = setFirstAccess
        )
    else
        PortraitOnBoardingPager(
            onBoardingDataList = onBoardingDataList,
            pagerState = pagerState,
            isNotCompact = isNotCompact,
            setFirstAccess = setFirstAccess
        )


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PortraitOnBoardingPager(
    onBoardingDataList: List<OnBoardingData>,
    pagerState: PagerState,
    isNotCompact: Boolean,
    setFirstAccess: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Logo()
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
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )
                    Text(
                        text = onBoardingDataList[pagerState.currentPage].description,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
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
                    shape = defaultButtonShape(),
                    modifier = Modifier.fillMaxWidth()
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LandscapeOnBoardingPager(
    onBoardingDataList: List<OnBoardingData>,
    pagerState: PagerState,
    isNotCompact: Boolean,
    setFirstAccess: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        HorizontalPager(
            pageCount = onBoardingDataList.size,
            modifier = Modifier.weight(1f),
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically,
            userScrollEnabled = false,
        ) {
            Image(
                painter = painterResource(id = onBoardingDataList[pagerState.currentPage].image),
                contentDescription = null,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(0.7f)
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.align(Alignment.TopCenter)) {
                Logo()
            }
            Box(modifier = Modifier.align(Alignment.Center)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = onBoardingDataList[pagerState.currentPage].title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )
                    Text(
                        text = onBoardingDataList[pagerState.currentPage].description,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                }
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (isNotCompact) {
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
                                shape = defaultButtonShape(),
                                modifier = Modifier.fillMaxWidth()
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
        }
    }
}
