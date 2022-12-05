package com.paulrybitskyi.gamedge.igdb.api.gamespot.di

import com.paulrybitskyi.gamedge.igdb.api.gamespot.utils.FakeGamespotConstantsProvider
import com.paulrybitskyi.gamedge.igdb.gamespot.common.GamespotConstantsProvider
import com.paulrybitskyi.gamedge.igdb.gamespot.common.di.GamespotConstantsModule
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [GamespotConstantsModule::class]
)
internal interface FakeGamespotConstantsModule {

    @Binds
    fun bindGamespotConstantsProvider(binding: FakeGamespotConstantsProvider): GamespotConstantsProvider
}
