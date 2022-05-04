package com.example.di.module

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(FragmentComponent::class)
class GlideModule {

    @Provides
    fun provideGlide(@ActivityContext context: Context): RequestManager = Glide.with(context)
}
