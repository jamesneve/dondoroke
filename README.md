# Dondoroke
=====

Dondoroke is a pagination plugin for Play Framework 2.4

Adding to your project
--------

To add Dondoroke to your Play project, add the following to Build.sbt

```
resolvers += Resolver.url("james-plugins", url("http://dl.bintray.com/jamesneve/sbt-plugins"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.jamesneve" % "dondoroke" % "1.0.4")
```

Routes
--------

Add a 'page' parameter to your Routes file:

```
GET /some/url		controllers.MyController.myAction(page: Int ?= 1)
```

Controller
--------

Import the helpers in your controller, and then create an implicit value for the pager to pass to the view. The pager takes three arguments:

* Total number of items in the list
* Current page number
* The current instance of RequestHeader
* [Optional] An instance of com.jamesneve.dondoroke.models.Config

For example:

```
import dondoroke.helpers._

class MyController {
	def myAction(page: Int) = { implicit request =>
		val someItems = Seq(1, 2, 3, 4, 5)
		implicit val paginator = new Paginator(someItems.size, page, request)
		Ok(views.html.pages.itemsList(someItems))
	}
}
```

The Paginator helper also provides a getRange method which returns (x: Int, y: Int) - the first number in the tuple is the offset, the second is the number of elements to be rendered. This can be passed to your DAO when you select elements to return, and complies with the slick .drop(x).take(y) syntax.

Views
--------

In your view, you can render a top items counter with renderCounter and a pager with renderPager:

```
@import dondoroke.helpers._
@(someItems: Seq[Int])(implicit paginator: Paginator)
@paginator.renderCounter
...
@paginator.renderPager
```

Global Config & Templates
--------

You'll usually want to use different HTML from the templates in the plugin. Start by copying all the template files from com/james/dondoroke/templates to a folder in your application. Then create an initializer somewhere in your app directory with, for example, the following code. (You can also change the number of items displayed per page and the level of padding.)

```
package modules.initializers

import com.google.inject.AbstractModule
import play.twirl.api.HtmlFormat._

trait Initializer {}

class Dondoroke extends Initializer {
  initialize()
  def initialize() = {
    dondoroke.models.GlobalConfig.abbreviation = views.html.somefolder.abbreviation.render _
    dondoroke.models.GlobalConfig.current = views.html.somefolder.current.render _
    dondoroke.models.GlobalConfig.first = views.html.somefolder.first.render _
    dondoroke.models.GlobalConfig.last = views.html.somefolder.last.render _
    dondoroke.models.GlobalConfig.next = views.html.somefolder.next.render _
    dondoroke.models.GlobalConfig.page = views.html.somefolder.page.render _
    dondoroke.models.GlobalConfig.paginator = views.html.somefolder.paginator.render _
    dondoroke.models.GlobalConfig.previous = views.html.somefolder.previous.render _

    dondoroke.models.GlobalConfig.counter = views.html.somefolder.counter.render _

    dondoroke.models.GlobalConfig.perPage = 20
    dondoroke.models.GlobalConfig.padding = 3
  }
}

class DondorokeInitializer extends AbstractModule {
  def configure() = {
    bind(classOf[Initializer])
      .to(classOf[Dondoroke]).asEagerSingleton
  }
}
```

Then in your application.conf, add:

```
play.modules.enabled += "modules.initializers.DondorokeInitializer"
```

After that you can change the HTML of the templates you copied to somefolder and use CSS to style the paginator.
