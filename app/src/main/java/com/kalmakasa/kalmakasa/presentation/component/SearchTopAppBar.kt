package com.kalmakasa.kalmakasa.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    query: String,
    modifier: Modifier = Modifier,
    useFilter: Boolean = true,
    placeholder: String = "Search",
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 16.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = query,
            shape = MaterialTheme.shapes.extraLarge,
            onValueChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(horizontal = 16.dp)
        )
        if (useFilter) {
            Row(
                modifier = Modifier
                    .height(FilterChipDefaults.Height)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(Modifier.width(16.dp))
                FilterChip(
                    selected = false,
                    onClick = {},
                    label = { Text("For Me") },
                    leadingIcon = { Icon(Icons.Outlined.AutoAwesome, null) },
                )
                VerticalDivider()
                FilterChip(selected = false, onClick = {}, label = { Text("Filter 1") })
                FilterChip(selected = false, onClick = {}, label = { Text("Filter 2") })
                FilterChip(selected = false, onClick = {}, label = { Text("Filter 3") })
                FilterChip(selected = false, onClick = {}, label = { Text("Filter 4") })
                Spacer(Modifier.width(16.dp))
            }
        }

    }
}