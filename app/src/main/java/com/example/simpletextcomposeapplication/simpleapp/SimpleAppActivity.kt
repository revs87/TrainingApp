package com.example.simpletextcomposeapplication.simpleapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.simpletextcomposeapplication.R
import com.example.simpletextcomposeapplication._common.AlphaMedium
import com.example.simpletextcomposeapplication.categoriesapp.CategoriesAppActivity
import com.example.simpletextcomposeapplication.theme.LightGray700
import com.example.simpletextcomposeapplication.theme.LightGreen200
import com.example.simpletextcomposeapplication.theme.MyTheme
import com.example.simpletextcomposeapplication.theme.ShapesTopEndCut

class SimpleAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }

        val intent = Intent(this, CategoriesAppActivity::class.java)
        startActivity(intent)
    }


    @Composable
    fun MainScreen() {
        MyTheme {
            UsersApplication(userProfileList.sortedBy { it.name })
        }
    }

/* ----------------------------------- */
// Screens

    @Composable
    fun UsersApplication(userProfiles: List<UserProfile> = userProfileList.sortedBy { it.name }) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "users_list") {
            composable(route = "users_list") { UsersListScreen(userProfiles, navController) }
            composable(
                route = "user_details/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.IntType })
            ) { navBackStackEntry ->
                UserDetailsScreen(navBackStackEntry.arguments!!.getInt("userId"), navController)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun UsersListScreen(
        userProfiles: List<UserProfile> = userProfileList.sortedBy { it.name },
        navController: NavHostController? = null
    ) {
        Scaffold(topBar = { AppBar(title = "My app", icon = Icons.Default.Home) { } }) { padding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                LazyColumn {
                    items(userProfiles) { userProfile ->
                        ProfileCard(userProfile = userProfile) {
                            navController?.navigate(route = "user_details/${userProfile.id}")
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun UserDetailsScreen(userId: Int = 2, navController: NavHostController?) {
        val userProfile = userProfileList.first { userProfile -> userId == userProfile.id }
        Scaffold(topBar = {
            AppBar(
                title = "${userProfile.name} profile", icon = Icons.Default.ArrowBack
            ) {
                navController?.navigateUp()
            }
        }) { padding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    ProfilePicture(userProfile.pictureUrl, userProfile.status, 300.dp)
                    ProfileContent(
                        userProfile.name,
                        userProfile.status,
                        Alignment.CenterHorizontally
                    )
                }
            }
        }
    }


/* ----------------------------------- */

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppBar(title: String, icon: ImageVector, iconClickAction: () -> Unit) {
        TopAppBar(
            navigationIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "cd",
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .clickable { iconClickAction.invoke() }
                )
            },
            title = { Text(text = title) }
        )
    }

    @Composable
    fun ProfileCard(
        userProfile: UserProfile,
        onClick: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Top)
                .padding(10.dp)
                .clickable(onClick = { onClick.invoke() }),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                contentColor = Color.White
            ),
            shape = ShapesTopEndCut.medium
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                ProfilePicture(userProfile.pictureUrl, userProfile.status, 72.dp)
                ProfileContent(userProfile.name, userProfile.status, Alignment.Start)
            }
        }
    }

    @Composable
    fun ProfilePicture(pictureUrl: String, status: Boolean, imageSize: Dp) {
        Card(
            shape = CircleShape,
            border = BorderStroke(
                2.dp,
                color = if (status) LightGreen200 else LightGray700
            ),
            modifier = Modifier
                .padding(16.dp)
                .size(imageSize),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

//        1:
//        Image(
////            bitmap = ImageBitmap.imageResource(drawableId), // Do not use! -> synchronous, slow and leaky
//            painter = painterResource(id = drawableId),
//            modifier = Modifier.size(72.dp),
//            contentScale = ContentScale.Crop,
//            contentDescription = null
//        )

//        2:
//        Image(
//            painter = rememberAsyncImagePainter(model = data, contentScale = ContentScale.Crop),
//            modifier = Modifier.size(72.dp),
//            contentDescription = null
//        )

            val data = ImageRequest.Builder(LocalContext.current)
                .data(pictureUrl)
                .transformations(CircleCropTransformation())
                .build()
            SubcomposeAsyncImage(
                model = data,
                loading = { DefaultProfilePicture(true) },
                error = { DefaultProfilePicture(false) },
                modifier = Modifier.size(72.dp),
                contentDescription = null
            )
        }
    }

    @Composable
    fun DefaultProfilePicture(loading: Boolean) {
        Image(
            painter = painterResource(id = R.drawable.default_profile_picture),
            modifier = Modifier.size(72.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        if (loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 3.dp
            )
        }
    }

    @Composable
    fun ProfileContent(name: String, status: Boolean, alignment: Alignment.Horizontal) {
        Column(
            modifier = Modifier
                .padding(end = 16.dp),
            horizontalAlignment = alignment
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.alpha(if (status) 1f else AlphaMedium)
            )
            Text(
                text = if (status) {
                    "Active now"
                } else {
                    "Inactive"
                },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.alpha(AlphaMedium)
            )
        }
    }


