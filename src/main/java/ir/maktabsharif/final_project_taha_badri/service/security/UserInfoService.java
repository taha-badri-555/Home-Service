package ir.maktabsharif.final_project_taha_badri.service.security;

import ir.maktabsharif.final_project_taha_badri.domain.dto.UserInfoDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserInfoService {

    private final WebClient webClient;

    public UserInfoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public UserInfoDTO getUserInfo(String accessToken) {
        return webClient.get()
                .uri("/realms/test-1/protocol/openid-connect/userinfo")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(UserInfoDTO.class)
                .block();
    }
}
