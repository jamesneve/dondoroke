package com.jamesneve.dondoroke.models

class Config(maybePerPage: Option[Int] = None,
						 maybePadding: Option[Int] = None) {
	
	val perPage: Int = getConfigValueOrDefault(maybePerPage, GlobalConfig.perPage)
	val padding: Int = getConfigValueOrDefault(maybePadding, GlobalConfig.padding)

	private def getConfigValueOrDefault(maybeOption: Option[Int], globalDefault: Int): Int = {
		maybeOption match {
			case Some(a) => a
			case None => globalDefault
		}
	}
}
