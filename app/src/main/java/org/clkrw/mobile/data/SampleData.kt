package org.clkrw.mobile.data

import org.clkrw.mobile.domain.model.Presentation
import org.clkrw.mobile.domain.model.Showing

val presentations = listOf(
    Presentation(0, "Kek", 13),
    Presentation(1, "Lol", 228),
    Presentation(2, "I want to sleep", 420),
    Presentation(3, "Help me", 420),
    Presentation(4, "Please", 420),
    Presentation(5, "No", 420),
)

val showings = mapOf(
    0 to Showing(0, 0, 1),
    1 to Showing(1, 0, 2),
    2 to Showing(2, 0, 3),
    3 to Showing(3, 0, 4),
    4 to Showing(4, 0, 4),
    5 to Showing(5, 0, 1),
)