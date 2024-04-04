package org.clkrw.mobile.domain.bus

import kotlinx.coroutines.flow.Flow
import org.clkrw.mobile.domain.model.ClickerSseEvent

interface ShowSseBus {
    fun getClickerEvents(showId: String): Flow<ClickerSseEvent>
}