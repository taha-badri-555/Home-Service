package ir.maktabsharif.final_project_taha_badri.service.user.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.user.*;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseUser;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;

import java.util.List;

public interface UserService extends BaseService<BaseUser, Long, SaveOrUpdateUser> {

    public List<BaseUser> searchUsers(SearchRequest request);

    BaseUser findByEmailAndPassword(LoginRequest loginRequest);

    boolean existsByEmail(EmailRequest emailRequest);

    boolean existsByEmailAndIdNot(EmailAndIdRequest emailAndIdRequest);

    boolean existsByEmailAndId(EmailAndIdRequest emailAndIdRequest);

}
