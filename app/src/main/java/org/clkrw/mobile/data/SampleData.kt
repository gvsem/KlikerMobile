package org.clkrw.mobile.data

import org.clkrw.mobile.domain.model.Presentation
import org.clkrw.mobile.domain.model.Showing

val presentations = listOf(
    Presentation(0, 0, "Kek", "Author 1", "10.10.2020", "https://clkr.me/111", 13),
    Presentation(1, 0, "Lol", "Author 2", "10.10.2020", "https://clkr.me/111", 228),
    Presentation(2, 0, "I want to sleep", "Author 1", "10.10.2020", "https://clkr.me/111", 420),
    Presentation(3, 0, "Help me", "Author 1", "10.10.2020", "https://clkr.me/111", 420),
    Presentation(4, 0, "Please", "Author 4", "10.10.2020", "https://clkr.me/111", 420),
    Presentation(5, 0, "No", "One more author", "10.10.2020", "https://clkr.me/111", 420),
)

val showings = mapOf(
    0 to Showing(0, 0, 1),
    1 to Showing(1, 0, 2),
    2 to Showing(2, 0, 3),
    3 to Showing(3, 0, 4),
    4 to Showing(4, 0, 4),
    5 to Showing(5, 0, 1),
)