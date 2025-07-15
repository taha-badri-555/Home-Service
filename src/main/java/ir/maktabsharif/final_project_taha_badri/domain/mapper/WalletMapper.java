package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateWallet;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletMapper extends BaseMapper<SaveOrUpdateWallet, Wallet,Long> {
    @Override
    SaveOrUpdateWallet entityToDto(Wallet entity);

    @Override
    Wallet dtoToEntity(SaveOrUpdateWallet saveOrUpdateWallet);

    @Override
    void updateEntityWithDTO(SaveOrUpdateWallet saveOrUpdateWallet, @MappingTarget Wallet entity);
}
