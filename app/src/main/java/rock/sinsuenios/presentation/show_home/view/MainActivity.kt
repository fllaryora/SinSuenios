package rock.sinsuenios.presentation.show_home.view

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.TextView
import android.graphics.Typeface
import android.graphics.Shader
import android.graphics.LinearGradient
import rock.sinsuenios.R
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import android.support.v4.app.Fragment
import android.view.View
import rock.sinsuenios.presentation.show_dashboard.DashboardFragment
import rock.sinsuenios.presentation.show_notifications.view.NotificationFragment


class MainActivity : AppCompatActivity() {

    private var mBottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appName: TextView = findViewById(R.id.text_app_name) as TextView
        val typeface = Typeface.createFromAsset(assets, "title.ttf")
        appName.setTypeface(typeface)
        val shader = LinearGradient(
                0f, 0f, 0f, appName.getTextSize(),
                Color.RED, Color.YELLOW,
                Shader.TileMode.CLAMP
        )
        appName.getPaint().setShader(shader)


        setupBottomNavigation()
        if (savedInstanceState == null) {
            loadHomeFragment()
        }
    }

    private fun setupBottomNavigation() {
        mBottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView
        mBottomNavigationView!!.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when (item.getItemId()) {
                    R.id.navigation_home -> {
                        loadHomeFragment()
                        return true
                    }
                    R.id.navigation_dashboard -> {
                        loadDashboardFragment()
                        return true
                    }
                    R.id.navigation_notifications -> {
                        loadNotificationFragment()
                        return true
                    }
                }
                return false
            }
        })
    }

    private fun loadHomeFragment() {
        val fragment : Fragment = HomeFragment.newInstance()
        val fragmentTransaction = supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.content, fragment)
        fragmentTransaction?.commit()
    }

    private fun loadDashboardFragment() {
        val fragment : Fragment = DashboardFragment.newInstance()
        val fragmentTransaction = supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.content, fragment)
        fragmentTransaction?.commit()
    }

    private fun loadNotificationFragment() {
        val fragment : Fragment = NotificationFragment.newInstance()
        val fragmentTransaction = supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.content, fragment)
        fragmentTransaction?.commit()
    }
}
