package net.leejjon.javawebservice.controller;

import net.leejjon.javawebservice.service.ExampleService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import net.leejjon.javawebservice.v1.model.ExampleSchema;
import net.leejjon.javawebservice.v1.model.ExamplesList;
import net.leejjon.javawebservice.v1.model.UnexpectedError;
import net.leejjon.javawebservice.v1.model.ValidationError;
import net.leejjon.javawebservice.v1.server.ExamplesApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@RequestMapping(value = "/api/v1")
public class ExampleController implements ExamplesApi {

	private ExampleService exampleService;

	@Autowired
	public ExampleController(ExampleService exampleService) {
		this.exampleService = exampleService;
	}

	@Override
	@RequestMapping(value = "/examples/{exampleId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity getExample(@PathVariable("exampleId") String exampleId) {
		if (exampleId == null || exampleId.isEmpty()) {
			ValidationError validationError = new ValidationError();
			validationError.setMessage("The exampleId parameter should not be null or empty.");
			validationError.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationError);
		}

		Optional<String> exampleResult = exampleService.getExampleResultBy(exampleId);
		if (exampleResult.isPresent()) {
			ExampleSchema example = new ExampleSchema();
			example.setResult(exampleResult.get());
			return ResponseEntity.status(HttpStatus.OK).body(example);
		} else { // If the result wasn't present, return null. Don't log an exception unless you couldn't continue.
			// For example if this were to be an update call and the example didn't exist, it would be unable to update.
			// But if it is just retrieving, the module that does the call should decide what to do.
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}

	@Override
	@ApiOperation(value = "", notes = "", response = Void.class, tags = {"examples",})
	@RequestMapping(value = "/examples", produces = {"application/json"}, method = RequestMethod.GET)
	public ResponseEntity getExistingExamples(@RequestParam(value = "parameterExists", required = false) Boolean parameterExists) {
		if (parameterExists == null) {
			ValidationError validationError = new ValidationError();
			validationError.setMessage("ParameterExists should not be null.");
			validationError.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationError);
		}

		List<String> existingExampleResults = exampleService.getExistingExampleResults(parameterExists);

		ExamplesList examplesList = new ExamplesList();
		for (String s : existingExampleResults) {
			ExampleSchema es = new ExampleSchema();
			es.setResult(s);
			examplesList.add(es);
		}
		return ResponseEntity.status(HttpStatus.OK).body(examplesList);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<UnexpectedError> handleErrorResponse(Exception ex) {
		UUID randomUUID = UUID.randomUUID();
		log.error(randomUUID + " " + ex.getMessage(), ex);

		UnexpectedError unexpectedError = new UnexpectedError();
		unexpectedError.setMessage(ex.getMessage());
		unexpectedError.setException(ex.getClass().getCanonicalName());
		unexpectedError.setUuid(randomUUID.toString());
		unexpectedError.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(unexpectedError);
	}
}
