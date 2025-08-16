package ir.maktabsharif.final_project_taha_badri.service.user.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.*;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.UserRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.UserResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.UserMapper;
import ir.maktabsharif.final_project_taha_badri.exception.EmailNotFoundException;
import ir.maktabsharif.final_project_taha_badri.repository.user.user.UserRepository;
import ir.maktabsharif.final_project_taha_badri.repository.user.user.UserSpecifications;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl
        extends BaseServiceImpl<
        Person,
        Long,
        UserRepository,
        UserRequest,
        UserResponse,
        UserMapper>
        implements UserService {
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        super(repository, mapper);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserRequest findByEmailAndPassword(LoginRequest loginRequest) {
        String email = loginRequest.email().toLowerCase();
        Person user = repository.
                findByEmailAndPassword(email, passwordEncoder.encode(loginRequest.password()))
                .orElseThrow(() -> new UsernameNotFoundException("user"));
        return mapper.entityToRequest(user);
    }

    @Override
    public boolean existsByEmail(EmailRequest emailRequest) {
        String email = emailRequest.email().toLowerCase();
        return repository.existsByEmail(email);

    }

    @Override
    public boolean existsByEmailAndIdNot(EmailAndIdRequest emailAndIdRequest) {
        String email = emailAndIdRequest.email().toLowerCase();
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByEmailAndId(EmailAndIdRequest emailAndIdRequest) {
        String email = emailAndIdRequest.email().toLowerCase();
        return repository.existsByEmailAndId(email, emailAndIdRequest.id());
    }

    @Override
    public Page<UserResponse> searchUsers(SearchRequest request, Pageable pageable) {
        Specification<Person> spec = combineSpecs(
                UserSpecifications.hasRole(request.role()),
                UserSpecifications.hasFirstName(request.firstName()),
                UserSpecifications.hasLastName(request.lastName()),
                UserSpecifications.hasRatingBetween(request.minRating(), request.maxRating()),
                UserSpecifications.hasServiceName(request.serviceName())
        );

        return repository.findAll(spec, pageable)
                .map(mapper::entityToResponse);
    }


    @SafeVarargs
    private Specification<Person> combineSpecs(Specification<Person>... specs) {
        Specification<Person> result = null;
        for (Specification<Person> spec : specs) {
            if (spec != null) {
                result = (result == null) ? spec : result.and(spec);
            }
        }
        return result;
    }

    @Override
    public Person findByEmail(String email) {
        return repository.findByEmail(email).
                orElseThrow(() -> new EmailNotFoundException("user not found with " + email + " email"));
    }
}
