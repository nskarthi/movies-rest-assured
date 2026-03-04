package api.utils;

import com.github.javafaker.Faker;

public class Utils {
	static Faker faker;
	
	public static String getName() {
		faker = new Faker();
		return faker.name().firstName();
	}
}
