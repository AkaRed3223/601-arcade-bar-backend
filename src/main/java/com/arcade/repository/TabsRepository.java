package com.arcade.repository;

import com.arcade.model.Tab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TabsRepository extends JpaRepository<Tab, Long> {
    Optional<Tab> findById(Long id);
    Optional<Tab> findByExternalId(Long externalId);
    void deleteByExternalId(Long externalId);
}
