package com.esliceu.oauthProject.Services;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        URIBuilder b = new URIBuilder("https://oauth2.googleapis.com/token");
        b.addParameter("client_id", clientId);
        b.addParameter("redirect_uri", redirecturi);
        b.addParameter("code", code);
        b.addParameter("client_secret", clientSecret);
        b.addParameter("grant_type", "authorization_code");


    }
}