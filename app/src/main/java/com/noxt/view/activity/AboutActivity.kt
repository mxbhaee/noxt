package com.noxt.view.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.transition.TransitionManager
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.noxt.BuildConfig
import com.noxt.R
import io.noties.markwon.Markwon

class AboutActivity : MaterialAboutActivity() {

    var popupWindow: PopupWindow? = null
    override fun getActivityTitle(): CharSequence? {
        return getString(R.string.about)
    }

    override fun getMaterialAboutList(context: Context): MaterialAboutList {

        val notedCard = MaterialAboutCard.Builder()
            .addItem(MaterialAboutTitleItem.Builder()
                .text("noxt")
                .icon(R.mipmap.ic_launcher_round)
                .build())
            .addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.version))
                .subText(BuildConfig.VERSION_NAME)
                .icon(R.drawable.ic_info_black)
                .build())
            .addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.changelog))
                .icon(R.drawable.ic_restore)
                .setOnClickAction {
                    showChangelogWindow()
                }
                .build())
            .addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.license))
                .icon(R.drawable.ic_class)
                .setOnClickAction {
                    startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://sites.google.com/view/noxt-application/home")))
                }
                .build())
            .build()

        val authorCard = MaterialAboutCard.Builder()
            .title(getString(R.string.author))
            .addItem(MaterialAboutActionItem.Builder()
                .text("Yahia Mostafa")
                .subText("@YahiaAngelo")
                .icon(R.drawable.ic_person)
                .setOnClickAction {
                    startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://github.com/YahiaAngelo")))
                }
                .build())
            .addItem(MaterialAboutActionItem.Builder()
                .text("bhae")
                .subText("@MrBhaee")
                .icon(R.drawable.ic_person)
                .setOnClickAction {
                    startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/MrBhaee")))
                }
                .build())
            .addItem(MaterialAboutActionItem.Builder()
                .text("Follow on Twitter")
                .icon(R.drawable.ic_logo_twitter)
                .setOnClickAction {
                    startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/MrBhaee")))
                }
                .build())
            .build()

        val appInfoCard = MaterialAboutCard.Builder()
            .title(getString(R.string.about))
            .addItem(MaterialAboutActionItem.Builder()
                .text("Version")
                .subText(BuildConfig.VERSION_NAME)
                .icon(R.drawable.ic_info_black)
                .build())
            .addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.contact_us))
                .icon(R.drawable.ic_email)
                .setOnClickAction {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:") // only email apps should handle this
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("nephilim148@gmail.com"))
                            putExtra(Intent.EXTRA_SUBJECT, "noxt not taking application.")
                        }
                        if (intent.resolveActivity(packageManager) != null) {
                            startActivity(intent)
                        }
                }
                .build())
            .build()

        return MaterialAboutList.Builder()
            .addCard(notedCard)
            .addCard(authorCard)
            .addCard(appInfoCard)
            .build()

    }

    private fun showChangelogWindow(){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.changelog_view,null)
        popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        ).apply {
            enterTransition = android.transition.Fade()
            exitTransition = android.transition.Fade()
            setBackgroundDrawable(ColorDrawable())
            isOutsideTouchable = true
            elevation = 10.0F
        }

        val markwon = Markwon.create(this)
        val changelogTextView = view.findViewById<TextView>(R.id.changelog_text)
        markwon.setMarkdown(changelogTextView, resources.getString(R.string.app_changelog))
        TransitionManager.beginDelayedTransition(this.recyclerView)
        popupWindow!!.showAtLocation(
            this.recyclerView, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )

    }

    override fun onBackPressed() {
        if (popupWindow != null){
            if (popupWindow!!.isShowing){
                popupWindow!!.dismiss()
            }else{
                super.onBackPressed()
            }
        }else{
            super.onBackPressed()
        }
    }
}