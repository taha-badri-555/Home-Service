package ir.maktabsharif.final_project_taha_badri.repository.user.user;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseUser;
import ir.maktabsharif.final_project_taha_badri.service.user.user.UserService;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class UserSpecifications {
    public static Specification<BaseUser> hasRole(String role) {
        return (root, query, cb) -> role == null ? null : cb.equal(root.get("role"), role);
    }

    public static Specification<BaseUser> hasFirstName(String firstName) {
        return (root, query, cb) -> firstName == null ? null :
                cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<BaseUser> hasLastName(String lastName) {
        return (root, query, cb) -> lastName == null ? null :
                cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    public static Specification<BaseUser> hasMinRating(Byte minRating) {
        return (root, query, cb) -> minRating == null ? null :
                cb.greaterThanOrEqualTo(root.get("rating"), minRating);
    }

    public static Specification<BaseUser> hasMaxRating(Byte maxRating) {
        return (root, query, cb) -> maxRating == null ? null :
                cb.lessThanOrEqualTo(root.get("rating"), maxRating);
    }

    public static Specification<BaseUser> hasServices(Set<Service> services) {
        return (root, query, cb) -> {
            if (services == null || services.isEmpty()) return null;
            Join<BaseUser, UserService> serviceJoin = root.join("services");
            return serviceJoin.in(services);
        };
    }
}
