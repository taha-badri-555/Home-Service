package ir.maktabsharif.final_project_taha_badri.service.security;

import ir.maktabsharif.final_project_taha_badri.domain.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final UserInfoService userInfoService;

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        String accessToken = jwt.getTokenValue();
        UserInfoDTO userInfo = userInfoService.getUserInfo(accessToken);
        if (userInfo.role() == null || userInfo.role().isEmpty()) return Collections.emptySet();
        return Set.of(new SimpleGrantedAuthority("ROLE_" + userInfo.role().toUpperCase()));
    }
}
