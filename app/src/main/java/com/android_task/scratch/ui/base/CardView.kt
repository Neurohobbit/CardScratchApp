package com.android_task.scratch.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android_task.scratch.R
import java.util.UUID

@Composable
fun CardView(
    id: UUID? = null,
    isActivated: Boolean? = null,
    isError: Boolean? = null
) {
    Column(
        Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, top = 16.dp, end = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isError == true) {
                    Color.Red
                } else if (isActivated == true) {
                    Color.Green
                } else {
                    Color.Gray
                }
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .wrapContentSize(),
            text = id?.toString() ?: stringResource(R.string.not_scratched),
            fontSize = 18.sp,
            fontWeight = if (id == null) FontWeight.Light else FontWeight.Bold,
            fontStyle = if (id == null) FontStyle.Italic else FontStyle.Normal,
            color = Color.White
        )

        if (id != null) {
            Text(
                modifier = Modifier
                    .wrapContentSize(),
                text = stringResource(if (isActivated == true) R.string.activated else R.string.not_activated),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                color = Color.White
            )
        }
    }
}