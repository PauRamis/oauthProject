package com.esliceu.oauthProject.Services;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.HashMap;
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
    }
}