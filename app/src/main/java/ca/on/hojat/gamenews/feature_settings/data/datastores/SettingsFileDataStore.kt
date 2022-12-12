package ca.on.hojat.gamenews.feature_settings.data.datastores

import androidx.datastore.core.DataStore
import ca.on.hojat.gamenews.feature_settings.domain.datastores.SettingsLocalDataStore
import ca.on.hojat.gamenews.feature_settings.domain.entities.Settings
import com.paulrybitskyi.hiltbinder.BindType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class SettingsFileDataStore @Inject constructor(
    private val protoDataStore: DataStore<ProtoSettings>,
    private val protoMapper: ProtoSettingsMapper,
) : SettingsLocalDataStore {

    override suspend fun saveSettings(settings: Settings) {
        protoDataStore.updateData {
            protoMapper.mapToProtoSettings(settings)
        }
    }

    override fun observeSettings(): Flow<Settings> {
        return protoDataStore.data
            .map(protoMapper::mapToDomainSettings)
    }
}
