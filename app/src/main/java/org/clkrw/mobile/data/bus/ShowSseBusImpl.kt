package org.clkrw.mobile.data.bus

import kotlinx.coroutines.flow.Flow
import org.clkrw.mobile.domain.bus.ShowSseBus
import org.clkrw.mobile.domain.model.ClickerSseEvent
import org.clkrw.mobile.util.SseClient

class ShowSseBusImpl(
    private val sseClient: SseClient,
) : ShowSseBus {
    override fun getClickerEvents(showId: String): Flow<ClickerSseEvent> =
        sseClient.getEventsFlow<ClickerSseEvent>("/show/${showId}/bus?silent=True", EVENT_TYPE)

    companion object {
        const val EVENT_TYPE = "clickerState"
    }
}