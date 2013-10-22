package com.jtm

import org.scalatest.{Matchers, WordSpec}

class TuplesSpec extends WordSpec with Matchers {

  import Tuples._

  "Tuples macros" should {
    "take tail of tuple" in {
      val tuple = (false, 1, "2", 3.0)

      tail(tuple) shouldEqual (1, "2", 3.0)
      tail(tail(tuple)) shouldEqual ("2", 3.0)
      tail(tail(tail(tuple))) shouldEqual Tuple1(3.0)
    }

    "take head of tuple" in {
      val tuple = (false, 1, "2", 3.0)

      head(tuple) shouldEqual false
      head(tail(tuple)) shouldEqual 1
      head(tail(tail(tuple))) shouldEqual "2"
      head(tail(tail(tail(tuple)))) shouldEqual 3.0
    }
  }
}
