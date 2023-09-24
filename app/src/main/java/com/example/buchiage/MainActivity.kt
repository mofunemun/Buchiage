package com.example.buchiage

import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buchiage.ui.theme.BuchiageTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = applicationContext
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(CONTENT_TYPE_SONIFICATION)
                .build()
            val soundPool = SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(1)
                .build()
            val soundId = soundPool.load(context, R.raw.horn , 0)
            BuchiageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ){
                        Text(text = "押すとホーンが鳴るよ")
                        Spacer(modifier = Modifier.height(40.dp))
                        BuchiageButton(
                            "アゲる",
                            soundPool,
                            soundId,
                            Modifier
                                .height(100.dp)
                                .width(200.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BuchiageButton(title: String, soundPool: SoundPool, soundId: Int, modifier: Modifier) {
    Button(
        modifier = modifier,
        onClick = {
            soundPool.play(
                soundId,
                1.0f,
                1.0f,
                0,
                0,
                1.0f,
            )
            Log.d("OK", "音を鳴らしたよ！！！")
        }
    ) {
        Text(
            text = title,
            fontSize = 36.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BuchiageButtonPreview() {
    val context = LocalContext.current
    val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .setContentType(CONTENT_TYPE_SONIFICATION)
        .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
        .build()
    val soundPool = SoundPool.Builder()
        .setAudioAttributes(audioAttributes)
        .setMaxStreams(1)
        .build()
    val soundId = soundPool.load(context, R.raw.horn , 0)
    val modifier = Modifier
        .height(100.dp)
        .width(100.dp)
    BuchiageTheme {
        BuchiageButton("ブチアゲ", soundPool, soundId, modifier)
    }
}