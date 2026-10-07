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

## Prerequisites

- JDK 21+
- Maven 3.9+
- Node.js 20+ and npm

The backend uses Lombok. Maven handles it automatically; in IntelliJ IDEA enable *Settings > Build, Execution, Deployment > Compiler > Annotation Processors > Enable annotation processing* (and install the Lombok plugin if your IDE version doesn't bundle it).

## Backend (`backend/`)

```bash
cd backend
mvn test             # domain unit tests + Spring tests (use case, REST layer)
mvn spring-boot:run  # starts on http://localhost:8080
```

API (contract first, see below):

- `GET /api/berlin-clock` - lamps for the current server time
- `GET /api/berlin-clock?time=13:17:01` - lamps for a given `HH:mm:ss` time
- Invalid input returns `400` as an RFC 7807 problem detail.

```json
{
  "seconds": "O",
  "fiveHours": "RROO",
  "oneHours": "RRRO",
  "fiveMinutes": "YYROOOOOOOO",
  "oneMinutes": "YYOO"
}
```

### The API contract (OpenAPI)

The contract is `backend/src/main/resources/openapi/berlin-clock.yaml`. It is the single source of truth:

- `mvn compile` (or any later Maven phase) runs the OpenAPI Generator in `generate-sources` and writes the `BerlinClockApi` interface to `backend/target/generated-sources/openapi`. It is not committed.
- `BerlinClockController` implements the generated interface, so the path and the optional `time` parameter cannot drift from the contract.
- The response is the handwritten `BerlinClockResponse` (own class, built from the domain `BerlinClockDisplay`). The contract's `BerlinClockResponse` and is mapped to it with `schemaMappings` in the pom, so the generator does not create a second copy. Because Java does not check that class against the YAML, `BerlinClockControllerTest` compares the whole JSON strictly: a field added to or missing from the class fails it.
- To change the API, edit the YAML first, then adapt the controller to whatever the compiler reports.
- **Swagger UI:** with the backend running, open <http://localhost:8080/swagger-ui.html>. It renders the contract file itself, which is also served at <http://localhost:8080/berlin-clock.yaml>.

Springdoc also publishes its own generated description at `/v3/api-docs`; the UI is pointed at the contract file instead. Do not set `springdoc.api-docs.enabled=false` to hide that endpoint: it switches off the Swagger UI too.

The `pattern:` rules in the contract are documentation only; `TimeOfDay` does the validation and produces the `400` messages.

### Backend architecture

Layers depend inwards only: `api`, `infrastructure` -> `application` -> `domain`.

```
com.berlinclock
├── domain/            framework-free, no Spring
│   ├── model/         value objects: TimeOfDay (validates, parses HH:mm:ss), Lamp, LampRow, BerlinClockDisplay (the lamps)
│   ├── rule/          LampRule + one small rule per row (seconds, five/one hours, five/one minutes)
│   └── service/       BerlinClock: asks each rule which lamps to light
├── application/       use case BerlinClockService (input port), CurrentTime (output port), BerlinClockServiceImpl
├── infrastructure/    SystemCurrentTime: adapter that implements CurrentTime with a java.time.Clock
├── api/               BerlinClockController (implements the generated BerlinClockApi), ApiExceptionHandler (400 problem details)
├── api/BerlinClockResponse   the JSON response, built from a BerlinClockDisplay
│   └── generated/     (build output) API interface generated from the OpenAPI contract
└── config/            Spring wiring: Clock bean, BerlinClock bean
```

- **Single responsibility:** each lamp row is its own class; `TimeOfDay` owns validation; the controller only translates HTTP.
- **Open/closed:** a changed or additional row is a new `LampRule`, no existing rule is edited.
- **Dependency inversion:** the use case depends on the `CurrentTime` port, not on `java.time.Clock`; `BerlinClock` depends on the `LampRule` interface.
- **Value objects:** `TimeOfDay`, `LampRow` and `BerlinClockDisplay` are immutable records; an invalid time cannot be constructed.

## Frontend (`frontend/`)

Start the backend first, then:

```bash
cd frontend
npm install
npm run dev     # http://localhost:5173, /api is proxied to localhost:8080
npm test        # Vitest + Testing Library
npm run build   # production build in dist/
```

The app polls the backend every second and also lets you enter a custom time to inspect.

```
src/
├── App.jsx                   composes the hook and the components (App.css: page layout only)
├── main.jsx
├── api/clockApi.js           HTTP call and error mapping
├── hooks/useBerlinClock.js   loading, 1 s polling and errors
├── config/clockRows.js       row definitions (label, lamp colours)
└── components/               one folder per component, with its CSS and test next to it
    ├── ClockFace/            composes the rows
    ├── LampRow/              one row of lamps; its variant sets the lamp size (CSS variables)
    ├── Lamp/
    ├── TimeForm/             custom time input and "Live" button
    └── ErrorMessage/         shows an error as an alert
```

## How it was built (TDD)

The git history is the story. The steps are in dependency order, from the inside (domain) to the outside (API, UI). Each behaviour is two commits: `test(red)` adds a failing test (it fails on purpose) and `feat(green)` is the simplest change that makes it pass. `refactor` commits change structure with the tests green.

1. **Setup:** the Spring Boot project and the kata rules as the spec.
2. **Domain, plain Java without Spring:** `Lamp` and `LampRow`, `TimeOfDay` (an invalid time cannot be constructed), one `LampRule` per row (seconds, five/one hours, five/one minutes), and `BerlinClock` which composes them.
3. **Application:** the `BerlinClockService` use case with the `CurrentTime` port, the `SystemCurrentTime` adapter, and the Spring wiring.
4. **API, contract first:** the OpenAPI contract, `BerlinClockResponse`, the generated interface, the controller, 400 problem details, and Swagger UI.
5. **Frontend, test-first:** the API client, `ClockFace` (then split into `LampRow` and `Lamp`, each owning its CSS), `TimeForm`, `useBerlinClock`, `ErrorMessage` and `App`. Every component lives in its own folder with its CSS and test.
6. **Docs:** this README, with how to build, run and test everything.

Domain value objects and rules are plain Java and are tested without Spring. The use case, adapter wiring and REST layer are tested with Spring's test support (`@SpringBootTest`, `@WebMvcTest`, `MockMvc`), which runs on the JUnit Platform underneath.
