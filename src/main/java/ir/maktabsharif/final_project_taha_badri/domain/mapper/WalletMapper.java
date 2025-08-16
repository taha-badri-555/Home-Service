package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.WalletRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.WalletResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Wallet;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface WalletMapper extends BaseMapper<WalletRequest, WalletResponse, Wallet, Long> {


    //    @Mapping(target = "user", ignore = true)
    @Override
    Wallet requestToEntity(WalletRequest dto);


    //    @Mapping(target = "userId", ignore = true)

    @Override
    @Mapping(source = "user.id", target = "userId")
    WalletRequest entityToRequest(Wallet entity);


    //    @Mapping(target = "user", ignore = true)
    @Override
    void updateEntityWithRequest(WalletRequest dto, @MappingTarget Wallet entity);

    @Override@Mapping(source = "userId", target = "user.id")
    Wallet responseToEntity(WalletResponse dto);

    @Override
    @Mapping(source = "user.id", target = "userId")
    WalletResponse entityToResponse(Wallet entity);
}
