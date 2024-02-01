package com.esliceu.oauthProject.Services;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;

@Service
public class LoginService {
    @Value("${client-id}")
    String clientId;

    @Value("${redirect-uri}")
    String redirecturi;
    public String getGoogleRedirection() throws Exception{
        //https://accounts.google.com/o/oauth2/v2/auth
        URIBuilder b = new URIBuilder("https://accounts.google.com/o/oauth2/v2/auth");
        b.addParameter("client_id", clientId);
        b.addParameter("redirect_uri", redirecturi);

        return "";
    }
}
