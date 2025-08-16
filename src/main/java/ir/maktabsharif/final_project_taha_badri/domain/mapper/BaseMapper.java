package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import org.mapstruct.MappingTarget;


public interface BaseMapper<REQUEST, RESPONSE, T extends BaseEntity<ID>, ID> {
    REQUEST entityToRequest(T entity);

    T requestToEntity(REQUEST dto);

    void updateEntityWithRequest(REQUEST dto, @MappingTarget T entity);

    T responseToEntity(RESPONSE dto);

    RESPONSE entityToResponse(T entity);


}
