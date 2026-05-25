package com.evolutiongaming.util.throttler

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.SpanSugar

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}

class RequestThrottlerSpec extends AnyFlatSpec with Matchers with ScalaFutures with SpanSugar {

  "RequestThrottler" should "allow all requests if disabled" in new Scope(enabled = false) {

    val keys = List("session1", "session2", "session3")

    val failedList = for {
      key <- keys
      _ <- 0L to rate * 2 if !requestThrottler.isAllowed(key)
    } yield key

    failedList shouldBe empty
  }

  it should "cut requests above allowed fixed rate" in new Scope(period = 10000L) {

    val TestKeys = 10
    val Threads = 10

    implicit val executor: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(Threads))

    val futures = for { _ <- 1 to TestKeys } yield {
      Future {
        val key = System.nanoTime().toString

        for { _ <- 1L to rate } {
          requestThrottler.isAllowed(key) shouldBe true
        }
        for { _ <- 1L to rate * 2 } {
          requestThrottler.isAllowed(key) shouldBe false
        }

        Thread.sleep(period / 2)

        for { _ <- 1L to rate * 2 } {
          requestThrottler.isAllowed(key) shouldBe false
        }

        Thread.sleep(period / 2)

        for { _ <- 1L to rate } {
          requestThrottler.isAllowed(key) shouldBe true
        }
        for { _ <- 1L to rate * 2 } {
          requestThrottler.isAllowed(key) shouldBe false
        }
      }
    }

    // will fail the test in case of failed future(s)
    implicit val patienceConfig: PatienceConfig = PatienceConfig(timeout = 30.seconds, interval = 200.millis)
    (Future sequence futures).futureValue
  }

  private abstract class Scope(val enabled: Boolean = true, val rate: Long = 10L, val period: Long = 1000L) {
    val requestThrottler = RequestThrottler(
      rejectedMeter = () => {},
      enabled = () => enabled,
      allowedRate = () => rate,
      throttlingPeriodMillis = () => period,
    )
  }
}
