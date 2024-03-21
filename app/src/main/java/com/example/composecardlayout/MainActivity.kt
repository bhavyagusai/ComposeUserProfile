@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composecardlayout

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.composecardlayout.ui.theme.lightGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(userProfiles: List<UserProfile> = userProfileList) {
    Scaffold(topBar = { AppBar() }) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.LightGray
        ) {
            LazyColumn{
                items(userProfiles){
                    //userProfile -> helps to iterate each item from list
                        userProfile -> ProfileCard(userProfile = userProfile)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(){
    TopAppBar(
        navigationIcon = {
            Icon(
                Icons.Default.Home,
                contentDescription = "",
                modifier = Modifier.padding(horizontal =  12.dp),
                tint = White) },

        title = {
            Text("Messaging Application",
                color = White,
                style = MaterialTheme.typography.h6)},

        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colors.lightGreen)
    )
}

@Composable
fun ProfileCard(userProfile: UserProfile){
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clip(shape = CutCornerShape(topEnd = 24.dp)),
        elevation = 8.dp,
        backgroundColor = White,){

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {

            ProfilePicture(userProfile.pictureUrl,
                userProfile.status)

            ProfileContent(userProfile.name,
                userProfile.status)
        }
    }
}

@Composable
fun ProfilePicture(pictureUrl: String, onlineStatus: Boolean) {
    Card(shape = CircleShape,
        border = BorderStroke(width = 2.dp,
            color = if (onlineStatus)
                MaterialTheme.colors.lightGreen
            else Color.Red
        ),
        modifier = Modifier.padding(16.dp),
        elevation = 4.dp) {

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = pictureUrl)
                    .apply(block = fun ImageRequest.Builder.() {
                        transformations(CircleCropTransformation())
                    }).build()
            ),
            modifier = Modifier.size(72.dp),
            contentDescription = null,)
    }
}

@Composable
fun ProfileContent(userName: String, onlineStatus: Boolean) {
    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        CompositionLocalProvider(LocalContentAlpha provides (
                if (onlineStatus)
                    1f else ContentAlpha.medium)){
            Text(userName, style = MaterialTheme.typography.h5)
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium){
            Text(if (onlineStatus)
                "Active now"
            else "Offline",
                style = MaterialTheme.typography.body2)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainScreen()
}