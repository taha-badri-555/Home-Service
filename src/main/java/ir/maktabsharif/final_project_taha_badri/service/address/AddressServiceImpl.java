package ir.maktabsharif.final_project_taha_badri.service.address;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateAddress;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.AddressMapper;
import ir.maktabsharif.final_project_taha_badri.repository.address.AddressRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary

public class AddressServiceImpl
        extends BaseServiceImpl<
        Address,
        Long,
        AddressRepository,
        AddressMapper,
        SaveOrUpdateAddress>
        implements AddressService {

    public AddressServiceImpl(AddressRepository repository, AddressMapper mapper) {
        super(repository, mapper);
    }
}
