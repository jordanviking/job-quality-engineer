package intelipost.cucumber.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import intelipost.cucumber.common.utils.RestApi;
import intelipost.cucumber.common.utils.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import static intelipost.cucumber.common.utils.PropertiesUtil.getProperty;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.*;

public class RestAPISteps {

    public void init() {
        RestApi.setPath("");
        RestApi.setHeaders(new ArrayList<>());
        RestApi.setParameters(new ArrayList<>());
        RestApi.setJsonBodyString("");
        RestApi.setEntity(null);
    }


	@Given("^I clear all the headers$")
    public void clearAllHeaders() throws Throwable {
        RestApi.setHeaders(new ArrayList<>());
    }

	@Given("^I use the route \"([^\"]*)\"$")
	public void setRoute(String route) throws Throwable {
		RestApi.setPath(route);
		pathToReport();
	}

	@Given("^I set queryparameter \"([^\"]*)\" as \"([^\"]*)\"$")
	public void setQueryParameter(String key, String value) throws Throwable {
		RestApi.getParameters().add(new BasicNameValuePair(key, value));
	}

	@Given("^I set pathparameter \"([^\"]*)\" as \"([^\"]*)\"$")
	public void setPathParameter(String key, String value) throws Throwable {
		RestApi.setPath(RestApi.getPath().replaceAll(key, value));
	}

	@Given("^I set header \"([^\"]*)\" as \"([^\"]*)\"$")
	public void setHeader(String key, String value) throws Throwable {
		for (Header header : RestApi.getHeaders()) {
			if (header.getName().equals(key)) {
                RestApi.getHeaders().remove(header);
				break;
			}
		}
        RestApi.getHeaders().add(new BasicHeader(key, value));
	}

	@Given("^I use the json body$")
	public void setJsonBody(String jsonBody) throws Throwable {

		// replace variable values
		RestApi.setJsonBodyString(jsonBody);
		try {
			ObjectMapper mapper = new ObjectMapper();
			Object json = mapper.readValue(RestApi.getJsonBodyString(), Object.class);
			String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			Hooks.scenario.write(prettyJson);
		} catch (Exception e) {
			Hooks.scenario.write(RestApi.getJsonBodyString());
		}
	}

	@When("^I send the POST request$")
	public void postRequest() throws Throwable {
		HttpClient http = new HttpClient();
		RestApi.setEntity(new StringEntity(RestApi.getJsonBodyString(), ContentType.APPLICATION_JSON));
		RestApi.setResponse(http.sendPost(RestApi.getPath(), RestApi.getParameters(), RestApi.getHeaders(), RestApi.getEntity()));
		extractJsonResponse();
        responseToReport();
	}

	@When("^I send the GET request$")
	public void getRequest() throws Throwable {
		HttpClient http = new HttpClient();
        RestApi.setResponse(http.sendGet(RestApi.getPath(), RestApi.getParameters(), RestApi.getHeaders()));
		extractJsonResponse();
		responseToReport();
	}

	@When("^I send the PUT request$")
	public void putRequest() throws Throwable {
		HttpClient http = new HttpClient();
        RestApi.setEntity(new StringEntity(RestApi.getJsonBodyString(), ContentType.APPLICATION_JSON));
        RestApi.setResponse(http.sendPut(RestApi.getPath(), RestApi.getParameters(), RestApi.getHeaders(), RestApi.getEntity()));
		extractJsonResponse();
		responseToReport();
	}

	@When("^I send the DELETE request$")
	public void deleteRequest() throws Throwable {
		HttpClient http = new HttpClient();
		RestApi.setResponse(http.sendDelete(RestApi.getPath(), RestApi.getParameters(), RestApi.getHeaders()));
		extractJsonResponse();
		responseToReport();
	}

	@When("^I send the PATCH request$")
	public void patchRequest() throws Throwable {
		HttpClient http = new HttpClient();
        RestApi.setEntity(new StringEntity(RestApi.getJsonBodyString(), ContentType.APPLICATION_JSON));
        RestApi.setResponse(http.sendPatch(RestApi.getPath(), RestApi.getParameters(), RestApi.getHeaders(), RestApi.getEntity()));
		extractJsonResponse();
		responseToReport();
	}

	@When("^I send the OPTIONS request$")
	public void optionsRequest() throws Throwable {
		HttpClient http = new HttpClient();
        RestApi.setResponse(http.sendOptions(RestApi.getPath(), RestApi.getParameters(), RestApi.getHeaders()));
		extractJsonResponse();
		responseToReport();
	}


    @Then("the response JSON node \"([^\"]*)\" must \"([^\"]*)\"$")
    public void validateJsonNode(String key, String value) throws Throwable {
        if (!"".equals(RestApi.getResponseJson())) {
            JsonNode rootNode = new ObjectMapper().readTree(new StringReader(RestApi.getResponseJson()));
            if (value.equals("exist")) {
                Hooks.scenario.write("Value found: " + rootNode.at(key).asText());
                assertNotEquals("", rootNode.at(key).asText());
            } else {
                assertEquals("", rootNode.at(key).asText());
            }
        }
    }

    @Then("the response JSON must have \"([^\"]*)\" as \"([^\"]*)\"$")
    public void valdiateStringJsonBody(String key, String value) throws Throwable {
		if (!"".equals(RestApi.getResponseJson())) {
            JsonNode rootNode = new ObjectMapper().readTree(new StringReader(RestApi.getResponseJson()));

            if (!key.equals("size")) {
                Hooks.scenario.write("Value found: " + rootNode.at(key).asText());
                assertEquals(value.trim(), rootNode.at(key).asText().trim());
            } else {
                Hooks.scenario.write("Value found: " + value);

                if (rootNode == null) {
                    assertEquals(Integer.parseInt(value), 0);
                } else {
                    assertEquals(Integer.parseInt(value), rootNode.size());
                }
            }
        }
    }

