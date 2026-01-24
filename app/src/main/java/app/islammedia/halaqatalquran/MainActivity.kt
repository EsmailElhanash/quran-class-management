package app.islammedia.halaqatalquran

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.viewpager2.widget.ViewPager2
import app.islammedia.halaqatalquran.halaqa.HalaqaActivity
import app.islammedia.halaqatalquran.student.ui.StudentActivity
import app.islammedia.halaqatalquran.utils.Constants
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_new_halaqa_or_student_popup_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.addHalaqa) {
            addHalaqa()
        } else if (id == R.id.addStudent) {
            addStudent()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()
        setUpViewPager()


    }

    private fun setUpViewPager(){
        val viewPager = findViewById<ViewPager2>(R.id.st_hq_vp2)
        val viewPagerTabsLayout = findViewById<TabLayout>(R.id.vp2_tabs)

        viewPager.adapter =  ViewPager2Slider(this)
        TabLayoutMediator(viewPagerTabsLayout, viewPager
        ) { tab: TabLayout.Tab?, position: Int ->
            tab?.text = if (position == 0) "الحلقات" else if (position == 1) "الحُفَّاظ" else null
        }.attach()
    }

    private fun setUpToolbar() {
        val myMainToolbar = findViewById<Toolbar?>(R.id.mainToolbar)
        setSupportActionBar(myMainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun addHalaqa() {
        val i = Intent(applicationContext, HalaqaActivity::class.java)
        i.putExtra(Constants.MODE, Constants.ADD)
        startActivity(i)
    }

    private fun addStudent() {
        val i = Intent(applicationContext, StudentActivity::class.java)
        i.putExtra(Constants.MODE, Constants.ADD)
        startActivity(i)
    }




}