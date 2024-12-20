package com.example.expencetacker


import androidx.activity.result.launch
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(innerPadding: PaddingValues,navController:NavHostController) {
    val animatable = listOf(
        R.raw.s1,
        R.raw.s4,
        R.raw.s3
    )

    val titles = listOf(
        "Tracker Your Money",
        "Save Your Money",
        "Spend Wisely"
    )

    val description = listOf(
        "Record every transaction effortlessly, so you always know where your money goes",
        "Your expenses are safely stored and categorized for easy access whenever you need",
        "Share your financial progress with loved ones and stay on track to meet your savings goals."
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { animatable.size }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(top = 130.dp)
        ) { currentPage ->
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animatable[currentPage]))
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.size(250.dp)
                )
                Text(
                    text = titles[currentPage],
                    modifier = Modifier.padding(top = 20.dp),
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Text(
                    text = description[currentPage],
                    modifier = Modifier.padding(top = 20.dp),
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )

//
            }

        }
        PagerIndicator(
            pageCount = animatable.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier.padding( 60.dp)

        )
    }

    ButtonSection(
        pagerState=pagerState,
        pageCount = animatable.size,
        currentPage = pagerState.currentPage,
        navController = navController

    )





}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ButtonSection(pagerState: PagerState, pageCount: Int, currentPage: Int,navController: NavHostController) {
    val scope = rememberCoroutineScope();
    Box(modifier = Modifier.fillMaxSize()
        .padding(30.dp)){
        if(pagerState.currentPage!=2){
            Text(text="Next",
                color = Color.White,
                modifier=Modifier.align(Alignment.BottomEnd).padding(all = 18.dp)
                    .clickable {
                        scope.launch {
                            val nextpage =pagerState.currentPage+1
                            pagerState.scrollToPage(nextpage)
                        }
                    },
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text="Back",
                color = Color.White,
                modifier=Modifier.align(Alignment.BottomStart).padding(all = 18.dp)
                    .clickable {
                        scope.launch {
                            val backpage =pagerState.currentPage-1
                            if(backpage>=0){
                                pagerState.scrollToPage(backpage)}
                        }
                    },
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        else{
            OutlinedButton(onClick = {

            },
                modifier = Modifier.align(Alignment.BottomCenter)) {
                Text(
                    text = "Get Started",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                    }
                )
            }
        }
    }

}


@Composable
fun IndicatorSingle(isSelect: Boolean) {

}


@Composable
fun PagerIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        repeat(pageCount) {
            Indicator(isSelected = it == currentPage)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width by animateDpAsState(targetValue = if (isSelected) 35.dp else 15.dp)
    Box(
        modifier = Modifier
            .padding(2.dp)
            .height(15.dp)
            .width(width)
            .clip(CircleShape)
            .background(if (isSelected) Color.White else Color.Gray)
    )
}

