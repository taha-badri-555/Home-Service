package ir.maktabsharif.final_project_taha_badri.domain.mapper.user;


import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.UserRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.UserResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserRequest, UserResponse, Person,Long> {
    @Override
    UserRequest entityToRequest(Person entity);

    @Override
    Person requestToEntity(UserRequest dto);

    @Override
    void updateEntityWithRequest(UserRequest dto,@MappingTarget Person entity);

    @Override
    Person responseToEntity(UserResponse dto);

    @Override
    UserResponse entityToResponse(Person entity);
}
