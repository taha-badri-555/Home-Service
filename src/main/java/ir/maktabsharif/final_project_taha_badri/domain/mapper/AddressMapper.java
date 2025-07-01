package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateAddress;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper extends BaseMapper<SaveOrUpdateAddress, Address> {
}
