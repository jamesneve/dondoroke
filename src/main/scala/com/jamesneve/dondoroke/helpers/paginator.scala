package com.jamesneve.dondoroke.helpers

import scala.language.higherKinds
import play.twirl.api.HtmlFormat._
import scala.reflect.ClassTag
import com.jamesneve.dondoroke.models._
import play.api.mvc.RequestHeader
import java.io.Serializable

@SerialVersionUID(104L)
class Paginator(val totalItems: Int, currentPageNumber: Int, request: RequestHeader, config: Option[Config] = None)
		extends Serializable {
	
	val fixedCurrentPageNumber = if(currentPageNumber <= 0) 1 else currentPageNumber
	val currentConfig = config match {
			case Some(a) => a
			case None => new Config
		}
	val currentPage = new Page(totalItems, fixedCurrentPageNumber, currentConfig, request)

	def renderPager: Appendable = GlobalConfig.paginator(currentPage)

	def renderCounter: Appendable = GlobalConfig.counter(currentPage)

	def getRange: (Int, Int) = {
		val drop = (fixedCurrentPageNumber - 1) * currentConfig.perPage
		val take = currentConfig.perPage
		(drop, take)
	}
}
