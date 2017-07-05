package com.evolutiongaming.util.throttler

import java.util.concurrent.Executors

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatest.time.SpanSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.{ExecutionContext, Future}

class RequestThrottlerSpec extends FlatSpec with Matchers with MockitoSugar with ScalaFutures with SpanSugar {

  "RequestThrottler" should "allow all requests if disabled" in new Scope(enabled = false) {
   
    val keys = List("session1", "session2", "session3")

    val failedList = for (key <- keys; _ <- 0L to rate * 2; if !(requestThrottler isRequestAllowed key)) yield key

    failedList shouldBe empty
  }

  it should  "cut requests above allowed fixed rate" in new Scope(period = 10000L) {
    
    val TestKeys = 10
    val Threads = 10

    implicit val ec = new ExecutionContext {
      val threadPool = Executors newFixedThreadPool Threads

      def execute(runnable: Runnable) = {
        threadPool submit runnable
      }

      def reportFailure(t: Throwable) = {}
    }

    val futures = for {_ <- 1 to TestKeys} yield {
      Future {
        val key = System.nanoTime().toString

        for {_ <- 1L to rate} {
          requestThrottler isRequestAllowed key shouldBe true
        }
        for {_ <- 1L to rate * 2} {
          requestThrottler isRequestAllowed key shouldBe false
        }

        Thread sleep period / 2

        for {_ <- 1L to rate * 2} {
          requestThrottler isRequestAllowed key shouldBe false
        }

        Thread sleep period / 2

        for {_ <- 1L to rate} {
          requestThrottler isRequestAllowed key shouldBe true
        }
        for {_ <- 1L to rate * 2} {
          requestThrottler isRequestAllowed key shouldBe false
        }
      }
    }

    // will fail the test in case of failed future(s)
    implicit val patienceConfig = PatienceConfig(timeout = 30.seconds, interval = 200.millis)
    (Future sequence futures).futureValue
  }
  
  private abstract class Scope(val enabled: Boolean = true, val rate: Long = 10L, val period: Long = 1000L) {
    val requestThrottler = new RequestThrottler(
      rejectedMeter = () => {},
      throttlingEnabled = () => enabled,
      allowedRate = () => rate,
      throttlingPeriodMillis = () => period)
  }
}
