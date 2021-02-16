package com.jaax.mediaplayer

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import java.io.IOException

class MainActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {
    private lateinit var btn: Button
    private lateinit var record: Button
    private lateinit var uri: Uri

    companion object {
        private const val RECORDING_CODE = 7
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById( R.id.btnSound )
        record = findViewById( R.id.btnRecord )
    }

    override fun onResume() {
        super.onResume()
        btn.setOnClickListener {
            //playSound()
            //playWebSound()
            //playerNativo()

            playRecording()
        }

        record.setOnClickListener {
            record()
        }
    }

    //audio local (raw)
    private fun playSound() {
        val mediaPlayer = MediaPlayer.create( this, R.raw.neogeo )
        mediaPlayer.start()
    }

    private fun playWebSound() {
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener(this)
        try {
            mediaPlayer.setDataSource( " URL " )
            mediaPlayer.prepareAsync()
            //mediaPlayer.prepare()
            //mediaPlayer.start()
        } catch( ioe: IOException ) {
            ioe.printStackTrace()
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp!!.start()
    }

    private fun playerNativo() {
        val intent = Intent( Intent.ACTION_VIEW )

        //SD card
        val data1 = Uri.parse( "file:///sdcard/sonido.mp3" )

        //internamente
        val data = Uri.parse( "android.resource://$packageName/${R.raw.neogeo}" )

        getIntent().setDataAndType( data, "audio/mp3" )
        startActivity( intent )
    }

    private fun record() {
        val intent = Intent( MediaStore.Audio.Media.RECORD_SOUND_ACTION )
        startActivityForResult( intent, RECORDING_CODE )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( requestCode == RECORDING_CODE && resultCode == Activity.RESULT_OK ){
            uri = data?.data!!
        }
    }

    private fun playRecording() {
        val mp = MediaPlayer.create( this, uri )
        mp.start()
    }
}