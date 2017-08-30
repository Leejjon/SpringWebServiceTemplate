package net.leejjon.javawebservice.service;

import java.sql.SQLException;
import java.util.*;

import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
public class ExampleService {

	@SneakyThrows
	public Optional<String> getExampleResultBy(String exampleId) {
		if (exampleId.equals("good")) {
			return Optional.of("result");
		} else if (exampleId.equals("bad")) {
			throw new SQLException("When a parameter is bad throw an exception.");
		} else {
			return Optional.empty();
		}
	}

	@SneakyThrows
	public List<String> getExistingExampleResults(boolean param) {
		List<String> exampleResults = new ArrayList<>();
		if (param) {
			exampleResults.add("result");
		}
		return exampleResults;
	}
}
