# Berlin Clock kata

A Berlin Clock (Mengenlehreuhr) with a **Java 21 / Spring Boot 4.1** backend, built test-first, and a **React** frontend.

## Rules implemented

| Row | Lamps | Meaning |
|-----|-------|---------|
| Seconds | 1 yellow | on for even seconds, off for odd |
| Five hours | 4 red | one lamp per 5 hours |
| One hours | 4 red | one lamp per remaining hour |
| Five minutes | 11 | one lamp per 5 minutes, every 3rd is red, the rest yellow |
| One minutes | 4 yellow | one lamp per remaining minute |

Lamps are encoded as `O` (off), `Y` (yellow on), `R` (red on). `24:00:00` is accepted (all lamps on); any other `24:xx:xx` is rejected.

Example, `23:59:59` -> `O` / `RRRR` / `RRRO` / `YYRYYRYYRYY` / `YYYY`.

## How to read the history

Commits named `test(red)` add a failing test on purpose; the `feat(green)` commit right after makes it pass.
