package jp.chikaharu11.instant_drumpad_tr808

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.*
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.arthenica.mobileffmpeg.FFmpeg
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_dialog.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), CustomAdapterListener {

    private lateinit var mediaProjectionManager: MediaProjectionManager

    private val handler = Handler()
    private var audioName = ""
    var start = 0
    var stop = 0

    companion object {
        private const val READ_REQUEST_CODE2: Int = 43
        private const val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 41
        private const val RECORD_AUDIO_PERMISSION_REQUEST_CODE = 42
        private const val MEDIA_PROJECTION_REQUEST_CODE = 13
    }

    fun selectAudio() {
        val uri = Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fdata%2Fjp.chikaharu11.instant_drumpad_tr808%2Ffiles%2FMusic")
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
            type = "audio/*"
        }
        startActivityForResult(intent, READ_REQUEST_CODE2)
    }

    fun selectEX() {
        if (!isReadExternalStoragePermissionGranted()) {
            requestReadExternalStoragePermission()
        } else {
            tSoundList.clear()
            val audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val cursor = contentResolver.query(audioUri, null, null, null, null)
            cursor!!.moveToFirst()
            val path: Array<String?> = arrayOfNulls(cursor.count)
            for (i in path.indices) {
                path[i] = cursor.getString(cursor.getColumnIndex("_data"))
                tSoundList.add(SoundList(path[i].toString()))
                cursor.moveToNext()
            }

            cursor.close()
        }
    }

    private lateinit var soundPool: SoundPool

    private lateinit var mp: MediaPlayer

    private lateinit var lmp: LoopMediaPlayer

    private lateinit var aCustomAdapter: CustomAdapter
    private lateinit var bCustomAdapter: CustomAdapter
    private lateinit var cCustomAdapter: CustomAdapter
    private lateinit var dCustomAdapter: CustomAdapter
    private lateinit var eCustomAdapter: CustomAdapter

    private lateinit var nCustomAdapter: CustomAdapter
    private lateinit var oCustomAdapter: CustomAdapter
    private lateinit var pCustomAdapter: CustomAdapter
    private lateinit var qCustomAdapter: CustomAdapter

    private lateinit var sCustomAdapter: CustomAdapter
    private lateinit var tCustomAdapter: CustomAdapter

    private lateinit var aSoundList: MutableList<SoundList>
    private lateinit var bSoundList: MutableList<SoundList>
    private lateinit var cSoundList: MutableList<SoundList>
    private lateinit var dSoundList: MutableList<SoundList>
    private lateinit var eSoundList: MutableList<SoundList>

    private lateinit var nSoundList: MutableList<SoundList>
    private lateinit var oSoundList: MutableList<SoundList>
    private lateinit var pSoundList: MutableList<SoundList>
    private lateinit var qSoundList: MutableList<SoundList>

    private lateinit var sSoundList: MutableList<SoundList>
    private lateinit var tSoundList: MutableList<SoundList>

    private var sound1 = 0
    private var sound2 = 0
    private var sound3 = 0
    private var sound4 = 0
    private var sound5 = 0
    private var sound6 = 0
    private var sound7 = 0
    private var sound8 = 0
    private var sound9 = 0
    private var sound10 = 0
    private var sound11 = 0
    private var sound12 = 0
    private var sound13 = 0
    private var sound14 = 0
    private var sound15 = 0
    private var sound16 = 0


    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.text = "fx16"
        textView2.text = "fx20"
        textView3.text = "fx23"
        textView4.text = "fx31"
        textView5.text = "fx35"
        textView6.text = "fx36"
        textView7.text = "fx42"
        textView8.text = "fx47"
        textView9.text = "fx56"
        textView10.text = "g8bit_beat12_90bpm"
        textView11.text = "g8bit_beat24_130bpm"
        textView12.text = "g8bit_loop07_130bpm"
        textView13.text = "ds_loop02_120bpm"
        textView14.text = "gb_beat12_90bpm"
        textView15.text = "gb_loop32b_90bpm"

        MobileAds.initialize(this) {}

        val adView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)


        aSoundList = arrayListOf(
            SoundList("fx01.ogg"),
            SoundList("fx02.ogg"),
            SoundList("fx03.ogg"),
            SoundList("fx04.ogg"),
            SoundList("fx05.ogg"),
            SoundList("fx06.ogg"),
            SoundList("fx07.ogg"),
            SoundList("fx08.ogg"),
            SoundList("fx09.ogg"),
            SoundList("fx10.ogg"),
            SoundList("fx11.ogg"),
            SoundList("fx12.ogg"),
            SoundList("fx13.ogg"),
            SoundList("fx14.ogg"),
            SoundList("fx15.ogg"),
            SoundList("fx16.ogg"),
            SoundList("fx17.ogg"),
            SoundList("fx18.ogg"),
            SoundList("fx19.ogg"),
            SoundList("fx20.ogg"),
            SoundList("fx21.ogg"),
            SoundList("fx22.ogg"),
            SoundList("fx23.ogg"),
            SoundList("fx24.ogg"),
            SoundList("fx25.ogg"),
            SoundList("fx26.ogg"),
            SoundList("fx27.ogg"),
            SoundList("fx28.ogg"),
            SoundList("fx29.ogg"),
            SoundList("fx30.ogg"),
            SoundList("fx31.ogg"),
            SoundList("fx32.ogg"),
            SoundList("fx33.ogg"),
            SoundList("fx34.ogg"),
            SoundList("fx35.ogg"),
            SoundList("fx36.ogg"),
            SoundList("fx37.ogg"),
            SoundList("fx38.ogg"),
            SoundList("fx39.ogg"),
            SoundList("fx40.ogg"),
            SoundList("fx41.ogg"),
            SoundList("fx42.ogg"),
            SoundList("fx43.ogg"),
            SoundList("fx44.ogg"),
            SoundList("fx45.ogg"),
            SoundList("fx46.ogg"),
            SoundList("fx47.ogg"),
            SoundList("fx48.ogg"),
            SoundList("fx49.ogg"),
            SoundList("fx50.ogg"),
            SoundList("fx51.ogg"),
            SoundList("fx52.ogg"),
            SoundList("fx53.ogg"),
            SoundList("fx54.ogg"),
            SoundList("fx55.ogg"),
            SoundList("fx56.ogg"),
            SoundList("fx57.ogg"),
            SoundList("fx58.ogg"),
            SoundList("fx59.ogg"),
            SoundList("fx60.ogg"),
            SoundList("fx61.ogg"),
            SoundList("fx62.ogg"),
            SoundList("fx63.ogg"),
            SoundList("fx64.ogg"),
            SoundList("fx65.ogg"),
            SoundList("fx66.ogg"),
            SoundList("fx67.ogg"),
            SoundList("fx68.ogg"),
            SoundList("fx69.ogg"),
            SoundList("fx70.ogg")
                )

        bSoundList = arrayListOf(
            SoundList("g8bit_beat01_120bpm.ogg"),
            SoundList("g8bit_beat02_120bpm.ogg"),
            SoundList("g8bit_beat03_120bpm.ogg"),
            SoundList("g8bit_beat04_120bpm.ogg"),
            SoundList("g8bit_beat05_120bpm.ogg"),
            SoundList("g8bit_beat06_120bpm.ogg"),
            SoundList("g8bit_beat07_120bpm.ogg"),
            SoundList("g8bit_beat08_120bpm.ogg"),
            SoundList("g8bit_beat09_120bpm.ogg"),
            SoundList("g8bit_beat10_120bpm.ogg"),
            SoundList("g8bit_beat11_90bpm.ogg"),
            SoundList("g8bit_beat12_90bpm.ogg"),
            SoundList("g8bit_beat13_90bpm.ogg"),
            SoundList("g8bit_beat14_90bpm.ogg"),
            SoundList("g8bit_beat15_90bpm.ogg"),
            SoundList("g8bit_beat16_90bpm.ogg"),
            SoundList("g8bit_beat17_90bpm.ogg"),
            SoundList("g8bit_beat18_90bpm.ogg"),
            SoundList("g8bit_beat19_90bpm.ogg"),
            SoundList("g8bit_beat20_90bpm.ogg"),
            SoundList("g8bit_beat21_130bpm.ogg"),
            SoundList("g8bit_beat22_130bpm.ogg"),
            SoundList("g8bit_beat23_130bpm.ogg"),
            SoundList("g8bit_beat24_130bpm.ogg"),
            SoundList("g8bit_beat25_130bpm.ogg"),
            SoundList("g8bit_beat26_130bpm.ogg"),
            SoundList("g8bit_beat27_130bpm.ogg"),
            SoundList("g8bit_beat28_130bpm.ogg"),
            SoundList("g8bit_beat29_130bpm.ogg"),
            SoundList("g8bit_beat30_130bpm.ogg")
        )
        cSoundList = arrayListOf(
            SoundList("g8bit_loop01_90bpm.ogg"),
            SoundList("g8bit_loop02_90bpm.ogg"),
            SoundList("g8bit_loop03_90bpm.ogg"),
            SoundList("g8bit_loop04_90bpm.ogg"),
            SoundList("g8bit_loop05_90bpm.ogg"),
            SoundList("g8bit_loop06_130bpm.ogg"),
            SoundList("g8bit_loop07_130bpm.ogg"),
            SoundList("g8bit_loop08_130bpm.ogg"),
            SoundList("g8bit_loop09_130bpm.ogg"),
            SoundList("g8bit_loop10_130bpm.ogg"),
            SoundList("g8bit_loop11_130bpm.ogg"),
            SoundList("g8bit_loop12_130bpm.ogg"),
            SoundList("g8bit_loop13_130bpm.ogg"),
            SoundList("g8bit_loop14_130bpm.ogg"),
            SoundList("g8bit_loop15_130bpm.ogg"),
            SoundList("g8bit_loop16_130bpm.ogg"),
            SoundList("g8bit_loop17_130bpm.ogg"),
            SoundList("g8bit_loop18_130bpm.ogg"),
            SoundList("g8bit_loop19_130bpm.ogg"),
            SoundList("g8bit_loop20_130bpm.ogg"),
            SoundList("g8bit_loop21_130bpm.ogg"),
            SoundList("g8bit_loop22_130bpm.ogg"),
            SoundList("g8bit_loop23_130bpm.ogg"),
            SoundList("g8bit_loop24_130bpm.ogg"),
            SoundList("g8bit_loop25_130bpm.ogg"),
            SoundList("g8bit_loop26_130bpm.ogg"),
            SoundList("g8bit_loop27_130bpm.ogg"),
            SoundList("g8bit_loop29_130bpm.ogg"),
            SoundList("g8bit_loop30_130bpm.ogg"),
            SoundList("g8bit_loop31_130bpm.ogg"),
            SoundList("g8bit_loop32_130bpm.ogg"),
            SoundList("g8bit_loop33_130bpm.ogg"),
            SoundList("g8bit_loop34_130bpm.ogg"),
            SoundList("g8bit_loop35_130bpm.ogg"),
            SoundList("g8bit_loop36_130bpm.ogg"),
            SoundList("g8bit_loop37_130bpm.ogg"),
            SoundList("g8bit_loop38_130bpm.ogg"),
            SoundList("g8bit_loop39_130bpm.ogg")
        )
        dSoundList = arrayListOf(
            SoundList("ds_loop01_120bpm.ogg"),
            SoundList("ds_loop02_120bpm.ogg"),
            SoundList("ds_loop03_120bpm.ogg"),
            SoundList("ds_loop04_120bpm.ogg"),
            SoundList("ds_loop05_120bpm.ogg"),
            SoundList("ds_loop06_120bpm.ogg"),
            SoundList("ds_loop07_120bpm.ogg"),
            SoundList("ds_loop08_120bpm.ogg"),
            SoundList("ds_loop09_120bpm.ogg"),
            SoundList("ds_loop10_120bpm.ogg"),
            SoundList("ds_loop11_120bpm.ogg"),
            SoundList("ds_loop12_120bpm.ogg"),
            SoundList("gb_beat01_120bpm.ogg"),
            SoundList("gb_beat02_120bpm.ogg"),
            SoundList("gb_beat03_120bpm.ogg"),
            SoundList("gb_beat04_77bpm.ogg"),
            SoundList("gb_beat05_90bpm.ogg"),
            SoundList("gb_beat06_90bpm.ogg"),
            SoundList("gb_beat07_90bpm.ogg"),
            SoundList("gb_beat08_90bpm.ogg"),
            SoundList("gb_beat09_90bpm.ogg"),
            SoundList("gb_beat10_90bpm.ogg"),
            SoundList("gb_beat11_90bpm.ogg"),
            SoundList("gb_beat12_90bpm.ogg"),
            SoundList("gb_beat13_90bpm.ogg"),
            SoundList("gb_beat14_90bpm.ogg"),
            SoundList("gb_beat15_90bpm.ogg"),
            SoundList("gb_loop16_90bpm.ogg"),
            SoundList("gb_loop17_90bpm.ogg"),
            SoundList("gb_loop18_90bpm.ogg"),
            SoundList("gb_loop19_90bpm.ogg"),
            SoundList("gb_loop20_90bpm.ogg"),
            SoundList("gb_loop21_90bpm.ogg"),
            SoundList("gb_loop22_90bpm.ogg"),
            SoundList("gb_loop23_90bpm.ogg"),
            SoundList("gb_loop24_90bpm.ogg"),
            SoundList("gb_loop25_90bpm.ogg"),
            SoundList("gb_loop26_90bpm.ogg"),
            SoundList("gb_loop27_90bpm.ogg"),
            SoundList("gb_loop28_90bpm.ogg"),
            SoundList("gb_loop29_90bpm.ogg"),
            SoundList("gb_loop30_90bpm.ogg"),
            SoundList("gb_loop31_90bpm.ogg"),
            SoundList("gb_loop32a_90bpm.ogg")
        )
        eSoundList = arrayListOf(
            SoundList("gb_loop01_120bpm.ogg"),
            SoundList("gb_loop02_120bpm.ogg"),
            SoundList("gb_loop03_120bpm.ogg"),
            SoundList("gb_loop04_120bpm.ogg"),
            SoundList("gb_loop05_120bpm.ogg"),
            SoundList("gb_loop06_120bpm.ogg"),
            SoundList("gb_loop07_120bpm.ogg"),
            SoundList("gb_loop08_120bpm.ogg"),
            SoundList("gb_loop09_120bpm.ogg"),
            SoundList("gb_loop10_120bpm.ogg"),
            SoundList("gb_loop11_120bpm.ogg"),
            SoundList("gb_loop12_120bpm.ogg"),
            SoundList("gb_loop13_120bpm.ogg"),
            SoundList("gb_loop14_120bpm.ogg"),
            SoundList("gb_loop15_120bpm.ogg"),
            SoundList("gb_loop16_120bpm.ogg"),
            SoundList("gb_loop17_120bpm.ogg"),
            SoundList("gb_loop18_120bpm.ogg"),
            SoundList("gb_loop19_120bpm.ogg"),
            SoundList("gb_loop20_120bpm.ogg"),
            SoundList("gb_loop21_120bpm.ogg"),
            SoundList("gb_loop22_120bpm.ogg"),
            SoundList("gb_loop23_120bpm.ogg"),
            SoundList("gb_loop24_120bpm.ogg"),
            SoundList("gb_loop25_120bpm.ogg"),
            SoundList("gb_loop26_120bpm.ogg"),
            SoundList("gb_loop27_120bpm.ogg"),
            SoundList("gb_loop28_120bpm.ogg"),
            SoundList("gb_loop29_120bpm.ogg"),
            SoundList("gb_loop30_120bpm.ogg"),
            SoundList("gb_loop31_120bpm.ogg"),
            SoundList("gb_loop32b_90bpm.ogg"),
            SoundList("gb_loop33_90bpm.ogg"),
            SoundList("gb_loop34_90bpm.ogg"),
            SoundList("gb_loop35_90bpm.ogg"),
            SoundList("gb_loop36_90bpm.ogg"),
            SoundList("gb_loop37_90bpm.ogg"),
            SoundList("gb_loop38_90bpm.ogg"),
            SoundList("gb_loop39_90bpm.ogg"),
            SoundList("gb_loop40_90bpm.ogg"),
            SoundList("gb_loop41_90bpm.ogg")
        )
        nSoundList = arrayListOf(
            SoundList("g8bit_beat01_120bpm.ogg"),
            SoundList("g8bit_beat02_120bpm.ogg"),
            SoundList("g8bit_beat03_120bpm.ogg"),
            SoundList("g8bit_beat04_120bpm.ogg"),
            SoundList("g8bit_beat05_120bpm.ogg"),
            SoundList("g8bit_beat06_120bpm.ogg"),
            SoundList("g8bit_beat07_120bpm.ogg"),
            SoundList("g8bit_beat08_120bpm.ogg"),
            SoundList("g8bit_beat09_120bpm.ogg"),
            SoundList("g8bit_beat10_120bpm.ogg"),
            SoundList("g8bit_beat11_90bpm.ogg"),
            SoundList("g8bit_beat12_90bpm.ogg"),
            SoundList("g8bit_beat13_90bpm.ogg"),
            SoundList("g8bit_beat14_90bpm.ogg"),
            SoundList("g8bit_beat15_90bpm.ogg"),
            SoundList("g8bit_beat16_90bpm.ogg"),
            SoundList("g8bit_beat17_90bpm.ogg"),
            SoundList("g8bit_beat18_90bpm.ogg"),
            SoundList("g8bit_beat19_90bpm.ogg"),
            SoundList("g8bit_beat20_90bpm.ogg"),
            SoundList("g8bit_beat21_130bpm.ogg"),
            SoundList("g8bit_beat22_130bpm.ogg"),
            SoundList("g8bit_beat23_130bpm.ogg"),
            SoundList("g8bit_beat24_130bpm.ogg"),
            SoundList("g8bit_beat25_130bpm.ogg"),
            SoundList("g8bit_beat26_130bpm.ogg"),
            SoundList("g8bit_beat27_130bpm.ogg"),
            SoundList("g8bit_beat28_130bpm.ogg"),
            SoundList("g8bit_beat29_130bpm.ogg"),
            SoundList("g8bit_beat30_130bpm.ogg")
        )
        oSoundList = arrayListOf(
            SoundList("g8bit_loop01_90bpm.ogg"),
            SoundList("g8bit_loop02_90bpm.ogg"),
            SoundList("g8bit_loop03_90bpm.ogg"),
            SoundList("g8bit_loop04_90bpm.ogg"),
            SoundList("g8bit_loop05_90bpm.ogg"),
            SoundList("g8bit_loop06_130bpm.ogg"),
            SoundList("g8bit_loop07_130bpm.ogg"),
            SoundList("g8bit_loop08_130bpm.ogg"),
            SoundList("g8bit_loop09_130bpm.ogg"),
            SoundList("g8bit_loop10_130bpm.ogg"),
            SoundList("g8bit_loop11_130bpm.ogg"),
            SoundList("g8bit_loop12_130bpm.ogg"),
            SoundList("g8bit_loop13_130bpm.ogg"),
            SoundList("g8bit_loop14_130bpm.ogg"),
            SoundList("g8bit_loop15_130bpm.ogg"),
            SoundList("g8bit_loop16_130bpm.ogg"),
            SoundList("g8bit_loop17_130bpm.ogg"),
            SoundList("g8bit_loop18_130bpm.ogg"),
            SoundList("g8bit_loop19_130bpm.ogg"),
            SoundList("g8bit_loop20_130bpm.ogg"),
            SoundList("g8bit_loop21_130bpm.ogg"),
            SoundList("g8bit_loop22_130bpm.ogg"),
            SoundList("g8bit_loop23_130bpm.ogg"),
            SoundList("g8bit_loop24_130bpm.ogg"),
            SoundList("g8bit_loop25_130bpm.ogg"),
            SoundList("g8bit_loop26_130bpm.ogg"),
            SoundList("g8bit_loop27_130bpm.ogg"),
            SoundList("g8bit_loop29_130bpm.ogg"),
            SoundList("g8bit_loop30_130bpm.ogg"),
            SoundList("g8bit_loop31_130bpm.ogg"),
            SoundList("g8bit_loop32_130bpm.ogg"),
            SoundList("g8bit_loop33_130bpm.ogg"),
            SoundList("g8bit_loop34_130bpm.ogg"),
            SoundList("g8bit_loop35_130bpm.ogg"),
            SoundList("g8bit_loop36_130bpm.ogg"),
            SoundList("g8bit_loop37_130bpm.ogg"),
            SoundList("g8bit_loop38_130bpm.ogg"),
            SoundList("g8bit_loop39_130bpm.ogg")
        )
        pSoundList = arrayListOf(
            SoundList("ds_loop01_120bpm.ogg"),
            SoundList("ds_loop02_120bpm.ogg"),
            SoundList("ds_loop03_120bpm.ogg"),
            SoundList("ds_loop04_120bpm.ogg"),
            SoundList("ds_loop05_120bpm.ogg"),
            SoundList("ds_loop06_120bpm.ogg"),
            SoundList("ds_loop07_120bpm.ogg"),
            SoundList("ds_loop08_120bpm.ogg"),
            SoundList("ds_loop09_120bpm.ogg"),
            SoundList("ds_loop10_120bpm.ogg"),
            SoundList("ds_loop11_120bpm.ogg"),
            SoundList("ds_loop12_120bpm.ogg"),
            SoundList("gb_beat01_120bpm.ogg"),
            SoundList("gb_beat02_120bpm.ogg"),
            SoundList("gb_beat03_120bpm.ogg"),
            SoundList("gb_beat04_77bpm.ogg"),
            SoundList("gb_beat05_90bpm.ogg"),
            SoundList("gb_beat06_90bpm.ogg"),
            SoundList("gb_beat07_90bpm.ogg"),
            SoundList("gb_beat08_90bpm.ogg"),
            SoundList("gb_beat09_90bpm.ogg"),
            SoundList("gb_beat10_90bpm.ogg"),
            SoundList("gb_beat11_90bpm.ogg"),
            SoundList("gb_beat12_90bpm.ogg"),
            SoundList("gb_beat13_90bpm.ogg"),
            SoundList("gb_beat14_90bpm.ogg"),
            SoundList("gb_beat15_90bpm.ogg"),
            SoundList("gb_loop16_90bpm.ogg"),
            SoundList("gb_loop17_90bpm.ogg"),
            SoundList("gb_loop18_90bpm.ogg"),
            SoundList("gb_loop19_90bpm.ogg"),
            SoundList("gb_loop20_90bpm.ogg"),
            SoundList("gb_loop21_90bpm.ogg"),
            SoundList("gb_loop22_90bpm.ogg"),
            SoundList("gb_loop23_90bpm.ogg"),
            SoundList("gb_loop24_90bpm.ogg"),
            SoundList("gb_loop25_90bpm.ogg"),
            SoundList("gb_loop26_90bpm.ogg"),
            SoundList("gb_loop27_90bpm.ogg"),
            SoundList("gb_loop28_90bpm.ogg"),
            SoundList("gb_loop29_90bpm.ogg"),
            SoundList("gb_loop30_90bpm.ogg"),
            SoundList("gb_loop31_90bpm.ogg"),
            SoundList("gb_loop32a_90bpm.ogg")
        )
        qSoundList = arrayListOf(
            SoundList("gb_loop01_120bpm.ogg"),
            SoundList("gb_loop02_120bpm.ogg"),
            SoundList("gb_loop03_120bpm.ogg"),
            SoundList("gb_loop04_120bpm.ogg"),
            SoundList("gb_loop05_120bpm.ogg"),
            SoundList("gb_loop06_120bpm.ogg"),
            SoundList("gb_loop07_120bpm.ogg"),
            SoundList("gb_loop08_120bpm.ogg"),
            SoundList("gb_loop09_120bpm.ogg"),
            SoundList("gb_loop10_120bpm.ogg"),
            SoundList("gb_loop11_120bpm.ogg"),
            SoundList("gb_loop12_120bpm.ogg"),
            SoundList("gb_loop13_120bpm.ogg"),
            SoundList("gb_loop14_120bpm.ogg"),
            SoundList("gb_loop15_120bpm.ogg"),
            SoundList("gb_loop16_120bpm.ogg"),
            SoundList("gb_loop17_120bpm.ogg"),
            SoundList("gb_loop18_120bpm.ogg"),
            SoundList("gb_loop19_120bpm.ogg"),
            SoundList("gb_loop20_120bpm.ogg"),
            SoundList("gb_loop21_120bpm.ogg"),
            SoundList("gb_loop22_120bpm.ogg"),
            SoundList("gb_loop23_120bpm.ogg"),
            SoundList("gb_loop24_120bpm.ogg"),
            SoundList("gb_loop25_120bpm.ogg"),
            SoundList("gb_loop26_120bpm.ogg"),
            SoundList("gb_loop27_120bpm.ogg"),
            SoundList("gb_loop28_120bpm.ogg"),
            SoundList("gb_loop29_120bpm.ogg"),
            SoundList("gb_loop30_120bpm.ogg"),
            SoundList("gb_loop31_120bpm.ogg"),
            SoundList("gb_loop32b_90bpm.ogg"),
            SoundList("gb_loop33_90bpm.ogg"),
            SoundList("gb_loop34_90bpm.ogg"),
            SoundList("gb_loop35_90bpm.ogg"),
            SoundList("gb_loop36_90bpm.ogg"),
            SoundList("gb_loop37_90bpm.ogg"),
            SoundList("gb_loop38_90bpm.ogg"),
            SoundList("gb_loop39_90bpm.ogg"),
            SoundList("gb_loop40_90bpm.ogg"),
            SoundList("gb_loop41_90bpm.ogg")
        )
        sSoundList = arrayListOf()
        tSoundList = arrayListOf()

        val listView = findViewById<ListView>(R.id.list_view)

        aCustomAdapter = CustomAdapter(this, aSoundList, this)
        bCustomAdapter = CustomAdapter(this, bSoundList, this)
        cCustomAdapter = CustomAdapter(this, cSoundList, this)
        dCustomAdapter = CustomAdapter(this, dSoundList, this)
        eCustomAdapter = CustomAdapter(this, eSoundList, this)
        nCustomAdapter = CustomAdapter(this, nSoundList, this)
        oCustomAdapter = CustomAdapter(this, oSoundList, this)
        pCustomAdapter = CustomAdapter(this, pSoundList, this)
        qCustomAdapter = CustomAdapter(this, qSoundList, this)
        sCustomAdapter = CustomAdapter(this, sSoundList, this)
        tCustomAdapter = CustomAdapter(this, tSoundList, this)

        listView.adapter = aCustomAdapter

        mp = MediaPlayer()

        supportActionBar?.title ="g8bit_beat01_120bpm"


            val audioUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
            val cursor = contentResolver.query(audioUri, null, null, null, null)
            cursor!!.moveToFirst()
            val path: Array<String?> = arrayOfNulls(cursor.count)
            for (i in path.indices) {
                path[i] = cursor.getString(cursor.getColumnIndex("_data"))
                sSoundList.add(SoundList(path[i].toString()))
                cursor.moveToNext()
            }

            cursor.close()


        val meSpinner = findViewById<Spinner>(R.id.menu_spinner)

        val adapter3 = ArrayAdapter.createFromResource(this, R.array.spinnerItems, android.R.layout.simple_spinner_item)

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)



        meSpinner.adapter = adapter3


        meSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?, position: Int, id: Long
            ) {
                if (!meSpinner.isFocusable) {
                    meSpinner.isFocusable = true
                    return
                }

                val soundListView = findViewById<ListView>(R.id.list_view)

                when(position){
                    0 -> {
                        radioButton19.performClick()
                        soundListView.adapter = aCustomAdapter
                        aCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    1 -> {
                        radioButton19.performClick()
                        soundListView.adapter = bCustomAdapter
                        bCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    2 -> {
                        radioButton19.performClick()
                        soundListView.adapter = cCustomAdapter
                        cCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    3 -> {
                        radioButton19.performClick()
                        soundListView.adapter = dCustomAdapter
                        dCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    4 -> {
                        radioButton19.performClick()
                        soundListView.adapter = eCustomAdapter
                        eCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    5 -> {
                        radioButton18.performClick()
                        soundListView.adapter = sCustomAdapter
                        sCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    6 -> {
                        selectEX()
                        radioButton18.performClick()
                        soundListView.adapter = tCustomAdapter
                        tCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    7 -> selectAudio()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        meSpinner.isFocusable = false


        val audioAttributes = AudioAttributes.Builder()

                .setUsage(AudioAttributes.USAGE_GAME)

                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()

        soundPool = SoundPool.Builder()

                .setAudioAttributes(audioAttributes)

                .setMaxStreams(20)
                .build()

        sound1 = soundPool.load(assets.openFd("fx16.ogg"), 1)

        sound2 = soundPool.load(assets.openFd("fx20.ogg"), 1)

        sound3 = soundPool.load(assets.openFd("fx23.ogg"), 1)

        sound4 = soundPool.load(assets.openFd("fx31.ogg"), 1)

        sound5 = soundPool.load(assets.openFd("fx35.ogg"), 1)

        sound6 = soundPool.load(assets.openFd("fx36.ogg"), 1)

        sound7 = soundPool.load(assets.openFd("fx42.ogg"), 1)

        sound8 = soundPool.load(assets.openFd("fx47.ogg"), 1)

        sound9 = soundPool.load(assets.openFd("fx56.ogg"), 1)

        sound10 = soundPool.load(assets.openFd("g8bit_beat12_90bpm.ogg"), 1)

        sound11 = soundPool.load(assets.openFd("g8bit_beat24_130bpm.ogg"), 1)

        sound12 = soundPool.load(assets.openFd("g8bit_loop07_130bpm.ogg"), 1)

        sound13 = soundPool.load(assets.openFd("ds_loop02_120bpm.ogg"), 1)

        sound14 = soundPool.load(assets.openFd("gb_beat12_90bpm.ogg"), 1)

        sound15 = soundPool.load(assets.openFd("gb_loop32b_90bpm.ogg"), 1)

        lmp = LoopMediaPlayer.create(this, Uri.parse("android.resource://" + packageName + "/raw/" + R.raw.g8bit_beat01_120bpm))


        imageView.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound1, 1.0f, 1.0f, 1, 0, 1.0f)
                }
                false
        }

        imageView2.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound2, 1.0f, 1.0f, 1, 0, 1.0f)
                }
                false
        }

        imageView3.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound3, 1.0f, 1.0f, 0, 0, 1.0f)
                }
                false
        }

        imageView4.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound4, 1.0f, 1.0f, 0, 0, 1.0f)
                }
                false
        }

        imageView5.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound5, 1.0f, 1.0f, 0, 0, 1.0f)
                }
                false
        }

        imageView6.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound6, 1.0f, 1.0f, 0, 0, 1.0f)
                }
                false
        }

        imageView7.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound7, 1.0f, 1.0f, 0, 0, 1.0f)
                }
                false
        }

        imageView8.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound8, 1.0f, 1.0f, 0, 0, 1.0f)
                }
                false
        }

        imageView9.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound9, 1.0f, 1.0f, 0, 0, 1.0f)
                }
                false

        }

        imageView10.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound10, 1.0f, 1.0f, 0, 0, 1.0f)
                }
                false
        }

        imageView11.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound11, 1.0f, 1.0f, 0, 0, 1.0f)
                }
                false
        }

        imageView12.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound12, 1.0f, 1.0f, 0, 0, 1.0f)
                }
                false
        }

        imageView13.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound13, 1.0f, 1.0f, 0, 0, 1.0f)
                }
                false
        }

        imageView14.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound14, 1.0f, 1.0f, 0, 0, 1.0f)
                }
                false
        }

        imageView15.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                        soundPool.play(sound15, 1.0f, 1.0f, 0, 0, 1.0f)
                }
                false
        }


        imageView.setOnLongClickListener {
            radioButton.performClick()
            meSpinner.performClick()
            true
        }
        imageView2.setOnLongClickListener {
            radioButton2.performClick()
            meSpinner.performClick()
            true
        }
        imageView3.setOnLongClickListener {
            radioButton3.performClick()
            meSpinner.performClick()
            true
        }
        imageView4.setOnLongClickListener {
            radioButton4.performClick()
            meSpinner.performClick()
            true
        }
        imageView5.setOnLongClickListener {
            radioButton5.performClick()
            meSpinner.performClick()
            true
        }
        imageView6.setOnLongClickListener {
            radioButton6.performClick()
            meSpinner.performClick()
            true
        }
        imageView7.setOnLongClickListener {
            radioButton7.performClick()
            meSpinner.performClick()
            true
        }
        imageView8.setOnLongClickListener {
            radioButton8.performClick()
            meSpinner.performClick()
            true
        }
        imageView9.setOnLongClickListener {
            radioButton9.performClick()
            meSpinner.performClick()
            true
        }
        imageView10.setOnLongClickListener {
            radioButton10.performClick()
            meSpinner.performClick()
            true
        }
        imageView11.setOnLongClickListener {
            radioButton11.performClick()
            meSpinner.performClick()
            true
        }
        imageView12.setOnLongClickListener {
            radioButton12.performClick()
            meSpinner.performClick()
            true
        }
        imageView13.setOnLongClickListener {
            radioButton13.performClick()
            meSpinner.performClick()
            true
        }
        imageView14.setOnLongClickListener {
            radioButton14.performClick()
            meSpinner.performClick()
            true
        }
        imageView15.setOnLongClickListener {
            radioButton15.performClick()
            meSpinner.performClick()
            true
        }
    }

    override fun clicked(soundList: SoundList) {
        sound16 = if (radioButton18.isChecked) {
            soundPool.load(soundList.name, 1)
        } else {
            soundPool.load(assets.openFd(soundList.name), 1)
        }
            soundPool.setOnLoadCompleteListener { soundPool, _, _ ->
                soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f)
            }
    }

    override fun clicked2(soundList: SoundList) {
        when {
            radioButton.isChecked && radioButton18.isChecked -> {
                sound1 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton2.isChecked && radioButton18.isChecked -> {
                sound2 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView2.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton3.isChecked && radioButton18.isChecked -> {
                sound3 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView3.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton4.isChecked && radioButton18.isChecked -> {
                sound4 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView4.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton5.isChecked && radioButton18.isChecked -> {
                sound5 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView5.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton6.isChecked && radioButton18.isChecked -> {
                sound6 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView6.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton7.isChecked && radioButton18.isChecked -> {
                sound7 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView7.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton8.isChecked && radioButton18.isChecked -> {
                sound8 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView8.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton9.isChecked && radioButton18.isChecked -> {
                sound9 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView9.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton10.isChecked && radioButton18.isChecked -> {
                sound10 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView10.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton11.isChecked && radioButton18.isChecked -> {
                sound11 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView11.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton12.isChecked && radioButton18.isChecked -> {
                sound12 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView12.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton13.isChecked && radioButton18.isChecked -> {
                sound13 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView13.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton14.isChecked && radioButton18.isChecked -> {
                sound14 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView14.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton15.isChecked && radioButton18.isChecked -> {
                sound15 = soundPool.load(soundList.name, 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView15.text = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
            }
            radioButton16.isChecked && radioButton18.isChecked -> {
                lmp.release()
                lmp = LoopMediaPlayer(this@MainActivity, Uri.parse(soundList.name))
                supportActionBar?.title = soundList.name.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
            }
            radioButton.isChecked && radioButton19.isChecked -> {
                sound1 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton2.isChecked && radioButton19.isChecked -> {
                sound2 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView2.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton3.isChecked && radioButton19.isChecked -> {
                sound3 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView3.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton4.isChecked && radioButton19.isChecked -> {
                sound4 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView4.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton5.isChecked && radioButton19.isChecked -> {
                sound5 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView5.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton6.isChecked && radioButton19.isChecked -> {
                sound6 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView6.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton7.isChecked && radioButton19.isChecked -> {
                sound7 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView7.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton8.isChecked && radioButton19.isChecked -> {
                sound8 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView8.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton9.isChecked && radioButton19.isChecked -> {
                sound9 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView9.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton10.isChecked && radioButton19.isChecked -> {
                sound10 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView10.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton11.isChecked && radioButton19.isChecked -> {
                sound11 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView11.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton12.isChecked && radioButton19.isChecked -> {
                sound12 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView12.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton13.isChecked && radioButton19.isChecked -> {
                sound13 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView13.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton14.isChecked && radioButton19.isChecked -> {
                sound14 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView14.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton15.isChecked && radioButton19.isChecked -> {
                sound15 = soundPool.load(assets.openFd(soundList.name), 1)
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
                textView15.text = soundList.name.replaceAfterLast(".", "").replace(".", "")
            }
            radioButton16.isChecked -> {
                lmp.release()
                lmp = LoopMediaPlayer(this@MainActivity, Uri.parse("android.resource://" + packageName + "/raw/" + soundList.name.replace(".ogg", "")))
                supportActionBar?.title = soundList.name.replaceAfterLast(".", "").replace(".", "")
                soundPool.setOnLoadCompleteListener{ soundPool, _, _ ->
                    soundPool.stop(soundPool.play(sound16, 1.0f, 1.0f, 0, 0, 1.0f))
                }
            }
            radioButton17.isChecked -> {
                audioName = soundList.name
                button4.performClick()
            }
        }
        findViewById<ListView>(R.id.list_view).visibility = View.INVISIBLE
    }

    private fun startCapturing() {
        if (!isRecordAudioPermissionGranted()) {
            requestRecordAudioPermission()
        } else {
            startMediaProjectionRequest()
        }
    }

    private fun stopCapturing() {

        startService(Intent(this, AudioCaptureService::class.java).apply {
            action = AudioCaptureService.ACTION_STOP
        })
        menuSwitch0 = true
        switch0.isChecked = false
        invalidateOptionsMenu()
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#5A5A66")))
    }

    private fun isRecordAudioPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestRecordAudioPermission() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_REQUEST_CODE
        )
    }

    private fun isReadExternalStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestReadExternalStoragePermission() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                RECORD_AUDIO_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (requestCode == RECORD_AUDIO_PERMISSION_REQUEST_CODE) {
            if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                        this,
                        R.string.onRequestPermissionsResult1,
                        Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                        this,
                        R.string.onRequestPermissionsResult2,
                        Toast.LENGTH_LONG
                ).show()
            }
        }

        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                        this,
                        R.string.onRequestPermissionsResult1,
                        Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                        this,
                        R.string.onRequestPermissionsResult2,
                        Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    private fun startMediaProjectionRequest() {
        mediaProjectionManager =
                applicationContext.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        startActivityForResult(
                mediaProjectionManager.createScreenCaptureIntent(),
                MEDIA_PROJECTION_REQUEST_CODE
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == MEDIA_PROJECTION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                menuSwitch0 = false
                switch0.isChecked = true
                invalidateOptionsMenu()
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#EC7357")))
                Toast.makeText(
                        this,
                        R.string.onActivityResult1,
                        Toast.LENGTH_SHORT
                ).show()

                val audioCaptureIntent = Intent(this, AudioCaptureService::class.java).apply {
                    action = AudioCaptureService.ACTION_START
                    putExtra(AudioCaptureService.EXTRA_RESULT_DATA, resultData!!)
                }
                startForegroundService(audioCaptureIntent)
            } else {
                Toast.makeText(
                        this,
                        R.string.onActivityResult2,
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {

            READ_REQUEST_CODE2 -> {

                resultData?.data?.also { uri ->
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        val item = "/storage/emulated/0/" + split[1]
                        when {
                            radioButton.isChecked -> {
                                sound1 = soundPool.load(item, 1)
                                textView.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton2.isChecked -> {
                                sound2 = soundPool.load(item, 1)
                                textView2.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton3.isChecked -> {
                                sound3 = soundPool.load(item, 1)
                                textView3.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton4.isChecked -> {
                                sound4 = soundPool.load(item, 1)
                                textView4.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton5.isChecked -> {
                                sound5 = soundPool.load(item, 1)
                                textView5.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton6.isChecked -> {
                                sound6 = soundPool.load(item, 1)
                                textView6.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton7.isChecked -> {
                                sound7 = soundPool.load(item, 1)
                                textView7.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton8.isChecked -> {
                                sound8 = soundPool.load(item, 1)
                                textView8.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton9.isChecked -> {
                                sound9 = soundPool.load(item, 1)
                                textView9.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton10.isChecked -> {
                                sound10 = soundPool.load(item, 1)
                                textView10.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton11.isChecked -> {
                                sound11 = soundPool.load(item, 1)
                                textView11.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton12.isChecked -> {
                                sound12 = soundPool.load(item, 1)
                                textView12.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton13.isChecked -> {
                                sound13 = soundPool.load(item, 1)
                                textView13.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton14.isChecked -> {
                                sound14 = soundPool.load(item, 1)
                                textView14.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton15.isChecked -> {
                                sound15 = soundPool.load(item, 1)
                                textView15.text = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton16.isChecked -> {
                                lmp.release()
                                lmp = LoopMediaPlayer(this@MainActivity, Uri.parse(item))
                                supportActionBar?.title = item.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                            }
                            radioButton17.isChecked -> {
                                audioName = item
                                button4.performClick()
                            }
                        }
                    } else {
                        try {
                            val item2 = "/stroage/" + type + "/" + split[1]
                            when {
                                radioButton.isChecked -> {
                                    sound1 = soundPool.load(item2, 1)
                                    textView.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton2.isChecked -> {
                                    sound2 = soundPool.load(item2, 1)
                                    textView2.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton3.isChecked -> {
                                    sound3 = soundPool.load(item2, 1)
                                    textView3.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton4.isChecked -> {
                                    sound4 = soundPool.load(item2, 1)
                                    textView4.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton5.isChecked -> {
                                    sound5 = soundPool.load(item2, 1)
                                    textView5.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton6.isChecked -> {
                                    sound6 = soundPool.load(item2, 1)
                                    textView6.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton7.isChecked -> {
                                    sound7 = soundPool.load(item2, 1)
                                    textView7.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton8.isChecked -> {
                                    sound8 = soundPool.load(item2, 1)
                                    textView8.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton9.isChecked -> {
                                    sound9 = soundPool.load(item2, 1)
                                    textView9.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton10.isChecked -> {
                                    sound10 = soundPool.load(item2, 1)
                                    textView10.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton11.isChecked -> {
                                    sound11 = soundPool.load(item2, 1)
                                    textView11.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton12.isChecked -> {
                                    sound12 = soundPool.load(item2, 1)
                                    textView12.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton13.isChecked -> {
                                    sound13 = soundPool.load(item2, 1)
                                    textView13.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton14.isChecked -> {
                                    sound14 = soundPool.load(item2, 1)
                                    textView14.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton15.isChecked -> {
                                    sound15 = soundPool.load(item2, 1)
                                    textView15.text = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton16.isChecked -> {
                                    lmp.release()
                                    lmp = LoopMediaPlayer(this@MainActivity, Uri.parse(item2))
                                    supportActionBar?.title = item2.replaceBeforeLast("/", "").replace("/", "").replaceAfterLast(".", "").replace(".", "")
                                }
                                radioButton17.isChecked -> {
                                    audioName = item2
                                    button4.performClick()
                                }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(applicationContext, R.string.READ_REQUEST_CODE2, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun selectCh() {

        val chSpinner = findViewById<Spinner>(R.id.choose_loop_spinner)

        val adapterC = ArrayAdapter.createFromResource(this, R.array.spinnerItems2, android.R.layout.simple_spinner_item)

        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        chSpinner.adapter = adapterC

        chSpinner.performClick()


        chSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?, position: Int, id: Long
            ) {
                if (!chSpinner.isFocusable) {
                    chSpinner.isFocusable = true
                    return
                }

                val soundListView = findViewById<ListView>(R.id.list_view)

                when(position){
                    0 -> {
                        lmp.stop()
                        menuSwitch = true
                        invalidateOptionsMenu()
                        switch1.isChecked = false
                        radioButton16.performClick()
                        radioButton19.performClick()
                        soundListView.adapter = nCustomAdapter
                        nCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    1 -> {
                        lmp.stop()
                        menuSwitch = true
                        invalidateOptionsMenu()
                        switch1.isChecked = false
                        radioButton16.performClick()
                        radioButton19.performClick()
                        soundListView.adapter = oCustomAdapter
                        oCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    2 -> {
                        lmp.stop()
                        menuSwitch = true
                        invalidateOptionsMenu()
                        switch1.isChecked = false
                        radioButton16.performClick()
                        radioButton19.performClick()
                        soundListView.adapter = pCustomAdapter
                        pCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    3 -> {
                        lmp.stop()
                        menuSwitch = true
                        invalidateOptionsMenu()
                        switch1.isChecked = false
                        radioButton16.performClick()
                        radioButton19.performClick()
                        soundListView.adapter = qCustomAdapter
                        qCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    4 -> {
                        lmp.stop()
                        menuSwitch = true
                        invalidateOptionsMenu()
                        switch1.isChecked = false
                        radioButton16.performClick()
                        radioButton18.performClick()
                        soundListView.adapter = sCustomAdapter
                        sCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    5 -> {
                        lmp.stop()
                        menuSwitch = true
                        invalidateOptionsMenu()
                        switch1.isChecked = false
                        radioButton16.performClick()
                        radioButton18.performClick()
                        selectEX()
                        soundListView.adapter = tCustomAdapter
                        tCustomAdapter.notifyDataSetChanged()
                        soundListView.visibility = View.VISIBLE
                    }
                    6 -> {
                        radioButton16.performClick()
                        selectAudio()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        chSpinner.isFocusable = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val menuLamp = menu!!.findItem(R.id.menu1)
        if (menuSwitch) {
            menuLamp.setIcon(R.drawable.ic_baseline_play_arrow_24)
        } else {
            menuLamp.setIcon(R.drawable.ic_baseline_stop_24)
        }

        val menuLamp3 = menu.findItem(R.id.menu8)
        if (menuSwitch0) {
            menuLamp3.setIcon(R.drawable.ic_baseline_radio_button_checked_24_2)
        } else {
            menuLamp3.setIcon(R.drawable.ic_baseline_radio_button_checked_24)
        }

        return true
    }

    private var menuSwitch = true
    private var menuSwitch2 = true
    private var menuSwitch0 = true
    private var mediaRecorder = MediaRecorder()

    private val locale: Locale = Locale.getDefault()


    @SuppressLint("SimpleDateFormat")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val timeStamp: String = SimpleDateFormat("MM月dd日HH時mm分ss秒").format(Date())
        val soundFilePathJA = this.getExternalFilesDir(Environment.DIRECTORY_MUSIC).toString() + "/$timeStamp" + "の録音.ogg"

        val timestamp2: String = SimpleDateFormat("dd-MM-yyyy-hh-mm-ss", Locale.US).format(Date())
        val soundFilePathEN = this.getExternalFilesDir(Environment.DIRECTORY_MUSIC).toString() + "/Record-$timestamp2.ogg"

        fun startRecording() {
            try {
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
                if (locale == Locale.JAPAN) {
                    mediaRecorder.setOutputFile(soundFilePathJA)
                } else {
                    mediaRecorder.setOutputFile(soundFilePathEN)
                }
                mediaRecorder.setMaxDuration(180000)
                mediaRecorder.setOnInfoListener { _, what, _ ->
                    if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                        mediaRecorder.stop()
                        menuSwitch2 = true
                        invalidateOptionsMenu()
                        switch2.isChecked = false
                        Toast.makeText(applicationContext, R.string.startRecording1, Toast.LENGTH_LONG).show()
                    }
                }
                mediaRecorder.prepare()
                mediaRecorder.start()
                Toast.makeText(applicationContext, R.string.startRecording2, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, R.string.startRecording3, Toast.LENGTH_LONG).show()

            }
        }

        fun stopRecording() {
            try {
                mediaRecorder.stop()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, R.string.stopRecording, Toast.LENGTH_LONG).show()
            }
        }

            button4.setOnClickListener {
                val myDir = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString() + "/showwavespics.png"
                FFmpeg.execute("-i $audioName -filter_complex showwavespic=s=2560x1280:colors=blue:scale=0 -y $myDir")

                val builder = AlertDialog.Builder(this)
                val inflater = layoutInflater
                val dialogView = inflater.inflate(R.layout.custom_dialog, null)

                mp.release()
                mp = MediaPlayer()
                mp.setDataSource(this, Uri.parse(audioName))
                mp.prepare()

                val seekBar = dialogView.findViewById<SeekBar>(R.id.seekBar)
                val seekBar2 = dialogView.findViewById<SeekBar>(R.id.seekBar2)

                seekBar.progress = 0
                seekBar2.progress = 0

                seekBar.max = mp.duration

                seekBar2.max = mp.duration
                seekBar2.progress = mp.duration

                start = 0
                stop = mp.duration

                val text1 = dialogView.findViewById<TextView>(R.id.textView16)
                val text2 = dialogView.findViewById<TextView>(R.id.textView17)
                val text3 = dialogView.findViewById<TextView>(R.id.textView18)

                text1.text = SimpleDateFormat("mm:ss.SSS").format(Date(0.toLong())).toString()
                text2.text = SimpleDateFormat("mm:ss.SSS").format(Date(mp.duration.toLong())).toString()
                text3.text = audioName.replaceBeforeLast("/", "").replace("/", "")

                    seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {


                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                            text1.text = SimpleDateFormat("mm:ss.SSS").format(Date(progress.toLong()))
                                start = progress

                        }


                        override fun onStartTrackingTouch(seekBar: SeekBar?) {

                        }


                        override fun onStopTrackingTouch(seekBar: SeekBar?) {

                        }
                    })

                    seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {


                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                            text2.text = SimpleDateFormat("mm:ss.SSS").format(Date(progress.toLong()))
                                stop = progress
                        }


                        override fun onStartTrackingTouch(seekBar: SeekBar?) {

                        }


                        override fun onStopTrackingTouch(seekBar: SeekBar?) {

                        }
                    })


                    val image = dialogView.findViewById<View>(R.id.imageView16) as ImageView

                    image.setImageURI(Uri.parse(myDir))

                    val button = dialogView.findViewById(R.id.button) as Button
                    val button2 = dialogView.findViewById(R.id.button2) as Button
                    val button3 = dialogView.findViewById(R.id.button3) as Button

                    button.setOnClickListener {
                            when {
                                    start < stop -> {
                                            val builder2 = AlertDialog.Builder(this)
                                            val inflater2 = layoutInflater
                                            val dialogView2 = inflater2.inflate(R.layout.file_name, null)
                                            builder2.setView(dialogView2)
                                                    .setTitle(R.string.button_setOnClickListener1)
                                                    .setPositiveButton(R.string.button_setOnClickListener2) { _, _ ->
                                                            val nt = dialogView2.findViewById<EditText>(R.id.filename)
                                                            val fnt = this.getExternalFilesDir(Environment.DIRECTORY_MUSIC).toString() + "/" + nt.text.replace("/".toRegex(), "") + audioName.replaceBeforeLast(".", "")
                                                            try {
                                                                FFmpeg.execute("-ss ${text1.text} -to ${text2.text} -i $audioName -y $fnt")
                                                                button3.performClick()
                                                                Toast.makeText(applicationContext, R.string.button_setOnClickListener3, Toast.LENGTH_LONG).show()
                                                            } catch (e: Exception) {
                                                                Toast.makeText(applicationContext, R.string.button_setOnClickListener4, Toast.LENGTH_LONG).show()
                                                            }
                                                    }
                                                    .setNegativeButton(R.string.button_setOnClickListener5) { _, _ ->

                                                    }
                                                    .show()

                                    }
                                    start > stop -> Toast.makeText(applicationContext, R.string.button_setOnClickListener6, Toast.LENGTH_LONG).show()
                                    start == stop -> Toast.makeText(applicationContext, R.string.button_setOnClickListener6, Toast.LENGTH_LONG).show()
                            }
                    }

                    button2.setOnClickListener {
                        if (switch3.isChecked) {
                            mp.stop()
                            mp.prepare()
                            handler.removeCallbacksAndMessages(null)
                            switch3.isChecked = false
                        } else {
                            mp.seekTo(start)
                            mp.start()
                            switch3.isChecked = true
                            if (mp.isPlaying)
                                handler.postDelayed({
                                    mp.stop()
                                    mp.prepare()
                                    switch3.isChecked = false
                                }, (stop - start).toLong())
                        }
                    }



                    builder.setView(dialogView)
                            .setOnCancelListener {
                                mp.stop()
                                mp.prepare()
                                switch3.isChecked = false
                            }
                            val dialog = builder.create()
                            dialog.show()

                button3.setOnClickListener {
                    dialog.cancel()
                }

            }

        val soundListView = findViewById<ListView>(R.id.list_view)

        when (item.itemId) {

            R.id.menu1 -> {
                if (switch1.isChecked) {
                    lmp.stop()
                    soundPool.autoPause()
                    menuSwitch = true
                    invalidateOptionsMenu()
                    switch1.isChecked = false
                } else {
                    lmp.start()
                    menuSwitch = false
                    invalidateOptionsMenu()
                    switch1.isChecked = true
                }
                return true
            }

            R.id.menu1a -> {
                radioButton17.performClick()
                selectAudio()
                return true
            }

            R.id.menu6 -> {
                AlertDialog.Builder(this)
                        .setTitle(R.string.menu6)
                        .setPositiveButton("YES") { _, _ ->
                            finish()
                        }
                        .setNegativeButton("NO") { _, _ ->

                        }
                        .show()

                return true
            }

            R.id.menu8 -> {
                when {

                    Build.VERSION.SDK_INT < Build.VERSION_CODES.Q -> Toast.makeText(applicationContext, R.string.menu8, Toast.LENGTH_LONG).show()

                    switch0.isChecked -> stopCapturing()

                    else -> startCapturing()

                }

                return true
            }

            R.id.menu9 -> {
                radioButton17.performClick()
                radioButton18.performClick()
                selectEX()
                soundListView.adapter = tCustomAdapter
                tCustomAdapter.notifyDataSetChanged()
                soundListView.visibility = View.VISIBLE
                return true
            }

            R.id.menu9b -> {
                if (!isRecordAudioPermissionGranted()) {
                    requestRecordAudioPermission()
                } else {
                    if (switch0.isChecked) {
                        stopCapturing()
                    }
                    val builder3 = AlertDialog.Builder(this)
                    val inflater3 = layoutInflater
                    val dialogView3 = inflater3.inflate(R.layout.record_dialog, null)
                    val rec = dialogView3.findViewById<ImageView>(R.id.imageView17)
                    rec.setImageResource(R.drawable.ic_baseline_mic_24)
                    rec.setOnClickListener {
                        if (switch2.isChecked) {
                            menuSwitch2 = true
                            stopRecording()
                            Toast.makeText(applicationContext, R.string.button_setOnClickListener3, Toast.LENGTH_LONG).show()
                            rec.setImageResource(R.drawable.ic_baseline_mic_24)
                            switch2.isChecked = false
                        } else {
                            menuSwitch2 = false
                            startRecording()
                            rec.setImageResource(R.drawable.ic_baseline_mic_24_2)
                            switch2.isChecked = true
                        }
                    }
                    builder3.setView(dialogView3)
                            .setTitle(R.string.builder3)
                            .setNegativeButton(R.string.back) { _, _ ->
                                if (switch2.isChecked) {
                                    menuSwitch2 = true
                                    stopRecording()
                                    Toast.makeText(applicationContext, R.string.button_setOnClickListener3, Toast.LENGTH_LONG).show()
                                    rec.setImageResource(R.drawable.ic_baseline_mic_24)
                                    switch2.isChecked = false
                                }
                            }
                            .setOnCancelListener {
                                if (switch2.isChecked) {
                                    menuSwitch2 = true
                                    stopRecording()
                                    Toast.makeText(applicationContext, R.string.button_setOnClickListener3, Toast.LENGTH_LONG).show()
                                    rec.setImageResource(R.drawable.ic_baseline_mic_24)
                                    switch2.isChecked = false
                                }
                            }
                    val dialog = builder3.create()
                    dialog.show()

                }
                return true
            }

            R.id.menu10 -> {
                selectCh()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
            lmp = LoopMediaPlayer.create(this, Uri.parse("android.resource://" + packageName + "/raw/" + R.raw.ta))
        lmp.reset()
        lmp.release()
        mp.reset()
        mp.release()
        soundPool.autoPause()
        soundPool.release()

        mediaRecorder.reset()
        mediaRecorder.release()
        super.onDestroy()
    }

    override fun onPause() {
        menuSwitch = true
        invalidateOptionsMenu()
        switch1.isChecked = false
        if (mp.isPlaying) {
            mp.stop()
            mp.prepare()
            switch3.isChecked = false
        }
        if (!menuSwitch2) {
            menuSwitch2 = true
            invalidateOptionsMenu()
            mediaRecorder.stop()
            Toast.makeText(applicationContext, R.string.button_setOnClickListener3, Toast.LENGTH_LONG).show()
            switch2.isChecked = false
        }

            lmp.stop()
            soundPool.autoPause()

        super.onPause()
    }
}
