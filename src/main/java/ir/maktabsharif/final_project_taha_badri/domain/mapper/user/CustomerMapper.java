package ir.maktabsharif.final_project_taha_badri.domain.mapper.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.user.CustomerUpdate;
import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateCustomer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper extends BaseMapper<SaveOrUpdateCustomer, Customer, Long> {
    @Override
    SaveOrUpdateCustomer entityToDto(Customer entity);

    @Override
    Customer dtoToEntity(SaveOrUpdateCustomer dto);

    @Override
    void updateEntityWithDTO(SaveOrUpdateCustomer saveOrUpdateCustomer, @MappingTarget Customer entity);

    void updateEntityWithDTO(CustomerUpdate dto, @MappingTarget Customer entity);
}
