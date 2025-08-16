package ir.maktabsharif.final_project_taha_badri.service.user.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.*;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.UserRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.UserResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService extends BaseService<Person, Long, UserRequest, UserResponse> {

     Page<UserResponse> searchUsers(SearchRequest request, Pageable pageable);

    UserRequest findByEmailAndPassword(LoginRequest loginRequest);

    boolean existsByEmail(EmailRequest emailRequest);

    boolean existsByEmailAndIdNot(EmailAndIdRequest emailAndIdRequest);

    boolean existsByEmailAndId(EmailAndIdRequest emailAndIdRequest);

    Person findByEmail(String email);
}
