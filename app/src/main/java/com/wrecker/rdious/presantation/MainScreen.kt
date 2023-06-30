package com.wrecker.rdious.presantation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wrecker.rdious.domain.entities.Facility

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(state: MainState) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Radius")
                },
                colors = TopAppBarDefaults.largeTopAppBarColors()
            )
        }
    ) {

        val selection = remember {
            mutableStateOf(-1)
        }
        val data = state.facilities
        val exclusion = state.exclusion
        if (data.isEmpty().not()) {
            LazyColumn(modifier = Modifier.padding(top = 48.dp)) {
                itemsIndexed(data) { index: Int, item: Facility ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentHeight(align = CenterVertically)
                            .selectable(
                                selected = selection.value == index,
                                onClick = {
                                    selection.value = if (selection.value != index) index else -1
                                }
                            ),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            if (selection.value == index) Color.DarkGray else Color.Gray
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Row(verticalAlignment = CenterVertically) {
                                Icon(painter = painterResource(id = IconsRes().getIcon(item.name)), contentDescription = "")
                                Text(text = item.name, Modifier.padding(12.dp))
                            }
                            if (selection.value == index) {
                                Divider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(2.dp)
                                )
                                var optionSelection by remember {
                                    mutableStateOf(-1)
                                }
                                LazyRow() {
                                    itemsIndexed(item.options) { index, item ->

                                        Card(
                                            Modifier
                                                .padding(16.dp)
                                                .selectable(
                                                    selected = optionSelection == index,
                                                    onClick = {
                                                        optionSelection =
                                                            if (optionSelection != index) index else -1
                                                    },
                                                    enabled = exclusion.binarySearchBy(item.id) { exclusion ->
                                                        exclusion.options_id
                                                    } == -1
                                                ),

                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(if (optionSelection == index) Color.Magenta else Color.Cyan)
                                        ) {
                                            Row(modifier = Modifier.padding(16.dp)) {
                                                Icon(
                                                    painter = painterResource(id = IconsRes().getIcon(item.icon)), ""
                                                )
                                                Text(text = item.name, modifier = Modifier.padding(4.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
