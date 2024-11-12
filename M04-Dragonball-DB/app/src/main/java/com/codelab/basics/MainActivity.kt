/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


// Started with Android Developer code sample
// Code available at https://developer.android.com/codelabs/jetpack-compose-theming#0
// ...then swapped in a DB to populate the compose list

package com.codelab.basics

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import com.android.volley.toolbox.ImageRequest
import com.codelab.basics.ui.theme.BasicsCodelabTheme
import com.codelab.basics.ui.theme.Blue
import com.codelab.basics.ui.theme.DragonBallColors
import com.codelab.basics.ui.theme.Typography
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val characterDB: Repository<Character> =  CharacterDBConnection.getInstance(this@MainActivity)

        setContent {
            BasicsCodelabTheme {
                MyApp(
                    modifier = Modifier.fillMaxSize(),
                    names = characterDB.findAll()
                )
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    names: List<Character>,
) {
    val windowInfo = rememberWindowInfo()  // get size of this screen
    var index by remember { mutableIntStateOf(-1) } // which name to display
    var showMaster: Boolean = (index == -1) // fudge to force master list first, when compact

    Surface(modifier /*color = DragonBallColors().FRIEZA_PURPLE*/){
        // either one page at a time, or both side-by-side
        Log.d(
            "CodeLab_DB",
            "MyApp0: index = $index "
        )
        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
            if (showMaster or ((index < 0) or (index >= names.size))) {      //Always Check endpoints!
                Log.d("CodeLab_DB", "MyApp1: index = $index firstTime = $showMaster")
                showMaster = false
                ShowPageMaster(names = names,
                    updateIndex = { index = it })
            } else {
                Log.d("CodeLab_DB", "MyApp2: $index ")
                ShowPageDetails(name = names[index],  // List starts at 0, DB records start at 1
                    index = index,               // use index for prev, next screen
                    updateIndex = { index = it })
            }
        } else {  // show master details side-by-side
            // force visible entry if index=-1
            if (index < 0) {
                index = 0
            }
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(1.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Blue)
                ) {
                    ShowPageMaster(names = names,
                        updateIndex = { index = it })
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
//                        .background(DragonBallColors().FRIEZA_PURPLE)
                ) {
                    ShowPageDetails(name = names[index],  // List starts at 0, DB records start at 1
                        index = index,               // use index for prev, next screen
                        updateIndex = { index = it })
                }
            }
        }
    }
}

@Composable
private fun ShowPageMaster(
    modifier: Modifier = Modifier,
    names: List<Character>,
    updateIndex: (index: Int) -> Unit
) {
    val characterDB: Repository<Character> =  CharacterDBConnection.getInstance(LocalContext.current)
    val character = characterDB.getFavorite()
    LazyColumn(
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        itemsIndexed(items = names) { index, name ->
            Log.d("CodeLab_DB", "Item at index $index is $name")
            if(index == 0) {
                ShowEachListItem(character,character.id - 1, updateIndex, true)
            } else if (name.id != character.id) {
                ShowEachListItem(name = name, index, updateIndex, false)
            }
        }
    }
}

@Composable
private fun ShowEachListItem(
    name: Character,
    pos: Int,
    updateIndex: (index: Int) -> Unit,
    favorite: Boolean
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if(favorite) DragonBallColors().BULMA_AQUA else DragonBallColors().GOKU_ORANGE
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name, pos, updateIndex, favorite)
    }
}

