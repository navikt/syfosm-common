[![Build status](https://github.com/navikt/syfosm-common/workflows/Publish%20artifacts/badge.svg)](https://github.com/navikt/syfosm-common/workflows/Publish%20artifacts/badge.svg)

# syfosm-common
Common library for team sykmeldings apps. 

## syfosm-common-diagnosis-codes
See https://github.com/navikt/syfodiagnosecodegeneratorjson

#### Requirements

* JDK 17

#### Build and run tests
To build locally and run the integration tests you can simply run 
``` bash
./gradlew clean build
``` 
or on windows
`gradlew.bat clean build`

### Upgrading the gradle wrapper
Find the newest version of gradle here: https://gradle.org/releases/ Then run this command:

``` bash
./gradlew wrapper --gradle-version $gradleVersjon
```

### Contact

This project is maintained by navikt/teamsykmelding

Questions and/or feature requests? Please create an [issue](https://github.com/navikt/syfosm-common/issues).

If you work in [@navikt](https://github.com/navikt) you can reach us at the Slack
channel [#team-sykmelding](https://nav-it.slack.com/archives/CMA3XV997).