    @Then("^Http response should be (\\d+)$")
	public void validateResponse(Integer responseCode) throws Throwable {
		Integer statusCode = RestApi.getResponse().getStatusLine().getStatusCode();
		assertEquals(responseCode.toString(), statusCode.toString());
	}


    @Then("the response JSON must have \"([^\"]*)\" as a not empty String$")
    public void validateNotEmptyStringJsonBody(String key) throws Throwable {

        if (!"".equals(RestApi.getResponseJson())) {
            JsonNode rootNode = new ObjectMapper().readTree(new StringReader(RestApi.getResponseJson()));

            String valueReturned = rootNode.at(key).asText();
			Hooks.scenario.write("Value found: " + valueReturned);

            if(StringUtils.isEmpty(valueReturned)) {
            	fail("The value from " + key + " must not be empty!");
            }
        }
    }

    @Then("the response JSON must have \"([^\"]*)\" as a not null Object$")
    public void validateNotNullObjectJsonBody(String key) throws Throwable {

        if (!"".equals(RestApi.getResponseJson())) {
            JsonNode rootNode = new ObjectMapper().readTree(new StringReader(RestApi.getResponseJson()));

            boolean valueReturned = rootNode.at(key).isNull();
			Hooks.scenario.write("Value found: " + valueReturned);

            if(valueReturned) {
            	fail("The value from " + key + " must not be empty!");
            }
        }
    }



    @Then("the response JSON must have \"([^\"]*)\" not equals \"([^\"]*)\"$")
    public void validateDifferentStringJsonBody(String key, String value) throws Throwable {
        if (!"".equals(RestApi.getResponseJson())) {
            JsonNode rootNode = new ObjectMapper().readTree(new StringReader(RestApi.getResponseJson()));

            Hooks.scenario.write("Value found: " + rootNode.at(key).asText());
            assertNotEquals(value, rootNode.at(key).asText());
        }
    }

    @Then("the response JSON must not have the field \"([^\"]*)\"$")
    public void validateFieldNotPresent(String key) throws Throwable {

        if (!"".equals(RestApi.getResponseJson())) {
            JsonNode rootNode = new ObjectMapper().readTree(new StringReader(RestApi.getResponseJson()));

            JsonNode keyNode = rootNode.get(key);

            Hooks.scenario.write("Key Used: " + key);
            assertNull(keyNode);
        }
    }




    @Then("^The response JSON list node \"([^\"]*)\" must contain all keys \"([^\"]*)\" as \"([^\"]*)\"$")
	public void theResponseJSONMustContainKeyAs(String listNodeName, String key, String value) throws Throwable {
		if (!"".equals(RestApi.getResponseJson())) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(new StringReader(RestApi.getResponseJson()));
			JsonNode list = rootNode.at(listNodeName);

			if(list.isArray()){
				Iterator<JsonNode> elementsIterator = ((ArrayNode) list).elements();
				while (elementsIterator.hasNext()) {
					JsonNode node = elementsIterator.next();
					String valueReturned = node.at("/" + key).asText();
					Hooks.scenario.write("Value found: " + valueReturned);
					assertEquals(value, valueReturned);

				}
			}else{
				fail("The value from " + key + " must not be empty!");
			}
		}
	}

    @Then("^The response JSON list node \"([^\"]*)\" must not contain any key \"([^\"]*)\" as \"([^\"]*)\"$")
    public void theResponseJSONMustNotContainKeyAs(String listNodeName, String key, String value) throws Throwable {
        if (!"".equals(RestApi.getResponseJson())) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new StringReader(RestApi.getResponseJson()));
            JsonNode list = rootNode.at(listNodeName);

            if(list.isArray()){
                Iterator<JsonNode> elementsIterator = ((ArrayNode) list).elements();
                while (elementsIterator.hasNext()) {
                    JsonNode node = elementsIterator.next();
                    String valueReturned = node.at("/" + key).asText();
                    Hooks.scenario.write("Value found: " + valueReturned);
                    assertNotEquals(value, valueReturned);
                }
            }else{
                fail("The value from " + key + " must not be empty!");
            }
        }
    }

    public void pathToReport() throws Throwable {
        Hooks.scenario.write(this.getCompletePath());
        Hooks.scenario.write(RestApi.getHeaders().toString());
        Hooks.scenario.write(RestApi.getParameters().toString());
    }

    private String getCompletePath() {
        String completePath = getProperty("host.schema") + "://" + getProperty("host.name") + ":"
                + getProperty("host.port") + getProperty("api.version") + RestApi.getPath();

        return completePath;
    }

    public void responseToReport() throws Throwable {

        Hooks.scenario.write(RestApi.getResponse().getStatusLine().toString());
        String headers = "[";
        for (Header header : RestApi.getResponse().getAllHeaders()) {
            headers += header.toString() + ", ";
        }
        Hooks.scenario.write(headers + "]");

        try {
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(RestApi.getResponseJson(), Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            Hooks.scenario.write(prettyJson);
        } catch (Exception e) {

        }
    }

    public void extractJsonResponse() throws Throwable {
        Integer statusCode = RestApi.getResponse().getStatusLine().getStatusCode();

        if (RestApi.getResponse().getEntity() != null && RestApi.getResponse().getEntity().getContent() != null) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(RestApi.getResponse().getEntity().getContent(),"UTF-8"));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            RestApi.setResponseJson(result.toString());
        } else {
            RestApi.setResponseJson("");
        }

    }


}