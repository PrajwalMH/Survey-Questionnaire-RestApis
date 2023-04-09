package com.prajwalmh.RestAPIs.survey;


import org.json.JSONException;
import org.junit.jupiter.api.Test;
import java.util.Base64;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

    //http://localhost:8080/surveys/Survey1/questions/Question1
    String str = """
			
			{
			  "id": "Question1",
			  "description": "Most Popular Cloud Platform Today",
			  "options": [
			    "AWS",
			    "Azure",
			    "Google Cloud",
			    "Oracle Cloud"
			  ],
			  "correctAnswer": "AWS"
			}
			
			""";


    //

    private static String SPECIFIC_QUESTION_URL = "/getSurveyById/Survey1/questions/Question1";

	private static String GENERIC_QUESTIONS_URL = "/getSurveyById/Survey1/questions";
    @Autowired
    private TestRestTemplate template;

    //
    //[Content-Type:"application/json",


    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {
//        ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> responseEntity
				= template.exchange(SPECIFIC_QUESTION_URL, HttpMethod.GET, httpEntity, String.class);

        String expectedResponse =
                """
                {
                    "id":"Question1",
                    "description":"Most Popular Cloud Platform Today",
                    "correctAnswer":"AWS"
                }
                """;

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse.trim(), responseEntity.getBody(), false);
        //assertEquals(expectedResponse.trim(), responseEntity.getBody());
        //System.out.println();
        //System.out.println(responseEntity.getHeaders());
    }

	@Test
	void retrieveAllSurveyQuestions_basicScenario() throws JSONException {

//		ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_QUESTIONS_URL, String.class);

		String expectedResponse =
				"""
						[
						  {
						    "id": "Question1"
						  },
						  {
						    "id": "Question2"
						  },
						  {
						    "id": "Question3"
						  }
						]
				
				""";

		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> responseEntity
				= template.exchange(GENERIC_QUESTIONS_URL, HttpMethod.GET, httpEntity, String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}

	@Test
	void addNewSurveyQuestion_basicScenario() {

		String requestBody = """
				{
				  "description": "Your Favorite Language",
				  "options": [
				    "Java",
				    "Python",
				    "JavaScript",
				    "Haskell"
				  ],
				  "correctAnswer": "Java"
				}
			""";


		//


		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();

		HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);

		ResponseEntity<String> responseEntity
				= template.exchange(GENERIC_QUESTIONS_URL, HttpMethod.POST, httpEntity, String.class);



		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		assertTrue(locationHeader.contains("/getSurveyById/Survey1/questions"));

		//DELETE
		//locationHeader
		//REMOVE SIDE EFFECT
		ResponseEntity<String> responseEntityDelete
				= template.exchange(locationHeader, HttpMethod.DELETE, httpEntity, String.class);

		assertTrue(responseEntityDelete.getStatusCode().is2xxSuccessful());


	}

	private HttpHeaders createHttpContentTypeAndAuthorizationHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Basic " + performBasicAuthEncoding("admin","password"));
		return headers;
	}

	String performBasicAuthEncoding(String user, String password) {
		String combined = user + ":" + password;
		byte[] encodedBytes = Base64.getEncoder().encode(combined.getBytes());
		return new String(encodedBytes);
	}


}
