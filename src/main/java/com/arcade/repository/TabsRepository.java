package com.arcade.repository;

import com.arcade.model.Tab;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TabsRepository extends JpaRepository<Tab, Long> {
    @NonNull Optional<Tab> findById(@NonNull Long id);
    Optional<Tab> findByExternalIdAndIsOpen(Long externalId, boolean isOpen);
}
