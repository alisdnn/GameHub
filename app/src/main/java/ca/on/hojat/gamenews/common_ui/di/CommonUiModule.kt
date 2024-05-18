package ca.on.hojat.gamenews.common_ui.di

import ca.on.hojat.gamenews.common_ui.TransitionAnimations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonUiModule {

    @Provides
    @Singleton
    @TransitionAnimationDuration
    fun provideTransitionAnimationDuration(): Long {
        return TransitionAnimations.DEFAULT_ANIMATION_DURATION.toLong()
    }
}
