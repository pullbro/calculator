package com.example.tipcalculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                val context = LocalContext.current
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Tip Calculator v3") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    },
                ) { innerPadding ->
                    TipScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }


    @Composable
    fun TipScreen(modifier: Modifier = Modifier) {
        Text("NEW UI CHECK", color = Color.Magenta)
        var billText by remember { mutableStateOf("") }
        var tipPercentText by remember { mutableStateOf("") }

        var tipAmount by remember { mutableDoubleStateOf(0.0) }
        var totalAmount by remember { mutableDoubleStateOf(0.0) }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LabeledInputRow(
                label = "Bill",
                value = billText,
                onValueChange = { billText = it },
                keyboardType = KeyboardType.Decimal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )

            LabeledInputRow(
                label = "Tip (%)",
                value = tipPercentText,
                onValueChange = { tipPercentText = it },
                keyboardType = KeyboardType.Number,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )

            HorizontalDivider(color = Color.Red, thickness = 10.dp)
            Spacer(Modifier.height(10.dp))

            LabeledOutputRow(
                label = "Tip ($)",
                valueText = "$tipAmount",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )

            LabeledOutputRow(
                label = "Total",
                valueText = "$totalAmount",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp)
            )
            Button(
                onClick = {

                    val bill = billText.toDouble()
                    val tipPct = tipPercentText.toDouble()

                    tipAmount = bill * tipPct / 100
                    totalAmount = bill + tipAmount
                },
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF00)),
                modifier = Modifier
                    .width(200.dp)
                    .height(55.dp)
            ) {
                Text("Calculate", color = Color.Black)
            }
        }
    }

    @Composable
    private fun LabeledInputRow(
        label: String,
        value: String,
        onValueChange: (String) -> Unit,
        keyboardType: KeyboardType,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier.height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(82.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(label, modifier = Modifier.padding(start = 10.dp))
            }
            TextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
        }
    }

    @Composable
    private fun LabeledOutputRow(
        label: String,
        valueText: String,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier.height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Gray label block on the left
            Box(
                modifier = Modifier
                    .width(82.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(label, modifier = Modifier.padding(start = 10.dp))
            }

            Spacer(Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color(0xFFB7FCB7)),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = valueText,
                    modifier = Modifier.padding(end = 12.dp),
                    color = Color.Black
                )
            }
        }
    }

    // I deleted the original number field stuff




    @Composable
            fun Greeting(name: String, modifier: Modifier = Modifier) {
                Text(
                    text = "Hello $name!",
                    modifier = modifier
                )
            }


            @Composable
            fun GreetingPreview() {
                TipCalculatorTheme {
                    Greeting("Android")
                }
            }

        }

