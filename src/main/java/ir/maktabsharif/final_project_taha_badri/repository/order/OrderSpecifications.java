package ir.maktabsharif.final_project_taha_badri.repository.order;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;

public class OrderSpecifications {
    public static Specification<Order> hasUserRole(String role) {
        return (root, query, cb) -> {
            if (role == null || (!role.equalsIgnoreCase("CUSTOMER") && !role.equalsIgnoreCase("EXPERT"))) return null;
            if (role.equalsIgnoreCase("CUSTOMER")) {
                Join<Order, Customer> customerJoin = root.join("customer");
                return cb.equal(customerJoin.get("role"), "CUSTOMER");
            } else if (role.equalsIgnoreCase("EXPERT")) {
                Join<Order, Expert> expertJoin = root.join("expert");
                return cb.equal(expertJoin.get("role"), "EXPERT");
            } else {
                return null;
            }
        };
    }

    public static Specification<Order> hasDateBetween(ZonedDateTime start, ZonedDateTime end) {
        return (root, query, cb) -> {
            if (start == null && end == null) return null;

            Path<ZonedDateTime> startDatePath = root.get("startDate");
            Path<ZonedDateTime> endDatePath = root.get("endDate");

            if (start != null && end != null) {
                return cb.and(cb.greaterThanOrEqualTo(endDatePath, start), cb.lessThanOrEqualTo(startDatePath, end));
            } else if (start != null) {
                return cb.greaterThanOrEqualTo(endDatePath, start);
            } else {
                return cb.lessThanOrEqualTo(startDatePath, end);
            }
        };
    }

    public static Specification<Order> hasStatus(OrderStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("orderStatus"), status);
    }

    public static Specification<Order> hasService(String serviceName) {
        return (root, query, cb) -> {
            if (serviceName == null) return null;
            Join<Order, Service> serviceJoin = root.join("service");
            return cb.equal(serviceJoin.get("name"), serviceName);
        };
    }

    public static Specification<Order> hasCustomerId(Long customerId) {
        return (root, query, cb)
                -> cb.equal(root.get("customer").get("id"), customerId);
    }


}




