/*
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
package com.evolutiongaming.util.throttler

import java.util.concurrent.TimeUnit

import com.evolutiongaming.util.throttler.tokenbucket.TokenBucket
import com.google.common.cache.{CacheBuilder, CacheLoader, LoadingCache}
import com.typesafe.scalalogging.LazyLogging

/**
  * RequestThrottler allows to limit the maximum number of requests / messages which can be processed
  * within a specific time period for a specific user / resource, etc.
  *
  * Uses [[com.google.common.cache.LoadingCache]] under the hood.
  *
  * @param rejectedMeter          A function which should be called on all rejects to meter their rate.
  * @param throttlingEnabled      A global setting which allows to enable / disable the {{{RequestThrottler}}}.
  * @param allowedRate            Allowed number of messages / requests which can be consumed per period.
  * @param throttlingPeriodMillis Period duration in milliseconds.
  * @param concurrencyLevel       See [[com.google.common.cache.CacheBuilder#concurrencyLevel]]
  * @param expirationMillis       See [[com.google.common.cache.CacheBuilder#expireAfterAccess]]
  * @param initialCapacity        See [[com.google.common.cache.CacheBuilder#initialCapacity]]
  */
class RequestThrottler(
  rejectedMeter: () => Unit,
  throttlingEnabled: => Boolean,
  allowedRate: => Long,
  throttlingPeriodMillis: => Long = 1000L,
  concurrencyLevel: => Int = 100,
  expirationMillis: => Long = 5 * 60 * 1000,
  initialCapacity: => Int = 3000) extends LazyLogging {

  private val cache: LoadingCache[String, TokenBucket] =
    CacheBuilder
      .newBuilder()
      .concurrencyLevel(concurrencyLevel)
      .expireAfterAccess(expirationMillis, TimeUnit.MILLISECONDS)
      .initialCapacity(initialCapacity)
      .build(new CacheLoader[String, TokenBucket] {
        override def load(key: String): TokenBucket = TokenBucket(allowedRate, throttlingPeriodMillis)
      })

  /**
    * Checks if a request for the specified {{{throttlingKey}}} is allowed at the moment.
    *
    * @param throttlingKey Meaningful key which identifies a single user (i.e. session id or connection id).
    * @param force         If {{{true}}}, throttling is enforced ignoring {{{throttlingEnabled}}} setting.
    * @return {{{true}}} if request is allowed, {{{false}}} otherwise.
    */
  def isRequestAllowed(throttlingKey: String, force: Boolean = false): Boolean =
    if (throttlingEnabled || force) {
      val throttler = cache get throttlingKey
      val requestAllowed = throttler.tryConsume()
      if (!requestAllowed) {
        logger warn s"Request from $throttlingKey was discarded, rate ${ throttler.ratePerPeriod } per ${ throttler.periodInMillis } ms"
        rejectedMeter()
      }
      requestAllowed
    } else true
}