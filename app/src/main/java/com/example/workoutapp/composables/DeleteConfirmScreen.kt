package com.example.workoutapp.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workoutapp.routes.Screen
import com.example.workoutapp.ui.theme.darkBlue
import com.example.workoutapp.ui.theme.lightBlue
import com.example.workoutapp.viewModel.WorkoutState
import com.example.workoutapp.viewModel.deleteRoutine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun DeleteConfirmScaffolding(
    navController : NavController,
    state : WorkoutState,
    context : Context
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = lightBlue)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.4f),
            contentAlignment = Alignment.BottomCenter
        ){
            Text(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                text = "Are you sure you want to delete " + state.routineName + "?"
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 50.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(.95f),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(color = darkBlue)
                    .clickable {
                        navController.navigate(Screen.HomeScreen.route)
                        GlobalScope.launch(Dispatchers.IO) {
                            deleteRoutine(state, context)
                        }
                    },
                contentAlignment = Alignment.Center
            ){
                TextConfirm("Yes")
            }
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(color = darkBlue)
                    .clickable {
                               navController.navigate(Screen.EditRoutineScreen.route)
                    },
                contentAlignment = Alignment.Center
            ) {
                TextConfirm("No")
            }
        }
    }
}

@Composable
fun TextConfirm(text : String){
    Text(
        modifier = Modifier.padding(15.dp),
        fontSize = 24.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        text = text
    )
}