package com.kalmakasa.kalmakasa.ui.screens.doctor

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.ui.theme.KalmakasaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailDoctorScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = {
                    Text(
                        text = "Detail Doctor",
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                // Doctor
                Row {
                    Image(
                        painter = painterResource(R.drawable.placeholder_doctor_img),
                        contentDescription = null,
                        modifier = Modifier
                            .width(144.dp)
                            .height(160.dp)
                            .border(1.dp, Color.LightGray, MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Dr. Dzaky Nashshar",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "Psychology Specialist",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Experience",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            text = "2 Years",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Patients",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            text = "256",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Biography",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellen tesque in imperdiet augue. Mauris in purus lorem. In egestas ultrices hendrerit. In fringilla magna in odio semper iaculis.",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Biography",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

            }
        }
    }
}

@Preview
@Composable
fun DetailDoctorPreview() {
    KalmakasaTheme {
        DetailDoctorScreen()
    }

}