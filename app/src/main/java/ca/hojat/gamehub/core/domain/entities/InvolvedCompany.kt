package ca.hojat.gamehub.core.domain.entities

import ca.hojat.gamehub.core.domain.entities.Company

data class InvolvedCompany(
    val company: Company,
    val isDeveloper: Boolean,
    val isPublisher: Boolean,
    val isPorter: Boolean,
    val isSupporting: Boolean,
)
