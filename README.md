# throttler [![Build Status](https://travis-ci.org/evolution-gaming/throttler.svg)](https://travis-ci.org/evolution-gaming/throttler) [![Coverage Status](https://coveralls.io/repos/evolution-gaming/throttler/badge.svg)](https://coveralls.io/r/evolution-gaming/throttler) [ ![version](https://api.bintray.com/packages/evolutiongaming/maven/throttler/images/download.svg) ](https://bintray.com/evolutiongaming/maven/throttler/_latestVersion)

### RequestThrottler

RequestThrottler allows to limit the maximum number of requests / messages which can be processed within a specific time period for a specific user / resource, etc.

### TokenBucket

TokenBucket is a request throttling implementation for limiting requests rate for a single resource, see <a href="http://en.wikipedia.org/wiki/Token_bucket">Token Bucket on Wikipedia</a>.
  
### FixedIntervalRefillStrategy  
  
FixedIntervalRefillStrategy is a token bucket refill strategy that will provide N tokens for a token bucket to consume every T milliseconds.