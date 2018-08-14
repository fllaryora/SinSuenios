package rock.sinsuenios.presentation.lyrics

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.arch.lifecycle.Observer
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.Window
import android.view.animation.AccelerateInterpolator
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import rock.sinsuenios.R
import rock.sinsuenios.data.database.mapper.TrackMapper
import rock.sinsuenios.data.exception.Failure
import rock.sinsuenios.data.music.MusicBackground
import rock.sinsuenios.presentation.lyrics.show_lyric.view.LyricView
import rock.sinsuenios.presentation.lyrics.show_lyric.viewmodel.LyricViewModel
import rock.sinsuenios.presentation.lyrics.show_lyric.viewmodel.LyricViewModelFactory


@SuppressLint("ClickableViewAccessibility")
//mute setOnTouchListener
class LyricsActivity : AppCompatActivity() {

    private lateinit var viewModel: LyricViewModel
    private lateinit var rootLayout: View

    private lateinit var playPauseBtn: ImageButton
    private lateinit var previousBtn: ImageButton
    private lateinit var repeatBtn: ImageButton

    private var revealX: Int = 0
    private var revealY: Int = 0

    private lateinit var seekBar: SeekBar

    private var songProgressCurrent: TextView? = null //song_progress_current
    private var songProgressMax: TextView? = null //song_progress_max

    private var amoungToupdate : Long = 0L
    private lateinit var mRunnable:Runnable
    private lateinit var mHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        mHandler = Handler()
        setContentView(R.layout.activity_lyrics)
        rootLayout = findViewById<View>(R.id.root_layout)
        val lyric = findViewById<TextView>(R.id.text_lyric_name)
        val title = findViewById<TextView>(R.id.song_info_title)
        val typeface = Typeface.createFromAsset(assets, getString(R.string.lyric_typeface))
        lyric.typeface = typeface
        lyric.text = intent.getStringExtra(getString(R.string.EXTRA_TRACK_LYRIC))
        title.text = intent.getStringExtra(getString(R.string.EXTRA_TRACK_TITLE))

        playPauseBtn = findViewById<ImageButton>(R.id.play_pause_btn)
        previousBtn = findViewById<ImageButton>(R.id.previous_btn)
        repeatBtn = findViewById<ImageButton>(R.id.repeat_btn)

        seekBar = findViewById<SeekBar>(R.id.song_progressbar)

        if (isPortrait()) {
            songProgressCurrent = findViewById<TextView>(R.id.song_progress_current)
            songProgressMax = findViewById<TextView>(R.id.song_progress_max)
        }

        playPauseBtn.setOnClickListener { view: View? -> playPauseBtnClick(view) }
        previousBtn.setOnClickListener { view: View? -> goToBeginBtnClick(view) }
        repeatBtn.setOnClickListener { view: View? -> loopBtnClick(view) }

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(getString(R.string.EXTRA_CIRCULAR_REVEAL_X)) &&
                intent.hasExtra(getString(R.string.EXTRA_CIRCULAR_REVEAL_Y))) {
            rootLayout.visibility = View.INVISIBLE

            revealX = intent.getIntExtra(getString(R.string.EXTRA_CIRCULAR_REVEAL_X), 0)
            revealY = intent.getIntExtra(getString(R.string.EXTRA_CIRCULAR_REVEAL_Y), 0)

            val viewTreeObserver: ViewTreeObserver = rootLayout.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        revealActivity(revealX, revealY)
                        rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        } else {
            rootLayout.visibility = View.VISIBLE
        }

        val musicResource: Int = TrackMapper.Companion.getTrackResourceFrom(intent.getIntExtra(
                getString(R.string.EXTRA_TRACK_RESOURCE_MUSIC_IN_RAW), 0))!!.trackResource

        /**init the viewModel***/
        val musicBackground = MusicBackground(this, musicResource)
        val lyricFactory: LyricViewModelFactory = LyricViewModelFactory(musicBackground)
        viewModel = ViewModelProviders.of(this, lyricFactory).get(LyricViewModel::class.java)
        viewModel.failure.observe(this, Observer(this::handleFailure))
        viewModel.lyric.observe(this, Observer(this::renderChangesInLyric))

        seekBar.max = 100
        amoungToupdate = 500L
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // called when progress is changed
                if (fromUser){
                    viewModel.seekTo(progress, seekBar.max)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // called when tracking the seekbar is started
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // called when tracking the seekbar is stopped
            }
        })

        mRunnable = Runnable {
            //run()
            //query  media Player & update by handler
            viewModel.updateProgress(seekBar.max)
            // Schedule the task to repeat after 1 second
            mHandler.postDelayed(
                    mRunnable, // Runnable
                    amoungToupdate // Delay in milliseconds
            )
        }
    }

    private fun playPauseBtnClick(view: View?) {
        viewModel.playPause()
        mHandler.postDelayed(
                    mRunnable, // Runnable
                    0 // Delay in milliseconds
            )
    }

    private fun goToBeginBtnClick(view: View?) {
        viewModel.seekTo(0, seekBar.max)
        seekBar.progress = 0
    }

    private fun loopBtnClick(view: View?) {
        viewModel.setLoop()
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

    private fun isPortrait(): Boolean {
        return ORIENTATION_PORTRAIT == resources.configuration.orientation
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacks(mRunnable)
        viewModel.onPause()
    }

    override fun onResume() {
        super.onResume()
        mHandler.postDelayed(
                mRunnable, // Runnable
                0 // Delay in milliseconds
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)
        viewModel.onDestroy()

    }

    /******/

    private fun renderChangesInLyric(lyricView: LyricView?) {
        //TODO refresco todos los cambios en la pantalla
        lyricView?.let{
            seekBar.progress = it.progress
            if (isPortrait()) {
                songProgressCurrent?.text = it.currentPosText
                var remainingTime :String = it.remainingTimeText
                if("00:00" != remainingTime){
                    remainingTime = "-$remainingTime"
                }
                songProgressMax?.text = remainingTime
            }

            var imageResource : Int = 0
            if (!it.isPlaying){
                imageResource = R.drawable.play_btn
                mHandler.removeCallbacks(mRunnable)
            }else{
                imageResource = R.drawable.pause_btn
            }
            playPauseBtn.setImageResource(imageResource)

            imageResource = if (it.isLooping) R.drawable.repeat_pressed_btn else R.drawable.repeat_btn
            repeatBtn.setImageResource(imageResource)

        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            //is Failure.ListNotAvailable -> renderFailure(R.string.failure_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        //movieList.invisible()
        //emptyView.visible()
        //hideProgress()
        //notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }

}