package com.sorabh.truelysocial.data.di

import com.sorabh.truelysocial.data.retrofit.NodeApiClient
import com.sorabh.truelysocial.data.retrofit.NodeApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NodeApiModule {
    @Provides
    fun provideNodeApi(): NodeApiInterface = NodeApiClient.nodeApiInterface
}