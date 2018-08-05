package rock.sinsuenios.presentation.lyrics

import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.animation.Animator
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import rock.sinsuenios.R
import android.os.Build
import android.view.*
import android.view.animation.AccelerateInterpolator


@SuppressLint("ClickableViewAccessibility")
//mute setOnTouchListener
class LyricsActivity: AppCompatActivity(){

    private lateinit var  rootLayout: View

    private lateinit var playPauseBtn : ImageButton
    private lateinit var previousBtn : ImageButton
    private lateinit var repeatBtn : ImageButton
/*
    private lateinit var playPauseRipple : RippleDrawable
    private lateinit var previousRipple : RippleDrawable
    private lateinit var repeatRipple : RippleDrawable
*/
    private  var  revealX: Int = 0
    private  var revealY: Int = 0

    private lateinit var seekBar: SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyrics)
        rootLayout = findViewById<View>(R.id.root_layout)
        //TODO GET FIELDS HERE
        val lyric = findViewById<TextView>(R.id.text_lyric_name)
        val title = findViewById<TextView>(R.id.song_info_title)
        val typeface = Typeface.createFromAsset(assets, getString(R.string.lyric_typeface))
        lyric.typeface = typeface
        lyric.text = intent.getStringExtra(getString(R.string.EXTRA_TRACK_LYRIC))
        title.text = intent.getStringExtra(getString(R.string.EXTRA_TRACK_TITLE))
        val musicResource : Int = intent.getIntExtra(
                getString(R.string.EXTRA_TRACK_RESOURCE_MUSIC_IN_RAW),0)

        playPauseBtn = findViewById<ImageButton>(R.id.play_pause_btn)
        previousBtn = findViewById<ImageButton>(R.id.previous_btn)
        repeatBtn = findViewById<ImageButton>(R.id.repeat_btn)

        seekBar = findViewById<SeekBar>(R.id.song_progressbar)
        //TODO CHANGE 10 from music theme
        seekBar.max = 10 //from fucking music getTotalTime
        playPauseBtn.setOnClickListener { view: View? ->  playPauseBtnClick(view) }
        previousBtn.setOnClickListener { view: View? ->  goToBeginBtnClick(view) }
        repeatBtn.setOnClickListener { view: View? ->  loopBtnClick(view) }


        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(getString(R.string.EXTRA_CIRCULAR_REVEAL_X)) &&
                intent.hasExtra(getString(R.string.EXTRA_CIRCULAR_REVEAL_Y)) ){
            rootLayout.visibility = View.INVISIBLE

            revealX = intent.getIntExtra(getString(R.string.EXTRA_CIRCULAR_REVEAL_X), 0)
            revealY = intent.getIntExtra(getString(R.string.EXTRA_CIRCULAR_REVEAL_Y), 0)

            val viewTreeObserver : ViewTreeObserver= rootLayout.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener( object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        revealActivity(revealX, revealY)
                        rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        } else {
            rootLayout.visibility = View.VISIBLE
        }

    }

    private fun playPauseBtnClick(view: View?){

    }
    private fun goToBeginBtnClick(view: View?){

    }
    private fun loopBtnClick(view: View?){

    }

    private fun revealActivity(revealX: Int, revealY: Int) {
        val finalRadius = (Math.max(rootLayout.width, rootLayout.height) * 1.1).toFloat()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimationUtils.createCircularReveal(rootLayout, revealX, revealY, 0f, finalRadius).apply {
                duration = 400L
                interpolator = AccelerateInterpolator()

                rootLayout.visibility = View.VISIBLE
                start()
            }
        }
    }

    override fun onBackPressed() {
        unRevealActivity()
    }

    private fun unRevealActivity() {
        val finalRadius = (Math.max(rootLayout.width, rootLayout.height) * 1.1).toFloat()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimationUtils.createCircularReveal(rootLayout, revealX, revealY, finalRadius, 0f).apply {
                duration = 400L
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        rootLayout.visibility = View.INVISIBLE
                        finish()
                    }
                })
                start()
            }
        } else {
            finish()
        }
    }

}