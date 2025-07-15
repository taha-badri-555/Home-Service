package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateAddress;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper extends BaseMapper<SaveOrUpdateAddress, Address, Long> {
    @Override
    SaveOrUpdateAddress entityToDto(Address entity);

    @Override
    Address dtoToEntity(SaveOrUpdateAddress dto);

    @Override
    void updateEntityWithDTO(SaveOrUpdateAddress dto, @MappingTarget Address entity);
}
