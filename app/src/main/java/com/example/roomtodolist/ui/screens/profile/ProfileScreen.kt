package com.example.roomtodolist.ui.screens.profile

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.roomtodolist.R
import com.example.roomtodolist.ui.components.ProfilePicture
import com.example.roomtodolist.ui.components.defaultTextFieldColors
import com.example.roomtodolist.ui.components.defaultTextFieldShape

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ActionBar(
            profilePicture = profileViewModel.getProfilePicture(),
            setProfilePicture = {
                profileViewModel.setProfilePicture(it)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        UsernameEdit(
            username = profileViewModel.getUsername(),
            setUsername = {
                profileViewModel.setUsername(it)
            }
        )
        DarkLightModeEdit(
            isDark = profileViewModel.isDarkMode(),
            setMode = { profileViewModel.setLightDarkMode(it) }
        )
    }
}

@Composable
fun DarkLightModeEdit(isDark: Boolean, setMode: (Boolean) -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primaryContainer, defaultTextFieldShape())
        .padding(horizontal = 25.dp, vertical = 2.5.dp)
    ) {
        Text(
            text = "Change mode",
            modifier = Modifier.align(Alignment.CenterStart),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium
        )
        Switch(
            checked = isDark,
            onCheckedChange = {
                setMode(it)
            },
            thumbContent = {
                Icon(
                    painter = painterResource(
                        id = if (isDark) R.drawable.outlined_moon_icon else R.drawable.outlined_sun_icon
                    ),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(2.dp)
                )
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onBackground,
                uncheckedThumbColor = MaterialTheme.colorScheme.onBackground,
                checkedTrackColor = MaterialTheme.colorScheme.background,
                uncheckedTrackColor = MaterialTheme.colorScheme.background,
                checkedBorderColor = MaterialTheme.colorScheme.background,
                uncheckedBorderColor = MaterialTheme.colorScheme.background
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun UsernameEdit(
    username: String?,
    setUsername : (String) -> Unit
) {
    var editMode by remember {
        mutableStateOf(false)
    }
    var usernameEdit by remember {
        mutableStateOf(username ?: "UNNAMED")
    }
    if (editMode)
        TextField(
            value = usernameEdit,
            onValueChange = { usernameEdit = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "username", style = MaterialTheme.typography.headlineMedium)
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        editMode = false
                        setUsername(usernameEdit)
                    },
                    modifier = Modifier
                        .size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.check_icon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .aspectRatio(1f)
                            .padding(8.dp)
                    )
                }
            },
            singleLine = true,
            shape = defaultTextFieldShape(),
            colors = defaultTextFieldColors(),
        )
    else
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer, defaultTextFieldShape())
            .padding(horizontal = 25.dp, vertical = 16.dp)
            .clickable { editMode = true }
        ) {
            Text(
                text = username ?: "UNNAMED",
                modifier = Modifier.align(Alignment.CenterStart),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium
            )
            Icon(
                painter = painterResource(id = R.drawable.outlined_non_lined_arrow_right_icon),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
}

@Composable
private fun ActionBar(
    profilePicture: Uri?,
    setProfilePicture: (Uri?) -> Unit,
    height: Dp = 180.dp
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val screenWidth = (configuration.screenWidthDp * density).dp
    val circleWidth = screenWidth.times(1.5f)
    val circleHeight = circleWidth.times(0.7f)

    val imageCropLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract(),
        onResult = {
            if (it.isSuccessful)
                setProfilePicture(it.uriContent)
            else
                setProfilePicture(null)
        }
    )

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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .drawBehind {
                drawOval(
                    color = primaryColor,
                    topLeft = Offset(
                        screenWidth
                            .minus(circleWidth)
                            .div(2).value, circleHeight.times(-0.75f).value
                    ),
                    size = Size(circleWidth.value, circleHeight.value),
                )
            }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(150.dp),
            contentAlignment = Alignment.Center
        ) {
            ProfilePicture(
                picture = profilePicture,
                modifier = Modifier
                    .size(150.dp),
                onPictureClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
            Icon(
                painter = painterResource(id = R.drawable.outlined_user_edit_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(Color.White, CircleShape)
                    .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                    .padding(5.dp),
                tint = Color.Black
            )
        }
    }
}