package com.example.inventoryapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

fun Fragment.navigate(directions: NavDirections) {
    val controller = findNavController()
    val currentDestination = (controller.currentDestination as? FragmentNavigator.Destination)?.className
        ?: (controller.currentDestination as? DialogFragmentNavigator.Destination)?.className
    if (currentDestination == this.javaClass.name) {
        controller.navigate(directions)
    }
}

fun String.showToast(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}

fun ImageView.loadImage(image:Bitmap){
    Glide.with(this.context)
        .load(image)
        .into(this)
}


//private fun showFullScreenBottomSheet(bottomSheet: FrameLayout) {
//    val layoutParams = bottomSheet.layoutParams
//    layoutParams.height = Resources.getSystem().displayMetrics.heightPixels
//    bottomSheet.layoutParams = layoutParams
//}
//
//private fun expandBottomSheet(bottomSheetBehavior: BottomSheetBehavior<FrameLayout>) {
//    bottomSheetBehavior.skipCollapsed = true
//    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//}