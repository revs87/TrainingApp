package com.example.simpletextcomposeapplication.gendarizeapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpletextcomposeapplication.R
import com.example.simpletextcomposeapplication.gendarizeapp.repository.api.UiState
import com.example.simpletextcomposeapplication.theme.MyTheme
import com.example.simpletextcomposeapplication.theme.Purple200
import com.example.simpletextcomposeapplication.theme.Teal200
import kotlinx.coroutines.launch

class GendarizeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                GendarizeMyNameApp()
            }
        }
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GendarizeMyNameApp() {
    val viewModel: GenderizeViewModel = viewModel()
    val name by viewModel.nameLiveData.observeAsState("")
    val maleContentList by viewModel.maleListLiveData.observeAsState(listOf("Male results:"))
    val femaleContentList by viewModel.femaleListLiveData.observeAsState(listOf("Female results:"))

    val maleListState = rememberLazyListState()
    val femaleListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val uiState by viewModel.uiState().observeAsState()
    val loading = uiState == UiState.Loading
    val error = uiState is UiState.Error
    if (uiState is UiState.Success) {
        viewModel.updateList((uiState as UiState.Success).gProfile)
        coroutineScope.launch {
            maleListState.animateScrollToItem(maleContentList.size-1)
            femaleListState.animateScrollToItem(femaleContentList.size-1)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(), color = colorResource(R.color.yellow_banana)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            InsertNameTextField(
                textFieldValue = name,
                textFieldUpdate = { viewModel.setName(it.trim()) }) {
                viewModel.getGender(name)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LazyColumn(
                        state = maleListState,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        itemsIndexed(maleContentList) { index, item ->
                            Profile(item, if (index == 0) Color.White else Teal200)
                        }
                    }
                    LazyColumn(
                        state = femaleListState,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(femaleContentList) { index, item ->
                            Profile(item, if (index == 0) Color.White else Purple200)
                        }
                    }
                }
                if (loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary, strokeWidth = 3.dp
                    )
                }
            }
        }
    }
}

@Composable
fun Profile(item: String, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            contentColor = backgroundColor
        ),
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            style = TextStyle(
                fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold
            ),
            text = item,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InsertNameTextField(
    textFieldValue: String = "",
    textFieldUpdate: (newText: String) -> Unit = {},
    buttonAction: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(8.dp),
            value = textFieldValue,
            onValueChange = textFieldUpdate,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text)
        )
        Button(enabled = textFieldValue.isNotBlank(), onClick = { buttonAction.invoke() }) {
            Text(
                text = "Add name", style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
