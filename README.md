# throttler 
[![Build Status](https://github.com/evolution-gaming/throttler/workflows/CI/badge.svg)](https://github.com/evolution-gaming/throttler/actions?query=workflow%3ACI)
[![Coverage Status](https://coveralls.io/repos/github/evolution-gaming/throttler/badge.svg?branch=master)](https://coveralls.io/github/evolution-gaming/throttler?branch=master) 
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/fde6c3ebc6bc4c68971a060f97d420e1)](https://app.codacy.com/gh/evolution-gaming/throttler/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Version](https://img.shields.io/badge/version-click-blue)](https://evolution.jfrog.io/artifactory/api/search/latestVersion?g=com.evolutiongaming&a=throttler_2.13&repos=public)
[![License](https://img.shields.io/badge/License-Apache%202.0-yellowgreen.svg)](https://opensource.org/licenses/Apache-2.0)

### RequestThrottler

RequestThrottler allows to limit the maximum number of requests / messages which can be processed within a specific time period for a specific user / resource, etc.

### TokenBucket

TokenBucket is a request throttling implementation for limiting requests rate for a single resource, see <a href="http://en.wikipedia.org/wiki/Token_bucket">Token Bucket on Wikipedia</a>.
  
### FixedIntervalRefillStrategy  
  
FixedIntervalRefillStrategy is a token bucket refill strategy that will provide N tokens for a token bucket to consume every T milliseconds.


## Setup

```scala
addSbtPlugin("com.evolution" % "sbt-artifactory-plugin" % "0.0.2")

libraryDependencies += "com.evolutiongaming" %% "throttler" % "2.0.1"
```