//--------
// Lifting viewmodel dependencies

    @Composable
    fun MainScreenChild(
        viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
            factory = MyViewModelFactory()
        )
    ) {

//    val greetingListState = remember { mutableStateListOf<String>("John", "Amanda") }
//    val contentState = remember { mutableStateOf("") }

        val newNameStateContent by viewModel.messageLiveData.observeAsState("")

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GreetingMessage(
                newNameStateContent
            ) { newText -> viewModel.setMessage(newText) }

        }
    }

    @Composable
    fun GreetingMessage(
        textFieldValue: String,
        textFieldUpdate: (newText: String) -> Unit
    ) {
        TextField(value = textFieldValue, onValueChange = textFieldUpdate)
        Button(onClick = { }) {
            Text(
                text = if (textFieldValue.isBlank()) "Add text" else "Add $textFieldValue",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }


    val namesList = arrayListOf(
        "John",
        "Mike",
//    "Andrew",
//    "Danna",
        "Georgia"
    )

    //GreetingList(greetingListState,
//{ greetingListState.add(newNameStateContent.value); newNameStateContent.value = "" },
//newNameStateContent.value,
//{ newNameStateContent.value = it })
    @Composable
    fun GreetingList(
        namesList: List<String>,
        buttonClick: () -> Unit,
        textFieldValue: String,
        textFieldUpdate: (newText: String) -> Unit
    ) {
        for (name in namesList) {
            Greeting(name = name)
        }
        TextField(value = textFieldValue, onValueChange = textFieldUpdate)
        Button(onClick = buttonClick) {
            Text(text = "Add new name", style = MaterialTheme.typography.headlineSmall)
        }
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!", style = MaterialTheme.typography.headlineSmall)
    }

    @Composable
    fun SquaresScreen() {
        Surface(color = Color.LightGray, modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    ColouredSquare(Color.Yellow)
                    ColouredSquare(Color.Red)
                }
                ColouredSquare(Color.Green)
                ColouredSquare(Color.Magenta)
                ColouredSquare(Color.Blue)
                ColouredSquare(Color.Yellow)
                ColouredSquare(Color.Red)
            }
        }
    }

    @Composable
    fun ColouredSquare(colour: Color) {
        Surface(
            color = colour,
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
        ) { }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MainScreen()
    }

//@Preview(showBackground = true)
//@Composable
//fun GreetingText(name: String = "world") {
//    val style1 = TextStyle(
//        color = Color.Blue,
//        fontFamily = FontFamily.Monospace,
//        fontWeight = FontWeight.Bold,
//        fontSize = 18.sp
//    )
//    Text(
//        text = "Hello $name!",
//        modifier = Modifier
//            .width(200.dp)
//            .height(240.dp)
//            .padding(all = 24.dp)
//            .clickable { },
//        style = MaterialTheme.typography.h5,
//        fontWeight = FontWeight.SemiBold
//    )
//}
//
//@Composable
//fun GreetingButton() {
//    Button(onClick = { }) {
//        GreetingText(name = "button")
//        GreetingText(name = "remaining button")
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    Button(onClick = { }) {
//        GreetingText(name = "button")
//    }
//}
//
//class Person constructor(public val age: Int) {
//    fun greet(name: String) {
//        println("Hello $name!")
//    }
//}
//
//fun main(args: Array<String>) {
//    val rui = Person(35)
//    rui.greet("Diana")
//}


}
