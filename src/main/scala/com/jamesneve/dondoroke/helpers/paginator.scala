package com.jamesneve.dondoroke.helpers

import scala.language.higherKinds
import play.twirl.api.HtmlFormat._
import scala.reflect.ClassTag
import com.jamesneve.dondoroke.models._
import java.io.Serializable

@SerialVersionUID(110L)
class Paginator(val totalItems: Int, currentPageNumber: Int, uri: String, config: Option[Config] = None)
		extends Serializable {
	
	val fixedCurrentPageNumber = if(currentPageNumber <= 0) 1 else currentPageNumber
	val currentConfig = config match {
			case Some(a) => a
			case None => new Config
		}
	val currentPage = new Page(totalItems, fixedCurrentPageNumber, currentConfig, uri)

	def renderPager: Appendable = GlobalConfig.paginator(currentPage)

	def renderCounter: Appendable = GlobalConfig.counter(currentPage)

	def getRange: (Int, Int) = {
		val drop = (fixedCurrentPageNumber - 1) * currentConfig.perPage
		val take = currentConfig.perPage
		(drop, take)
	}
}
