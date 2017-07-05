/*
 * Copyright 2012-2014 Brandon Beck (https://github.com/bbeck/token-bucket)
 * Copyright 2016-2017 Evolution Gaming (https://github.com/evolution-gaming/throttler)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.evolutiongaming.util.throttler.tokenbucket

/**
  * A token bucket refill strategy that will provide N tokens for a token bucket to consume every T milliseconds.
  * The tokens are refilled in bursts rather than at a fixed rate. This refill strategy will never allow more than
  * N tokens to be consumed during a window of time T.
  *
  * @param numTokensPerPeriod The number of tokens to add to the bucket every period.
  * @param periodInMillis     How often to refill the bucket (in milliseconds).
  */
case class FixedIntervalRefillStrategy(
  numTokensPerPeriod: Long,
  periodInMillis: Long) {

  private val periodDurationInNanos = periodInMillis * 1000000

  private def nanoTime: Long = System.nanoTime()

  @volatile private var lastRefillTime: Long = nanoTime
  @volatile private var nextRefillTime: Long = nanoTime

  /**
    * Returns the number of tokens to add to the token bucket.
    *
    * @return The number of tokens to add to the token bucket.
    */
  def refill(): Long = {
    val now = nanoTime
    if (now < nextRefillTime) {
      0
    } else {
      this synchronized {
        val numPeriods = Math.max(0, (now - lastRefillTime) / periodDurationInNanos)
        lastRefillTime += numPeriods * periodDurationInNanos
        nextRefillTime = lastRefillTime + periodDurationInNanos
        numPeriods * numTokensPerPeriod
      }
    }
  }
}

