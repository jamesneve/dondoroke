package com.example

import org.scalatest._
import play.api.test._
import play.api.test.Helpers._
import org.scalatestplus.play._

import com.jamesneve.dondoroke.models._

class PageSpec extends WordSpec with MustMatchers {
  val page = new Page(52, 2, new Config, "http://app-liv.com/en/games/all/10008?cat=dog&page=2&test=rawr")

  "totalPages" must {
    "return total pages" in {
      page.totalPages mustBe 3
    }
  }

  "firstPage" must {
    "return false if this page is not the first page" in {
      page.firstPage mustBe false
    }

    "return true if this page is the first page" in {
      val page2 = new Page(52, 1, new Config, "http://app-liv.com/en/games/all/10008?cat=dog&page=2&test=rawr")
      page2.firstPage mustBe true
    }
  }

  "lastPage" must {
    "return true if the current page is the last page" in {
      val page2 = new Page(52, 3, new Config, "http://app-liv.com/en/games/all/10008?cat=dog&page=2&test=rawr")
      page2.lastPage mustBe true
    }

    "return false if this page is not the last page" in {
      page.lastPage mustBe false
    }
  }

  "forwardAbb" must {
    "return false if a forward abbreviation is not required" in {
      page.forwardAbb mustBe false
    }
  }

  "backwardAbb" must {
    "return false if a forward abbreviation is not required" in {
      page.backwardAbb mustBe false
    }
  }

  "buildUrl" must {
    "handle the case with a page" in {
      page.buildUrl(77) mustBe "http://app-liv.com/en/games/all/10008?cat=dog&page=77&test=rawr"
    }

    "handle the case with no page" in {
      val page2 = new Page(52, 2, new Config, "http://app-liv.com/en/games/all/10008")
      page2.buildUrl(99) mustBe "http://app-liv.com/en/games/all/10008?page=99"
    }

    "handle the case with no page but other URL options" in {
      val page2 = new Page(52, 2, new Config, "http://app-liv.com/en/games/all/10008?cat=dog&rawr=test")
      page2.buildUrl(123) mustBe "http://app-liv.com/en/games/all/10008?cat=dog&rawr=test&page=123"
    }
  }

  "from" must {
    "retrieve the first item number" in {
      page.from mustBe 26
    }
  }

  "to" must {
    "retrieve the last item number" in {
      page.to mustBe 50
    }
  }
}
