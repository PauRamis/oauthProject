package com.esliceu.oauthProject.Services;

import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
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

import javax.swing.text.html.parser.Entity;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    @Value("${client-id}")
    String clientId;

    @Value("${redirect-uri}")
    String redirecturi;

    @Value("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")
    String clientSecret;

    //Build get url
    public String getGoogleRedirection() throws Exception{
        URIBuilder b = new URIBuilder("https://accounts.google.com/o/oauth2/v2/auth");
        b.addParameter("client_id", clientId);
        b.addParameter("redirect_uri", redirecturi);
        b.addParameter("scope", "https://www.googleapis.com/auth/userinfo.email");
        b.addParameter("access_type", "offline");
        b.addParameter("state", "state_parameter_passthrough_value");
        b.addParameter("response_type", "code");
        b.addParameter("prompt", "select_account");
        return b.build().toURL().toString();

    }

    public String getGoogleUserEmail(String code) throws Exception{
        URL url = new URL("https://oauth2.googleapis.com/token");
        Map<String, String > parameters = new HashMap<>();
        parameters.put("client_id", clientId);
        parameters.put("redirect_uri", redirecturi);
        parameters.put("code", code);
        parameters.put("client_secret", clientSecret);
        parameters.put("grant_type", "authorization_code");
        String result = doPost(url, parameters);
        Map<String, String> map = new Gson()
                .fromJson(result, HashMap.class);
        String access_token = map.get("access_token");
        URIBuilder b = new URIBuilder("https://www.googleapis.com/oauth2/v1/userinfo");
        b.addParameter("access_token", access_token);
        b.addParameter("alt", "json");
        String resp = doGet(b.build().toURL());
        System.out.println(result); //No sout
        return null;
    }

    private String doGet(URL url) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url.toString());
        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            return EntityUtils.toString(response.getEntity());
        }
        throw new RuntimeException("Error in response");
    }

    private String doPost(URL url, Map<String, String> parameters) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url.toString());
        List<NameValuePair> nvps = new ArrayList<>();
        for (String s: parameters.keySet()) {
            nvps.add(new BasicNameValuePair(s, parameters.get(s)));
        }
        post.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity());
        }
        throw new RuntimeException("Error in response");
    }
}