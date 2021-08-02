package com.example.crudwithvaadin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //query to select id & msidn_id from database for filtering
    @Query("select c from Customer c " +
            "where lower(c.id) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.msisdn_id) like lower(concat('%', :searchTerm, '%'))")
    List<Customer> search(@Param("searchTerm") String searchTerm);
}