@Composable
private fun CardContent(
    name: Character,
    pos: Int,
    updateIndex: (index: Int) -> Unit,
    favorite: Boolean
) {
    val characterDB: Repository<Character> =  CharacterDBConnection.getInstance(LocalContext.current)
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    fun saveImageToInternalStorage(context: Context, uri: Uri) {
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            inputStream?.let { input ->
                val outputDir = context.filesDir // Use internal storage
                val outputFile = File(outputDir, name.name + ".png") // Name of the file
                val outputStream = FileOutputStream(outputFile)
                input.copyTo(outputStream)
                input.close()
                outputStream.close()
                Log.i("image", "Image saved to internal storage: ${outputFile.absolutePath}")
                Toast.makeText(context, "Image saved to internal storage: ${outputFile.absolutePath}", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error saving image: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(), // Open a document
        onResult = { uri: Uri? ->
            if (uri != null) {
                saveImageToInternalStorage(context, uri)
                Log.i("image", uri.toString())
                Toast.makeText(
                    context,
                    "Image selected: $uri",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    )

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = DragonBallColors().VEGETA_BLUE,
                    contentColor = DragonBallColors().KRILLIN_YELLOW
                ),
                onClick = {
                    updateIndex(pos)
                })
            { Text(text = "Bio") }
            Log.d("favorite", favorite.toString())
            Text(
                // Just the name of this record
                text = if(favorite)name.name + " ⭐ Favorite" else name.name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
            )
            if (expanded) {
                Text("Race: " + name.race)
                Text("Gender: " + name.gender)
                Text("Attack: " + name.attack.toString())
                Text("Defense: " + name.defense.toString())
                Text("Ki Restore Speed: " + name.kiRestoreSpeed.toString())
                TextButton(
                    onClick = {
                        val imageIntent = Intent(Intent.ACTION_PICK)
                        imageIntent.type = "image/*"
                        imageIntent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
                        context.startActivity(imageIntent)
                    }) { Text("See Picture", color = DragonBallColors().VEGETA_BLUE) }
                TextButton(
                    onClick = {
                        imagePickerLauncher.launch(arrayOf("image/*"))
                    }
                ) { Text("Character Image", color = DragonBallColors().VEGETA_BLUE) }
                TextButton(
                    onClick = {
                        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(name.url))
                        context.startActivity(webIntent)
                }) { Text("Character Wiki", color = DragonBallColors().VEGETA_BLUE) }
            }
        }
        IconButton(onClick = {
            expanded = !expanded
            if (!expanded) {
                characterDB.incrementFavorite(name.id)
            }
        }) {
            Icon(
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Composable
private fun ShowPageDetails(
    name: Character,
    updateIndex: (index: Int) -> Unit,
    index: Int
) {
    val windowInfo = rememberWindowInfo()  // sorta global, not good
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val libreBaskerville = FontFamily(Font(R.font.librebaskerville_regular))
    val sourSans = FontFamily(Font(R.font.sourcecodepro_variablefont_wght))
    fun scrollToTop(){
        scope.launch {
            scrollState.scrollTo(0)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .verticalScroll(scrollState)
            .padding(20.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(text ="Bio",
            color = DragonBallColors().BULMA_AQUA,
            fontFamily = sourSans,
            style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.ExtraBold))

        Text(text = name.bio,
            color = DragonBallColors().KAMI_WHITE,
            style = Typography.bodyLarge,
            fontFamily = libreBaskerville,
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp,
            )
        )

        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = DragonBallColors().VEGETA_BLUE,
                    contentColor = DragonBallColors().KRILLIN_YELLOW
                ),
                onClick = { updateIndex(-1)})
            { Text(text = "Characters") }
        }
        // need check for end of array
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = DragonBallColors().VEGETA_BLUE,
                contentColor = DragonBallColors().KRILLIN_YELLOW
            ),
            onClick = {
                scrollToTop()
                updateIndex(index + 1)
            })
        { Text(text = "Next") }
        if (index > 0) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = DragonBallColors().VEGETA_BLUE,
                    contentColor = DragonBallColors().KRILLIN_YELLOW
                ),
                onClick = {
                    updateIndex(index - 1)
                    scrollToTop()
                })
            { Text(text = "Prev") }
        }
    }
}

@Composable
fun PopupBox(popupWidth: Float, popupHeight:Float, showPopup: Boolean, onClickOutside: () -> Unit, content: @Composable() () -> Unit) {
    if (showPopup) {
        // full screen background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DragonBallColors().FRIEZA_PURPLE)
                .zIndex(10F),
            contentAlignment = Alignment.Center
        ) {
            // popup
            Popup(
                alignment = Alignment.Center,
                properties = PopupProperties(
                    excludeFromSystemGesture = true,
                ),
                // to dismiss on click outside
                onDismissRequest = { onClickOutside() }
            ) {
                Box(
                    Modifier
                        .width(popupWidth.dp)
                        .height(popupHeight.dp)
                        .background(DragonBallColors().KAMI_WHITE)
                        .clip(RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    }
}