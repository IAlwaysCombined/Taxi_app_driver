package com.example.taxi_app_driver.ui.`object`

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.taxi_app_driver.R
import com.example.taxi_app_driver.database.DRIVER
import com.example.taxi_app_driver.ui.fragment.driver.end_rides.EndRidesFragment
import com.example.taxi_app_driver.ui.fragment.driver.rides.ListRidesFragment
import com.example.taxi_app_driver.ui.fragment.driver.my_rides.MyRidesFragment
import com.example.taxi_app_driver.ui.fragment.driver.user.ProfileFragment
import com.example.taxi_app_driver.uitlities.*
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader

class AppDrawer(private var toolbar: Toolbar) {

    private lateinit var drawer: Drawer
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var header: AccountHeader
    private lateinit var currentProfile: ProfileDrawerItem

    //Create and init drawer
    fun create() {
        initLoader()
        createHeader()
        createDrawer()
        drawerLayout = drawer.drawerLayout
    }

    //Disable drawer
    fun disableDrawer() {
        drawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = false
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.setNavigationOnClickListener {
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }
    }

    //Enable drawer
    fun enableDrawer() {
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        drawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = true
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        toolbar.setNavigationOnClickListener {
            drawer.openDrawer()
        }
    }

    //Create drawer
    private fun createDrawer() {
        DrawerBuilder()
            .withActivity(APP_ACTIVITY)
            .withAccountHeader(header)
            .withSliderBackgroundColorRes(R.color.white)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName(APP_ACTIVITY.getString(R.string.profile_item_driver))
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(102)
                    .withIconTintingEnabled(true)
                    .withName("Мои заказы")
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(103)
                    .withIconTintingEnabled(true)
                    .withName("Оконченные заказы")
                    .withSelectable(false)
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    changeFragmentReplace(position)
                    return false
                }
            }).build().also { drawer = it }
    }

    private fun changeFragmentReplace(position: Int) {
        when (position) {
            1 -> APP_ACTIVITY.replaceFragment(ProfileFragment())
            2 -> APP_ACTIVITY.replaceFragment(MyRidesFragment())
            3 -> APP_ACTIVITY.replaceFragment(EndRidesFragment())
        }
    }

    //Create header
    private fun createHeader() {
        currentProfile = ProfileDrawerItem()
            .withName(DRIVER.name_driver)
            .withEmail(DRIVER.phone_number_driver)
            .withIcon(DRIVER.photo_driver)
            .withIdentifier(200)
        header = AccountHeaderBuilder()
            .withProfileImagesClickable(false)
            .withActivity(APP_ACTIVITY)
            .withSelectionListEnabledForSingleProfile(false)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                currentProfile
            ).build()
    }

    //Update header
    fun updateHeader(){
        currentProfile
            .withName(DRIVER.name_driver)
            .withEmail(DRIVER.phone_number_driver)
            .withIcon(DRIVER.photo_driver)
        header.updateProfile(currentProfile)

    }

    //Init loader for download image in header
    private fun initLoader() {
        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable) {
                imageView.downloadAndSetImage(uri.toString())
            }
        })
    }
}