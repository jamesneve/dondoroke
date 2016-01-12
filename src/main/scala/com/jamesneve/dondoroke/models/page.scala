package com.jamesneve.dondoroke.models

import play.api.mvc.RequestHeader
import play.twirl.api.HtmlFormat._

class Page(val totalItems: Int, val pageNumber: Int = 1, 
		config: Config, request: RequestHeader) {

	def totalPages: Int = (totalItems + config.perPage - 1) / config.perPage

	def firstPage: Boolean = (pageNumber == 1)

	def lastPage: Boolean = (pageNumber >= totalPages)

	def forwardAbb: Boolean = (pageNumber + config.padding < totalPages)

	def backwardAbb: Boolean = (pageNumber - config.padding > 1)

	def buildUrl(pageLink: Int): String = {
		val pageRegex = """(page)=[0-9]+""".r.unanchored
		val urlOptionsRegex = """(\?|\&)([^=]+)\=([^&]+)""".r.unanchored

		(request.uri) match {
			case pageRegex(_*) => pageRegex.replaceAllIn(request.uri, s"page=${pageLink}")
			case urlOptionsRegex(_*) => s"${request.uri}&page=${pageLink}"
			case blankUri => s"${request.uri}?page=${pageLink}" 
		}
	}

	def getPaddedPages(transformer: (Int, Int) => Int): Seq[Appendable] =
		for(i <- 1 to config.padding; if(transformer(pageNumber, i) >= 1 && transformer(pageNumber, i) <= totalPages)) 
			yield { com.jamesneve.dondoroke.templates.html.page.render(this, transformer(pageNumber, i)) }

	def from: Int = scala.math.min(((pageNumber - 1) * config.perPage) + 1, totalItems)

	def to: Int = scala.math.min((from + config.perPage) - 1, totalItems)
}
