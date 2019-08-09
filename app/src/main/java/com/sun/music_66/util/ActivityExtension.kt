package com.sun.music_66.util

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sun.music_66.base.view.BaseFragment
import com.sun.music_66.constant.ScreenAnimation

object ActivityExtension {
    fun addFragment(
        fragmentManager: FragmentManager,fragment:BaseFragment, screenAnimation: ScreenAnimation,
        containerViewId: Int, isAddFragment: Boolean, isAddBackStack: Boolean
    ) {
        val currentFragment = fragmentManager.findFragmentByTag(fragment::class.java.name) as BaseFragment?
        val transaction = fragmentManager.beginTransaction()
        if (currentFragment != null) {
            if (currentFragment.isVisible) {
                return
            }
            setAnimation(transaction, screenAnimation)
            transaction.show(currentFragment).commit()
            return
        }
        setAnimation(transaction, screenAnimation)
        if (isAddFragment) {
            transaction.add(containerViewId, fragment, fragment::class.java.name)
        } else {
            transaction.replace(containerViewId, fragment, fragment::class.java.name)
        }
        if (isAddBackStack) {
            transaction.addToBackStack(fragment::class.java.name)
        }
        transaction.commit()
    }

    private fun setAnimation(transaction: FragmentTransaction, screenAnimation: ScreenAnimation) {
        transaction.setCustomAnimations(
            screenAnimation.enterToRight, screenAnimation.exitToRight,
            screenAnimation.enterToLeft, screenAnimation.exitToLeft
        )
    }
}
