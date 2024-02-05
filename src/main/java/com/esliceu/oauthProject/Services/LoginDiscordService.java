package com.esliceu.oauthProject.Services;

import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginDiscordService {
    @Value("1203028398176215091")
    String clientId;

    @Value("http://localhost:8080/discord/callback")
    String redirecturi;

    @Value("r90nytEcrI4qnlghnm9JifKlysaD6mKk")
    String clientSecret;

    @Value("9cd496ff9327d2bbc013139215e919810017bc433fce537026491b993e42e70c")
    String publicKey;

    public String getDiscordRedirection() throws Exception{
        URIBuilder b = new URIBuilder("https://discord.com/oauth2/authorize");
        b.addParameter("client_id", clientId);
        b.addParameter("redirect_uri", redirecturi);
        b.addParameter("scope", "identify email");
        b.addParameter("access_type", "offline");
        b.addParameter("state", "state_parameter_passthrough_value");
        b.addParameter("response_type", "code");
        b.addParameter("prompt", "select_account");
        return b.build().toURL().toString();
    }

    public String getDiscordUserEmail(String code) throws Exception {
        URL url = new URL("https://discord.com/api/oauth2/token");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", clientId);
        parameters.put("redirect_uri", redirecturi);
        parameters.put("code", code);
        parameters.put("client_secret", clientSecret);
        parameters.put("grant_type", "authorization_code");

        String result = doPost(url, parameters);

        Map<String, String> map = new Gson()
                .fromJson(result, HashMap.class);
        String accessToken = map.get("access_token");

        URIBuilder userInfoBuilder = new URIBuilder("https://discord.com/api/users/@me");
        String userInfoResponse = doGet(userInfoBuilder.setParameters(List.of(new BasicNameValuePair("Authorization", "Bearer " + accessToken))).build().toURL());

        Map<String, String> userInfoMap = new Gson()
                .fromJson(userInfoResponse, HashMap.class);

        return userInfoMap.get("email");
    }

    private String doGet(URL url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url.toString());
        CloseableHttpResponse response = httpClient.execute(get);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity());
        }
        throw new RuntimeException("Error in response");
    }

    private String doPost(URL url, Map<String, String> parameters) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url.toString());

        List<NameValuePair> nvps = new ArrayList<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        post.setEntity(new UrlEncodedFormEntity(nvps));

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity());
        }
        throw new RuntimeException("Error in response");
    }

}
