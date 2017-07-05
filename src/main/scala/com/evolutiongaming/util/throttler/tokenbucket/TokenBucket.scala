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

import java.util.concurrent.atomic.AtomicLong

/**
  * A token bucket is used for rate limiting access to a portion of code.
  *
  * @see <a href="http://en.wikipedia.org/wiki/Token_bucket">Token Bucket on Wikipedia</a>
  *
  * @param ratePerPeriod  Allowed number of tokens which can be consumed per period.
  * @param periodInMillis Period duration in milliseconds.
  */
case class TokenBucket(
  ratePerPeriod: Long,
  periodInMillis: Long = 1000L) {

  private val size = new AtomicLong(ratePerPeriod)

  private val refillStrategy = FixedIntervalRefillStrategy(ratePerPeriod, periodInMillis)

  /**
    * Returns the current number of tokens in the bucket.  If the bucket is empty then this method will return 0.
    *
    * @return The current number of tokens in the bucket.
    */
  def currentSize: Long = {
    refill(refillStrategy.refill())
    size.get()
  }

  /**
    * Attempt to consume a specified number of tokens from the bucket.  If the tokens were consumed then {{{true}}}
    * is returned, otherwise {{{false}}} is returned.
    *
    * @param numTokens The number of tokens to consume from the bucket, must be a positive number.
    * @return {{{true}}} if the tokens were consumed, {{{false}}} otherwise.
    */
  def tryConsume(numTokens: Long = 1L): Boolean = {
    if (numTokens < 1L) throw new IllegalArgumentException("Number of tokens to consume must be > 1")

    refill(refillStrategy.refill())

    if (numTokens > size.get())
      false
    else {
      // as it's not synchronized here, it may cause very small amount of non-equal distribution between periods
      size getAndAdd -numTokens
      true
    }
  }

  /**
    * Refills the bucket with the specified number of tokens.  If the bucket is currently full or near capacity then
    * fewer than {{{numTokens}}} may be added.
    *
    * @param numTokens The number of tokens to add to the bucket.
    */
  def refill(numTokens: Long): Unit = {
    if (numTokens != 0L) {
      // as it's not synchronized here, it may cause very small amount of false positives
      if ((size addAndGet numTokens) > ratePerPeriod) size set ratePerPeriod
    }
  }
}
