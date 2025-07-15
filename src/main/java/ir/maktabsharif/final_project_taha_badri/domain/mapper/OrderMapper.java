package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateOrder;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper extends BaseMapper<SaveOrUpdateOrder, Order,Long> {
    @Override
    SaveOrUpdateOrder entityToDto(Order entity);

    @Override
    Order dtoToEntity(SaveOrUpdateOrder saveOrUpdateOrder);

    @Override
    void updateEntityWithDTO(SaveOrUpdateOrder saveOrUpdateOrder, @MappingTarget Order entity);
}
