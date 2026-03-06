package com.moviedb.tests.movies;

import java.util.List;

import com.moviedb.pojos.movies.Cast;
import com.moviedb.pojos.movies.Crew;
import com.moviedb.pojos.movies.Finance;
import com.moviedb.pojos.movies.Movie;
import com.moviedb.pojos.movies.ReleaseDetails;

import api.utils.FakerUtils;

public class MovieData {

    public static Movie getMoviePayload() {
		String movieTitle = FakerUtils.generateMovieTitle();
		Crew crew = new Crew(FakerUtils.generateName(), FakerUtils.generateName(), FakerUtils.generateCompanyName());
		Cast cast1 = new Cast(FakerUtils.generateName(), FakerUtils.generateName(), List.of(
				FakerUtils.generateAward(), FakerUtils.generateAward()
		    ));
		Cast cast2 = new Cast(FakerUtils.generateName(), FakerUtils.generateName(), List.of(FakerUtils.generateAward()));
		Cast cast3 = new Cast(FakerUtils.generateName(), FakerUtils.generateName(), List.of(FakerUtils.generateAward()));
		List<String> genres = List.of(FakerUtils.generateGenre(), FakerUtils.generateGenre(), FakerUtils.generateGenre());
		ReleaseDetails releaseDetails= new ReleaseDetails(FakerUtils.generateCountry(), FakerUtils.generateCountry());
		Finance finance = new Finance(FakerUtils.generateAmount(), FakerUtils.generateAmount(), FakerUtils.generateAmount(), 
				FakerUtils.generateCurrency());

		return Movie.builder()
			.title(movieTitle)
			.crew(crew)
			.cast(List.of(cast1, cast2, cast3))
			.genres(genres)
			.releaseDetails(releaseDetails)
			.finance(finance)
			.build();
    }
}
