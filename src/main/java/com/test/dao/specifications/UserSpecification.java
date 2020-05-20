package com.test.dao.specifications;

import com.test.api.request.UserSearchRequest;
import com.test.domain.Country;
import com.test.domain.Role;
import com.test.domain.User;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserSpecification implements Specification<User> {
    private UserSearchRequest userSearchRequest;

    public UserSpecification(UserSearchRequest userSearchRequest) {
        this.userSearchRequest = userSearchRequest;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Path<String> name = root.get("name");
        Path<String> email = root.get("email");
        Path<Country> country = root.get("country");
        Path<LocalDateTime> creationDate = root.get("creationDate");
        Join<User, Role> rolesJoin = root.join("roles");

        List<Predicate> predicates = new ArrayList<>();
        if(userSearchRequest.getName() != null) {
            predicates.add(
                    criteriaBuilder.equal(criteriaBuilder.lower(name), userSearchRequest.getName().toLowerCase())
            );
        }
        if(userSearchRequest.getEmail() != null) {
            predicates.add(
                    criteriaBuilder.equal(criteriaBuilder.lower(email), userSearchRequest.getEmail().toLowerCase())
            );
        }
        if(userSearchRequest.getCountry() != null) {
            predicates.add(
                    criteriaBuilder.equal(criteriaBuilder.lower(country.get("name")), userSearchRequest.getCountry().toLowerCase())
            );
        }
        if(userSearchRequest.getCreationDateFrom() !=null) {
           predicates.add(
                   criteriaBuilder.greaterThan(creationDate, userSearchRequest.getCreationDateFrom())
           );
        }
        if(userSearchRequest.getCreationDateTo() != null) {
            predicates.add(
                    criteriaBuilder.lessThan(creationDate, userSearchRequest.getCreationDateTo())
            );
        }
        if(userSearchRequest.getRole() != null) {
            predicates.add(
                    criteriaBuilder.equal(criteriaBuilder.lower(rolesJoin.get("name")), userSearchRequest.getRole().toLowerCase())
            );
        }

        //as we use join, we should avoid repeating data
        criteriaQuery.distinct(true);
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
