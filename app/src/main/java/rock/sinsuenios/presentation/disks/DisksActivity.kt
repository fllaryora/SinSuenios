package rock.sinsuenios.presentation.disks

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.transition.Fade
import android.transition.Slide
import android.view.Gravity
import android.view.MenuItem
import android.view.Window
import android.view.animation.LinearInterpolator
import android.widget.TextView
import rock.sinsuenios.R
import rock.sinsuenios.presentation.adapter.BottomNavigationAdapter
import rock.sinsuenios.presentation.disks.show_song.view.SongsFragment
import rock.sinsuenios.presentation.disks.show_stars.view.StarsFragment

class DisksActivity : AppCompatActivity() {

    private lateinit var mBottomNavigationView: BottomNavigationView
    private lateinit var mNavigationAdapter: BottomNavigationAdapter
    private lateinit var mViewPager: ViewPager
    private var mPrevMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disks)
        val title : String = intent.getStringExtra(getString(R.string.EXTRA_DISK_TITLE))
        val textDiskName: TextView = findViewById<TextView>(R.id.text_disk_name)
        textDiskName.text = title
        val typeface = Typeface.createFromAsset(assets, getString(R.string.cd_room_typeface))
        textDiskName.typeface = typeface
        val shader = LinearGradient(
                0f, 0f, 0f, textDiskName.textSize,
                Color.RED, Color.YELLOW,
                Shader.TileMode.CLAMP
        )
        textDiskName.paint.shader = shader
        setupBottomNavigation()
        setupWindowAnimations()
    }

    private fun setupBottomNavigation() {
        mNavigationAdapter = BottomNavigationAdapter(supportFragmentManager)
        mNavigationAdapter.addPage(StarsFragment.newInstance(), getString(R.string.title_stars))
        mNavigationAdapter.addPage(SongsFragment.newInstance(), getString(R.string.title_dashboard))
        mViewPager = findViewById(R.id.viewpager_songs) as ViewPager
        mBottomNavigationView = findViewById(R.id.disk_bottom_navigation) as BottomNavigationView
        mViewPager.adapter = mNavigationAdapter

        mBottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_stars -> {
                    mViewPager.currentItem = 0
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_songs -> {
                    mViewPager.currentItem = 1
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageSelected(position: Int) {
                if (mPrevMenuItem != null) {
                    mPrevMenuItem!!.isChecked = false
                } else {
                    mBottomNavigationView.menu.getItem(0).isChecked = false
                }
                mBottomNavigationView.menu.getItem(position).isChecked = true
                mPrevMenuItem = mBottomNavigationView.menu.getItem(position)
            }
            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // A (general activity) --> B ((this , detail activity))
            val slide = Slide(Gravity.RIGHT)
            slide.duration = 200
            slide.interpolator = LinearInterpolator()
            slide.excludeTarget(android.R.id.statusBarBackground, true);
            slide.excludeTarget(android.R.id.navigationBarBackground, true);
            window.enterTransition = slide

            // A (general activity) <-- B ((this, detail activity))
            window.returnTransition = slide
            window.setBackgroundDrawable(
                    ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary)))

        }
    }

}
