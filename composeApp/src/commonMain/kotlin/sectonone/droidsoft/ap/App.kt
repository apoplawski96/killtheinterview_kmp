package sectonone.droidsoft.ap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.Dispatchers
import sectonone.droidsoft.ap.json.ResourcesFileReader
import sectonone.droidsoft.ap.screens.home.HomeScreen
import sectonone.droidsoft.ap.theme.AppTheme
import sectonone.droidsoft.ap.theme.AppThemeMode
import sectonone.droidsoft.ap.theme.KTITheme
import sectonone.droidsoft.ap.theme.LocalThemeIsDark
import sectonone.droidsoft.ap.theme.ktiColors

@Composable
internal fun JSApp() = AppTheme {
    InitialUi(ResourcesFileReader(Dispatchers.Default))
}

@Composable
internal fun App() = AppTheme {
    KTITheme {
        Navigator(HomeScreen) { navigator ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            ) {
                SlideTransition(navigator)
            }
        }
    }
}

internal expect fun openUrl(url: String?)

@Composable
private fun InitialUi(fileReader: ResourcesFileReader) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    var jsonState by remember { mutableStateOf("") }

    LaunchedEffect(null) {
        val json = fileReader.readFile("questions_git.json") ?: "CAN'T READ FILE"
        jsonState = json
    }

    Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {

        Text(
            text = jsonState,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.weight(1.0f))

            var isDark by LocalThemeIsDark.current
            IconButton(
                onClick = { isDark = !isDark }
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp).size(20.dp),
                    imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = null
                )
            }
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    val imageVector = if (passwordVisibility) Icons.Default.Close else Icons.Default.Edit
                    Icon(imageVector, contentDescription = if (passwordVisibility) "Hide password" else "Show password")
                }
            }
        )

        Button(
            onClick = { /* Handle login logic here */ },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Login")
        }

        TextButton(
            onClick = { openUrl("https://github.com/terrakok") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Open github")
        }
    }
}