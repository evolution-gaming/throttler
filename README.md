# throttler 
[![Build Status](https://github.com/evolution-gaming/throttler/workflows/CI/badge.svg)](https://github.com/evolution-gaming/throttler/actions?query=workflow%3ACI)
[![Coverage Status](https://coveralls.io/repos/github/evolution-gaming/throttler/badge.svg?branch=master)](https://coveralls.io/github/evolution-gaming/throttler?branch=master) 
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a5cfa2105c1f48bb91f87b40955b0a55)](https://www.codacy.com/app/evolution-gaming/throttler?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=evolution-gaming/throttler&amp;utm_campaign=Badge_Grade)
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