package ir.maktabsharif.final_project_taha_badri.repository.user.user;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.domain.enums.Role;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<Person> hasRole(String role) {
        return (root, query, cb)
                -> role == null ? null : cb.equal(root.get("role"), role);
    }

    public static Specification<Person> hasFirstName(String firstName) {
        return (root, query, cb) -> firstName == null ? null :
                cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<Person> hasLastName(String lastName) {
        return (root, query, cb) -> lastName == null ? null :
                cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    public static Specification<Person> hasRatingBetween(Double minRating, Double maxRating) {
        return (root, query, cb) -> {
            Predicate predicate = cb.equal(root.get("role"), Role.EXPERT.name());
            if (minRating != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("avgScore"), minRating));
            }
            if (maxRating != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("avgScore"), maxRating));
            }
            return predicate;
        };
    }



    public static Specification<Person> hasServiceName(String serviceName) {
        return (root, query, cb) -> {
            if (serviceName == null || serviceName.trim().isEmpty()) return null;

            Join<Person, Service> serviceJoin = root.join("services");

            return cb.like(
                    cb.lower(serviceJoin.get("name")),
                    "%" + serviceName.toLowerCase() + "%"
            );
        };
    }
}
