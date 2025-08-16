package ir.maktabsharif.final_project_taha_badri.service.security;

import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.service.user.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    public Person loadUserByUsername(String email) throws UsernameNotFoundException {
        return userService.findByEmail(email);
    }
}
