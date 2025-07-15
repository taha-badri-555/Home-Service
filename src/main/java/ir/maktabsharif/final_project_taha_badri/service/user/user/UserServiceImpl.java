package ir.maktabsharif.final_project_taha_badri.service.user.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.user.*;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseUser;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.UserMapper;
import ir.maktabsharif.final_project_taha_badri.repository.user.user.UserRepository;
import ir.maktabsharif.final_project_taha_badri.repository.user.user.UserSpecifications;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserServiceImpl
        extends BaseServiceImpl<
        BaseUser,
        Long,
        UserRepository,
        SaveOrUpdateUser,
        UserMapper>
        implements UserService {
    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public BaseUser findByEmailAndPassword(LoginRequest loginRequest) {
        String email = loginRequest.email().toLowerCase();
        return repository.
                findByEmailAndPassword(email, loginRequest.password())
                .orElseThrow(() -> new NoSuchElementException("No such user found."));
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
    public List<BaseUser> searchUsers(SearchRequest request) {
        Specification<BaseUser> spec = combineSpecs(
                UserSpecifications.hasRole(request.role()),
                UserSpecifications.hasFirstName(request.firstName()),
                UserSpecifications.hasLastName(request.lastName()),
                UserSpecifications.hasMinRating(request.minRating()),
                UserSpecifications.hasMaxRating(request.maxRating()),
                UserSpecifications.hasServices(request.services())
        );

        return repository.findAll(spec);
    }

    @SafeVarargs
    private final Specification<BaseUser> combineSpecs(Specification<BaseUser>... specs) {
        Specification<BaseUser> result = null;
        for (Specification<BaseUser> spec : specs) {
            if (spec != null) {
                result = (result == null) ? spec : result.and(spec);
            }
        }
        return result;
    }
}
