package fmv1992.mypackage

import org.scalatest.funsuite.AnyFunSuite

class TestMain extends AnyFunSuite {
  test("Test.") {
    assert(Main.core === 1)
    assert(Main.coreStream === Stream(1, 2))
  }
}
