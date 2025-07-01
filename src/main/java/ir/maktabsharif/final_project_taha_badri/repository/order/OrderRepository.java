package ir.maktabsharif.final_project_taha_badri.repository.order;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.repository.base.crud.CrudRepository;
import ir.maktabsharif.final_project_taha_badri.repository.base.user.BaseUserRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
