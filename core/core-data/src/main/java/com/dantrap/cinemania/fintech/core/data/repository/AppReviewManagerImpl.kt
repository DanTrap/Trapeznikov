package com.dantrap.cinemania.fintech.core.data.repository

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.dantrap.cinemania.fintech.core.common.utils.SettingsConstants
import com.dantrap.cinemania.fintech.core.domain.repository.AppReviewManager

internal class AppReviewManagerImpl(private val context: Context) : AppReviewManager {

    override fun reviewApplication() {
        try {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=${SettingsConstants.APPLICATION_PACKAGE}")
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        } catch (e: ActivityNotFoundException) {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=${SettingsConstants.APPLICATION_PACKAGE}")
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        }
    }
}
