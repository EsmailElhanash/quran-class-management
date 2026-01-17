package app.islammedia.halaqatalquran

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.islammedia.halaqatalquran.fragments.HalaqatViewFragment
import app.islammedia.halaqatalquran.fragments.StudentsViewFragment

class ViewPager2Slider(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    companion object {
        private const val NUMBER_OF_PAGES = 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 1) {
            StudentsViewFragment()
        } else HalaqatViewFragment()
    }

    override fun getItemCount(): Int {
        return NUMBER_OF_PAGES
    }
}