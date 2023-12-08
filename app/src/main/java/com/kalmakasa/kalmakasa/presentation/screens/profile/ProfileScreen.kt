package com.kalmakasa.kalmakasa.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.domain.model.User
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme

@Composable
fun ProfileScreen(
    uiState: User,
    onLogoutClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraSmall,
        ) {
            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.placeholder_consultant_img),
                    contentDescription = stringResource(R.string.profile_picture),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(60.dp)
                        .clip(CircleShape),
                )
                Column(Modifier.weight(1f)) {
                    Text(
                        text = uiState.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = uiState.email,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        Divider()
        // Profile Options
        ProfileOptionCard(
            icon = Icons.Default.Settings,
            title = stringResource(R.string.edit_profile),
            onClick = {},
            enabled = false,
        )
        ProfileOptionCard(
            icon = Icons.Default.History,
            title = stringResource(R.string.history),
            onClick = {},
            enabled = false,
        )
        ProfileOptionCard(
            icon = Icons.Default.HealthAndSafety,
            title = stringResource(R.string.my_health_test_result),
            onClick = {},
            enabled = false,
        )
        ProfileOptionCard(
            icon = Icons.Default.Logout,
            title = stringResource(R.string.logout),
            onClick = onLogoutClicked
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileOptionCard(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraSmall,
        enabled = enabled,
        onClick = onClick
    ) {
        Row(Modifier.padding(12.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(end = 12.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    KalmakasaTheme {
        ProfileScreen(uiState = User()) {

        }
    }
}