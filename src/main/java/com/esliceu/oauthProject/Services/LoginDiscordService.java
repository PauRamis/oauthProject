package com.esliceu.oauthProject.Services;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoginDiscordService {
    @Value("1203028398176215091")
    String clientId;

    @Value("${redirect-uri}")
    String redirecturi;

    @Value("r90nytEcrI4qnlghnm9JifKlysaD6mKk")
    String clientSecret;

    @Value("9cd496ff9327d2bbc013139215e919810017bc433fce537026491b993e42e70c")
    String publicKey;

    public String getDiscordRedirection() throws Exception{
        URIBuilder b = new URIBuilder("https://discord.com/oauth2/authorize");
        b.addParameter("client_id", clientId);
        b.addParameter("redirect_uri", redirecturi);
        b.addParameter("scope", "email");
        /*b.addParameter("access_type", "offline");
        b.addParameter("state", "state_parameter_passthrough_value");
        b.addParameter("response_type", "code");
        b.addParameter("prompt", "select_account");*/
        return b.build().toURL().toString();
    }
}
