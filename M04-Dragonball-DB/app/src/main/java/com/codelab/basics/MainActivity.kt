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

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme
import com.codelab.basics.ui.theme.Blue
import com.codelab.basics.ui.theme.DragonBallColors
import com.codelab.basics.ui.theme.Typography

/**
 * Sample DB Compose app with Master-Details pages
 * ShowPageMaster ... shows the list of DB elements
 * ShowPageDetails ... shows the detail contents of one element
 *
 * Added Adaptive behavior...
 *  - show master and details on different screens
 *  - if landscape, show master and details side-by-side
 *
 * Use the logcat to follow the logic.
 *
 * It's waiting for real data....
 */
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

    Surface(modifier, color = DragonBallColors().FRIEZA_PURPLE){
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
                    .padding(8.dp)
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
                        .background(Red)
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
        itemsIndexed(items = names) { pos, name ->
            Log.d("CodeLab_DB", "Item at index $pos is $name")
            if(pos == 0) {
                ShowEachListItem(character,character.id - 1, updateIndex, true)
            } else if (name.id != character.id) {
                ShowEachListItem(name = name, pos, updateIndex, false)
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
    modifier: Modifier = Modifier,
    index: Int
) {
    val windowInfo = rememberWindowInfo()  // sorta global, not good
    Column(
        modifier = modifier.fillMaxWidth(0.5f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(text ="Bio\n\n",
            color = DragonBallColors().BULMA_AQUA,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.ExtraBold))
        Text(text = name.bio,
            color = DragonBallColors().KAMI_WHITE,
            style = Typography.bodyLarge,
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
            onClick = { updateIndex(index + 1) })
        { Text(text = "Next") }
        if (index > 0) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = DragonBallColors().VEGETA_BLUE,
                    contentColor = DragonBallColors().KRILLIN_YELLOW
                ),
                onClick = { updateIndex(index - 1) })
            { Text(text = "Prev") }
        }
    }
}