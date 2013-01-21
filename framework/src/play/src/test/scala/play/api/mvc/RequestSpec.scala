package play.api.mvc

import org.specs2.mutable._
import org.specs2.specification.{AroundOutside, Scope}
import org.specs2.execute.{Result => SpecsResult}
import play.api.mvc.Results._
import play.api.http.MediaRange

object RequestSpec extends Specification {

  sequential

  "Request" should {

    "support extracting accepted types" in {
      val headers = TestHeaders()
      val request = TestRequestHeaders(headers).
        withHeaders("Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      request.acceptedTypes must contain(MediaRange("text/html"),
        MediaRange("application/xhtml+xml"),
        MediaRange("application/xml;q=0.9"),
        MediaRange("*/*;q=0.8")).only
    }
  }

  case class TestRequestHeaders(headers: TestHeaders) extends RequestHeader{
    def id: Long = ???
    def tags: Map[String, String] = ???
    def uri: String = ???
    def path: String = ???
    def method: String = ???
    def version: String = ???
    def queryString: Map[String, Seq[String]] = ???
    def remoteAddress: String = ???
    def withHeaders(params:(String,String)*):TestRequestHeaders = TestRequestHeaders(
      TestHeaders(params.map{ case(a,b) => (a,Seq(b)) }))
  }

  case class TestHeaders(data:Seq[(String, Seq[String])]) extends Headers

  object TestHeaders {
    def apply():TestHeaders = TestHeaders(Seq())
  }
}

  