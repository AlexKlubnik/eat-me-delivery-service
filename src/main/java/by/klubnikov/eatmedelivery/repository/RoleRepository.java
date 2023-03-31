package by.klubnikov.eatmedelivery.repository;

import by.klubnikov.eatmedelivery.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
