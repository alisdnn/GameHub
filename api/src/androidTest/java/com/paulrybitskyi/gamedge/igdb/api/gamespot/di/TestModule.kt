package com.paulrybitskyi.gamedge.igdb.api.gamespot.di

import com.paulrybitskyi.gamedge.common.testing.di.MocksModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [MocksModule::class])
@InstallIn(SingletonComponent::class)
internal object TestModule
