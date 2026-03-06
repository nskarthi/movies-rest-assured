package api.utils;

import com.github.javafaker.Faker;

public class FakerUtils {
	
	public static String generateMovieTitle() {
		Faker faker = new Faker();
		return faker.book().title();
	}
	
	public static String generateName() {
		Faker faker = new Faker();
		return faker.artist().name();
	}
	
	public static String generateCompanyName() {
		Faker faker = new Faker();
		return faker.company().name();
	}
	
	public static String generateRole() {
		Faker faker = new Faker();
		return faker.aquaTeenHungerForce().character();
	}
	
	public static String generateAward() {
		Faker faker = new Faker();
		return faker.university().suffix();
	}
	
	public static String generateGenre() {
		Faker faker = new Faker();
		return faker.book().genre();
	}
	
	public static String generateLanguage() {
		Faker faker = new Faker();
		return faker.nation().language();
	}
	
	public static String generateCountry() {
		Faker faker = new Faker();
		return faker.country().name();
	}

	public static Long generateAmount() {
		Faker faker = new Faker();
		return (long) (faker.number().randomDouble(2, 10, 100) * 1000000);
	}
	
	public static String generateCurrency() {
		Faker faker = new Faker();
		return faker.country().currency();
	}
}
