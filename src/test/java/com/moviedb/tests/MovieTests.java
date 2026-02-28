package com.moviedb.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import com.moviedb.pojos.health.Health;
import com.moviedb.pojos.movies.Cast;
import com.moviedb.pojos.movies.Crew;
import com.moviedb.pojos.movies.Finance;
import com.moviedb.pojos.movies.Movie;
import com.moviedb.pojos.movies.ReleaseDetails;

import api.utils.AuthManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
//import io.restassured.filter.log.LogDetail.IF_VALIDATION_FAILS;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class MovieTests {
	RequestSpecification requestSpecification;
	ResponseSpecification responseSpecification;
	//String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicm9sZSI6ImFkbWluIiwiaWF0IjoxNzcyMDY5ODAxLCJleHAiOjE3NzIxNTYyMDF9.bDM_VmIEGn39WJJZFyO5gb13rEIUUHuM7_8Nnq7CWkw";
	String bearerToken = AuthManager.getToken();
	
	@BeforeClass
	public void beforeClass() {
		// 1. Setup Request Spec
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
				.setBaseUri("http://localhost:4000/")
				.setBasePath("/movies")
				.addHeader("Authorization", "Bearer " + bearerToken)
				.setContentType(ContentType.JSON)
				.log(LogDetail.ALL);
		requestSpecification = requestSpecBuilder.build();
		
		// 2. Setup Response Spec
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
				.expectContentType(ContentType.JSON)
				.log(LogDetail.ALL);

		responseSpecification = responseSpecBuilder.build();

		// 3. Enable Global Logging on Failure
		// Shortcut
	    //RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		// The "Explicit" (The Architect's Choice), reason: customization
		// RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig()
		//	    .enableLoggingOfRequestAndResponseIfValidationFails()
		//	    .blacklistHeader("Authorization"));
	}

	//@Test(description = "Verify that the movie list is correctly paginated when 'page' and 'limit' parameters are provided")
	public void givenAuthorizedUser_whenRequestingAllMovieDetails_thenReturnsList() {
		int qParamPage = 1;
		int qParamLimit = 100;

		given()
			.baseUri("http://localhost:4000/")
			.basePath("/movies")
			.header("Authorization", "Bearer " + "incorrect_token")
			.contentType(ContentType.JSON)
			.queryParam("page", qParamPage)
			.queryParam("limit", qParamLimit)
			.log().all()
		.when()
			.get()
		.then()
			.spec(responseSpecification)
			.statusCode(200)
			.body("limit", equalTo(qParamLimit),
					"page", equalTo(qParamPage));
	}
	
	//@Test(description = "Verify that a 200 OK and the complete list of movies are returned for an authorized request")
	public void givenUnauthorizedUser_whenRequestingAllMovieDetails_thenReturnsList() {
		//tbd
		int qParamPage = 1;
		int qParamLimit = 100;

		given(requestSpecification)
			.queryParam("page", qParamPage)
			.queryParam("limit", qParamLimit)
		.when()
			.get()
		.then()
			.spec(responseSpecification)
			.statusCode(200);
	}
	
	//@Test(description = "Ensure that movie details are returned when a valid movie ID is passed as input")
	public void givenValidMovieId_whenRequestingMovieInfo_thenStatusIs200() {
		String movieId = "2";
		given(requestSpecification)
		.when()
			.get("/" + movieId)
		.then()
			.spec(responseSpecification)
			.assertThat()
			.statusCode(200)
			.body("title", equalTo("Inception"),
					"crew.director", equalTo("Christopher Nolan"),
					"cast.actorName", hasItem("Leonardo DiCaprio"),
					"finance.budget", equalTo(160000000))
			.body("cast.find { it.actorName == 'Joseph Gordon-Levitt'}.role", is("Arthur"));
			//.body("cast.find { it.actorName == 'Joseph Gordon-Levitt'}.role.awards", hasItem("Independent Spirit Award"));
	}

	//@Test(description = "Ensure that 404 NOT FOUND is returned when an invalid movie Id is requested")
	public void givenInvalidMovieId_whenRequestingMovieInfo_thenStatusIs404() {
		String movieId = "200";
		given(requestSpecification)
		.when()
			.get("/" + movieId)
		.then()
			.spec(responseSpecification)
			.assertThat()
			.statusCode(404)
			.body("status", equalTo(404),
					"error", equalTo("Not Found"),
					"message", equalTo("Movie with id " + movieId + " not found"));
	}

	//@Test(description = "Ensure that new movie is created when movie details provided is valid")
	public void givenValidMovieDetails_whenCreatingMovie_thenStatusIs201() {
		String payload = "{\r\n"
				+ "  \"title\": \"Colarado Jones\",\r\n"
				+ "  \"crew\": {\r\n"
				+ "    \"director\": \"Denis Villeneuve\",\r\n"
				+ "    \"musicDirector\": \"Grégoire Hetzel\",\r\n"
				+ "    \"productionCompany\": \"Micro_scope\"\r\n"
				+ "  },\r\n"
				+ "  \"cast\": [\r\n"
				+ "    {\r\n"
				+ "      \"actorName\": \"Lubna Azabal\",\r\n"
				+ "      \"role\": \"Nawal Marwan\",\r\n"
				+ "      \"awards\": [\r\n"
				+ "        \"Magritte Award for Best Actress\",\r\n"
				+ "        \"Jutra Award for Best Actress\"\r\n"
				+ "      ]\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"actorName\": \"Mélissa Désormeaux-Poulin\",\r\n"
				+ "      \"role\": \"Jeanne Marwan\",\r\n"
				+ "      \"awards\": [\r\n"
				+ "        \"Genie Award Nomination for Best Actress\"\r\n"
				+ "      ]\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"actorName\": \"Maxim Gaudette\",\r\n"
				+ "      \"role\": \"Simon Marwan\",\r\n"
				+ "      \"awards\": [\r\n"
				+ "        \"Genie Award for Best Supporting Actor\"\r\n"
				+ "      ]\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"genres\": [\r\n"
				+ "    \"Drama\",\r\n"
				+ "    \"Mystery\",\r\n"
				+ "    \"War\"\r\n"
				+ "  ],\r\n"
				+ "  \"releaseDetails\": {\r\n"
				+ "    \"language\": \"French, Arabic\",\r\n"
				+ "    \"country\": \"Canada, France\"\r\n"
				+ "  },\r\n"
				+ "  \"finance\": {\r\n"
				+ "    \"budget\": 6800000,\r\n"
				+ "    \"boxOffice\": 16038312,\r\n"
				+ "    \"profit\": 9238312,\r\n"
				+ "    \"currency\": \"CAD\"\r\n"
				+ "  }\r\n"
				+ "}";

		int movieId = given(requestSpecification)
			.body(payload)
		.when()
			.post("")
		.then()
			.spec(responseSpecification)
			.statusCode(201)
			.extract().path("id");

		System.out.println("Id of the newly created movie is: " + movieId);
	}

	//@Test(description = "Ensure that new movie is created when movie details provided via POJO is valid")
	public void givenValidMovieDetailsViaPOJO_whenCreatingMovie_thenStatusIs201() {
		Crew crew = new Crew("Christopher Nolan", "Hans Zimmer", "Syncopy, Lynda Obst Productions, Legendary Pictures");
		Cast cast1 = new Cast("Matthew McConaughey", "Joseph Cooper", List.of(
		        "Saturn Award for Best Actor (Nominee)", 
		        "Critics' Choice Movie Award for Best Actor (Nominee)"
		    ));
		Cast cast2 = new Cast("Anne Hathaway", "Anne Hathaway", List.of(
		        "Saturn Award for Best Actress (Nominee)"));
		Cast cast3 = new Cast("Jessica Chastain", "Murphy Cooper (Adult)", List.of(
		        "Saturn Award for Best Supporting Actress (Nominee)"));
		List<String> genres = List.of("Sci-Fi", "Adventure", "Drama");
		ReleaseDetails releaseDetails= new ReleaseDetails("English", "United States, United Kingdom");
		Finance finance = new Finance(165000000L, 773800000L, 47200000L, "USD");

		Movie movie = new Movie("Lovingstellar", crew, List.of(cast1, cast2, cast3), genres, releaseDetails, finance);
		Movie moviePayload = new Movie()
			.setTitle("Amazingstellar")
			.setCrew(crew)
			.setCast(List.of(cast1, cast2, cast3))
			.setGenres(genres)
			.setReleaseDetails(releaseDetails)
			.setFinance(finance);

		Movie responseMovie = given(requestSpecification)
			.body(movie)
		.when()
			.post("")
		.then()
			.spec(responseSpecification)
			.statusCode(201)
			.extract().as(Movie.class);

		System.out.println("Id of the newly created movie is: " + responseMovie.getId());

	    assertNotNull(responseMovie.getId(), "ID should be generated by API");
	    assertEquals(responseMovie.getTitle(), movie.getTitle());

	    SoftAssert softAssert = new SoftAssert();
	    softAssert.assertTrue(responseMovie.getId() > 0, "ID should be generated");
	    softAssert.assertEquals(responseMovie.getTitle(), movie.getTitle());
	    softAssert.assertEquals(responseMovie.getFinance().getBoxOffice(), movie.getFinance().getBoxOffice());
	    softAssert.assertAll();   
	}

	//@Test(description = "Ensure that a 400 BAD REQUEST is returned when movie name is missing in the movie creation request")
	public void givenInvalidMovieDetails_whenCreatingMovie_thenStatusIs400() {
		String movieTitle = "Boston Boys";
		Crew crew = new Crew("Christopher Nolan", "Hans Zimmer", "Syncopy, Lynda Obst Productions, Legendary Pictures");
		Cast cast1 = new Cast("Matthew McConaughey", "Joseph Cooper", List.of(
		        "Saturn Award for Best Actor (Nominee)", 
		        "Critics' Choice Movie Award for Best Actor (Nominee)"
		    ));
		Cast cast2 = new Cast("Anne Hathaway", "Anne Hathaway", List.of(
		        "Saturn Award for Best Actress (Nominee)"));
		Cast cast3 = new Cast("Jessica Chastain", "Murphy Cooper (Adult)", List.of(
		        "Saturn Award for Best Supporting Actress (Nominee)"));
		List<String> genres = List.of("Sci-Fi", "Adventure", "Drama");
		ReleaseDetails releaseDetails= new ReleaseDetails("English", "United States, United Kingdom");
		Finance finance = new Finance(165000000L, 773800000L, 47200000L, "USD");

		Movie moviePayloadWithoutTitle = new Movie()
			.setCrew(crew)
			.setCast(List.of(cast1, cast2, cast3))
			.setGenres(genres)
			.setReleaseDetails(releaseDetails)
			.setFinance(finance);

		given(requestSpecification)
			.body(moviePayloadWithoutTitle)
		.when()
			.post("")
		.then()
			.spec(responseSpecification)
			.statusCode(400);
	}
	
	//@Test(description = "Ensure that movie information is updated when valid movie id is provided")
	public void givenValidMovieId_whenUpdatingMovie_thenStatusIs200() {
		String movieId = "3";
		String payload = "{\r\n"
				+ "  \"id\": 3,\r\n"
				+ "  \"title\": \"Lagaan\",\r\n"
				+ "  \"crew\": {\r\n"
				+ "    \"director\": \"Ashutosh Gowariker\",\r\n"
				+ "    \"musicDirector\": \"A.R. Rahman\",\r\n"
				+ "    \"productionCompany\": \"Aamir Khan Productions\"\r\n"
				+ "  },\r\n"
				+ "  \"cast\": [\r\n"
				+ "    {\r\n"
				+ "      \"actorName\": \"Aamir Khan\",\r\n"
				+ "      \"role\": \"Bhuvan\",\r\n"
				+ "      \"awards\": [\r\n"
				+ "        \"Filmfare Award for Best Actor\"\r\n"
				+ "      ]\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"actorName\": \"Gracy Singh\",\r\n"
				+ "      \"role\": \"Gauri\",\r\n"
				+ "      \"awards\": [\r\n"
				+ "        \"IIFA Award for Best Actress\"\r\n"
				+ "      ]\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"actorName\": \"Rachel Shelley\",\r\n"
				+ "      \"role\": \"Elizabeth Russell\",\r\n"
				+ "      \"awards\": []\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"genres\": [\r\n"
				+ "    \"Drama\",\r\n"
				+ "    \"Musical\",\r\n"
				+ "    \"Sport\"\r\n"
				+ "  ],\r\n"
				+ "  \"releaseDetails\": {\r\n"
				+ "    \"language\": \"Hindi\",\r\n"
				+ "    \"country\": \"India\"\r\n"
				+ "  },\r\n"
				+ "  \"finance\": {\r\n"
				+ "    \"budget\": 240000000,\r\n"
				+ "    \"boxOffice\": 659000000,\r\n"
				+ "    \"profit\": 409000000,\r\n"
				+ "    \"currency\": \"INR\"\r\n"
				+ "  }\r\n"
				+ "}";

		given(requestSpecification)
			.body(payload)
		.when()
			.put("/" + movieId)
		.then()
			.spec(responseSpecification)
			.statusCode(200)
			.body("finance.budget", equalTo(240000000));
	}
	
	//@Test(description = "Ensure that movie information is updated when valid movie id is provided and the payload is from POJO")
	public void givenValidMovieIdUsingPOJO_whenUpdatingMovie_thenStatusIs200() {
		String movieId = "3";
		String movieTitle = "Lagaan";
		Crew crew = new Crew("Ashutosh Gowariker", "A.R. Rahman", "Aamir Khan Productions");
		Cast cast1 = new Cast("Aamir Khan", "Bhuvan", List.of(
		        "Filmfare Award for Best Actor)"));
		Cast cast2 = new Cast("Gracy Singh", "Gauri", List.of(
		        "IIFA Award for Best Actress"));
		Cast cast3 = new Cast("Rachel Shelley", "Elizabeth Russell", List.of());
		List<String> genres = List.of("Drama", "Musical", "Sport");
		ReleaseDetails releaseDetails= new ReleaseDetails("Hindi", "India");
		Finance finance = new Finance(240000000L, 659000000L, 409000000L, "INR");

		Movie moviePayload = new Movie()
			.setId(3)
			.setTitle(movieTitle)
			.setCrew(crew)
			.setCast(List.of(cast1, cast2, cast3))
			.setGenres(genres)
			.setReleaseDetails(releaseDetails)
			.setFinance(finance);
		
		Movie responseMovie = given(requestSpecification)
			.body(moviePayload)
		.when()
			.put("/" + movieId)
		.then()
			.spec(responseSpecification)
			.statusCode(200)
			.extract()
			.as(Movie.class);

		assertEquals(responseMovie.getFinance().getBudget(), moviePayload.getFinance().getBudget());
	}

	//@Test(description = "Ensure that a 200 OK is returned when a valid movie ID is deleted by an authorized user")
	public void givenValidMovieId_whenDeletingMovie_thenStatusIs200() {
		String movieId = "14";

		given(requestSpecification)
		.when()
			.delete("/" + movieId)
		.then()
			.spec(responseSpecification)
			.statusCode(200);
	}

	//@Test(description = "Verify that the health check endpoint returns 200 OK and status 'UP'")
	public void givenAllAPIsAreUp_whenHealthCheckIsPerformed_thenReturn200() {
		Health health = given()
			.baseUri("http://localhost:4000/")
			.basePath("/health")
			.log().all()
	.	when()
			.get()
		.then()
			.spec(responseSpecification)
			.statusCode(200)
			.body("status", equalTo("UP"))
			.extract()
			.as(Health.class);

		System.out.println("Health check status: " + health.getStatus());
	}
	
}
