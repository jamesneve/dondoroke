package dondoroke.models

import play.twirl.api.HtmlFormat._
import play.api.Play

object GlobalConfig {
	var abbreviation: Page => Appendable = com.jamesneve.dondoroke.templates.html.abbreviation.render _
	var current: Page => Appendable = com.jamesneve.dondoroke.templates.html.current.render _
	var first: Page => Appendable = com.jamesneve.dondoroke.templates.html.first.render _
	var last: Page => Appendable = com.jamesneve.dondoroke.templates.html.last.render _
	var next: Page => Appendable = com.jamesneve.dondoroke.templates.html.next.render _
	var page: (Page, Int) => Appendable = com.jamesneve.dondoroke.templates.html.page.render _
	var paginator: Page => Appendable = com.jamesneve.dondoroke.templates.html.paginator.render _
	var previous: Page => Appendable = com.jamesneve.dondoroke.templates.html.previous.render _

	var counter: Page => Appendable = com.jamesneve.dondoroke.templates.html.counter.render _

	var perPage = 25
	var padding = 3
}
