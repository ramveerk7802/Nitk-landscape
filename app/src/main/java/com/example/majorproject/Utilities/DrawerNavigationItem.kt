package com.example.majorproject.Utilities

import com.example.majorproject.R

sealed class DrawerNavigationItem(
    val title:String,
    val route:String,
    val icon:Int
) {
    object Home : DrawerNavigationItem(
        title = "Home",
        route = "home",
        icon = R.drawable.home
    )
    object Nitk_link: DrawerNavigationItem(
        title = "Visit the Nitk",
        route = "nitk_link",
        icon = R.drawable.link_icon
    )

    object Share : DrawerNavigationItem(
        title = "Share",
        route = "share",
        icon = R.drawable.share
    )

    object Privacy : DrawerNavigationItem(
        title = "Privacy & policy",
        route = "privacy&policy",
        icon = R.drawable.privacy
    )

    companion object{
        val list= listOf(Home,Nitk_link,Share,Privacy)
    }

}