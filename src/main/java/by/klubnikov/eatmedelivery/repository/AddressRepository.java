package by.klubnikov.eatmedelivery.repository;

import by.klubnikov.eatmedelivery.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
