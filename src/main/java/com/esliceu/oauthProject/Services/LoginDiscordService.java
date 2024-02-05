package com.esliceu.oauthProject.Services;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        System.out.println("getDiscordUserEmail");
        System.out.println(code);
        String accessToken = exchangeCodeForToken(code);
        return getDiscordUserInfo(accessToken);
    }

    private String exchangeCodeForToken(String code) throws Exception {
        URIBuilder uriBuilder = new URIBuilder("https://discord.com/api/oauth2/token");
        uriBuilder.addParameter("client_id", clientId);
        uriBuilder.addParameter("client_secret", clientSecret);
        uriBuilder.addParameter("grant_type", "authorization_code");
        uriBuilder.addParameter("code", code);
        uriBuilder.addParameter("redirect_uri", redirecturi + "/discord/callback");

        // Realiza la solicitud HTTP POST para intercambiar el código por un token de acceso
        // (Implementa esta lógica según la biblioteca o método que estés utilizando)

        // Devuelve el token de acceso obtenido
        // (Implementa esta lógica según la biblioteca o método que estés utilizando)
        return null;
    }

    private String getDiscordUserInfo(String accessToken) throws Exception {
        URIBuilder uriBuilder = new URIBuilder("https://discord.com/api/users/@me");

        // Realiza la solicitud HTTP GET a la API de Discord para obtener información del usuario
        // (Implementa esta lógica según la biblioteca o método que estés utilizando)

        // Devuelve el correo electrónico del usuario obtenido de la respuesta
        // (Implementa esta lógica según la biblioteca o método que estés utilizando)
        return null;
    }

}
