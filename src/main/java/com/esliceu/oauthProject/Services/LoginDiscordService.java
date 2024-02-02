package com.esliceu.oauthProject.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoginDiscordService {
    @Value("1203028398176215091")
    String clientId;

    @Value("r90nytEcrI4qnlghnm9JifKlysaD6mKk")
    String clientSecret;

    @Value("${redirect-uri}")
    String redirecturi;

    @Value("9cd496ff9327d2bbc013139215e919810017bc433fce537026491b993e42e70c")
    String publicKey;

    public String getDiscordRedirection() {
        return null;
    }
}
