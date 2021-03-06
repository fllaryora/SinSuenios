package rock.sinsuenios.presentation

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.transition.Fade
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import android.widget.TextView
import rock.sinsuenios.R
import rock.sinsuenios.presentation.adapter.BottomNavigationAdapter
import rock.sinsuenios.presentation.show_dashboard.view.DashboardFragment
import rock.sinsuenios.presentation.show_home.view.HomeFragment
import rock.sinsuenios.presentation.show_notifications.view.NotificationFragment
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    private lateinit var mBottomNavigationView: BottomNavigationView
    private lateinit var mNavigationAdapter: BottomNavigationAdapter
    private lateinit var mViewPager: ViewPager
    private var mPrevMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appName = findViewById<TextView>(R.id.text_app_name)
        val typeface = Typeface.createFromAsset(assets, getString(R.string.app_typeface))
        appName.typeface = typeface
        val shader = LinearGradient(
                0f, 0f, 0f, appName.textSize,
                Color.RED, Color.YELLOW,
                Shader.TileMode.CLAMP
        )
        appName.paint.shader = shader
        setupBottomNavigation()
        //animations for api >= 21
        setupWindowAnimations()
    }

    private fun setupBottomNavigation() {
        mNavigationAdapter = BottomNavigationAdapter(supportFragmentManager)
        mNavigationAdapter.addPage(HomeFragment.newInstance(), getString(R.string.title_home))
        mNavigationAdapter.addPage(DashboardFragment.newInstance(), getString(R.string.title_dashboard))
        mNavigationAdapter.addPage(NotificationFragment.newInstance(), getString(R.string.title_notifications))
        mViewPager = findViewById<ViewPager>(R.id.viewpager)
        mBottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        mViewPager.adapter = mNavigationAdapter

        mBottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    mViewPager.currentItem = 0
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    mViewPager.currentItem = 1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    mViewPager.currentItem = 2
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

            // A (this,general activity) --> B ((detail activity))
            val slide = Slide(Gravity.LEFT)
            slide.duration = 500
            window.exitTransition = slide

            // A (this,general activity) <-- B ((detail activity))
            val fade = Fade()
            fade.duration = 1000
            window.reenterTransition = fade

            //shared element exit transition
            // A (this,general activity) --> B ((detail activity))
            //window.sharedElementExitTransition =
        }
    }

}
