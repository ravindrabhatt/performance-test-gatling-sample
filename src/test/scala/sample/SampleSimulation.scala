package sample

class SampleSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl(Configuration.t_baseUrl)
    .acceptHeader("text/html,application/json,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  if (getScenario) {
    val getScenario = scenario("get").exec(GetSearch.searchGET)

    setUp(
      getScenario
        .inject(constantUsersPerSec(Configuration.t_constantUserPerSec) during (Configuration.t_constantLoadDuration seconds))
    ).protocols(httpProtocol)
  }else {
    val rampedUpRateScenario = scenario("post").exec(PostSearch.searchPost)

    setUp(
      rampedUpRateScenario
        .inject(constantUsersPerSec(Configuration.t_constantUserPerSec) during (Configuration.t_constantLoadDuration seconds))
    ).protocols(httpProtocol)
  }

  private def getScenario = {
    "GET".equals(Configuration.t_scenario)
  }
}

object GetSearch {
  val feeder = csv("search_all.csv").batch(2000).random
  val authToken = Configuration.t_authToken

  val searchGET = feed(feeder)
    .exec(http("search_all")
      .get("/endpoint?param1=${param1}&param2=${param2}&param3=${param3}&param4=${param4}&param5=${param5}")
      .header("HEADER_NAME", "HEADER_VALUE"))
}

object PostSearch {
  val feeder = csv("search_post.csv").batch(50).random
  val authToken = Configuration.t_authToken

  val searchPost = feed(feeder)
    .exec(http("post_search")
      .post("/endpoint")
      .header("Authorization", "Bearer "+ authToken)
      .body(ElFileBody("apiBody.json")).asJson)

}

object Configuration {
  val t_scenario = System.getProperty("scenario")
  val t_baseUrl = System.getProperty("baseUrl")
  val t_constantUserPerSec = Integer.getInteger("constantUserPerSec", 56).toInt
  val t_constantLoadDuration = Integer.getInteger("constantLoadDuration", 10).toInt
}
