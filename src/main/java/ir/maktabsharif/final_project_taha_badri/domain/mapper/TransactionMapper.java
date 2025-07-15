package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateTransaction;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper extends BaseMapper<SaveOrUpdateTransaction, Transaction,Long> {
    @Override
    SaveOrUpdateTransaction entityToDto(Transaction entity);

    @Override
    Transaction dtoToEntity(SaveOrUpdateTransaction saveOrUpdateTransaction);

    @Override
    void updateEntityWithDTO(SaveOrUpdateTransaction saveOrUpdateTransaction, @MappingTarget Transaction entity);
}
