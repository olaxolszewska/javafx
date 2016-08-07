package data.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import data.dto.Airport;
import data.dto.AuthorizationDetails;

public class LufthansaDataRetriever {

    private static String authorizationToken;

    public void getAuthorizationToken() throws IOException {
    	
    	String userId = "ck9aqffrwwpnrpratfm2qf69";
    	String userSecret = "aBSgTFNczJ";

        String url = "https://api.lufthansa.com/v1/oauth/token";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> nvps = new ArrayList<>();

        nvps.add(new BasicNameValuePair("client_id", userId));
        nvps.add(new BasicNameValuePair("client_secret", userSecret));
        nvps.add(new BasicNameValuePair("grant_type", "client_credentials"));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps);
        httpPost.setEntity(entity);

        CloseableHttpResponse httpResponse = client.execute(httpPost);

        AuthorizationDetails authorizationDetails = new AuthorizationDetails();

        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            HttpEntity responseEntity = httpResponse.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseHandler.handleResponse(httpResponse));
            authorizationDetails.setAccessToken(root.get("access_token").asText());
            authorizationToken = authorizationDetails.getAccessToken();
            EntityUtils.consume(responseEntity);
        } finally {
            httpResponse.close();
        }

    }

    public List<Airport> retrieveAirportData(String code, String lang, boolean lhOperated) throws IOException {
    	List<Airport> airports = new ArrayList<>();
    	
        String url = "https://api.lufthansa.com/v1/references/airports/";
        
        if (code != null) {
            url += code + "/";
        }
        
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet getAirportData = new HttpGet(url);
        Header authorization = new BasicHeader("Authorization", "Bearer " + authorizationToken);
        getAirportData.setHeader(authorization);

        CloseableHttpResponse response = client.execute(getAirportData);

        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseHandler.handleResponse(response));
            AirportJsonToObjectConverter converter = new AirportJsonToObjectConverter();
            if (code != null) {
                airports = converter.convertSingleRequest(root, true);
            } else {
            	airports = converter.convertSingleRequest(root, false);
            }
            
            } finally {
            response.close();
        }

        return airports;
    }

    //this will be used when I'm working from work laptop
	@SuppressWarnings("unused")
	private CloseableHttpClient getProxyClient() {
		HttpHost proxy = new HttpHost("proxyase.ham.hamburgsud.com", 8081);
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		return HttpClients.custom().setRoutePlanner(routePlanner).build();
	}
	
}
