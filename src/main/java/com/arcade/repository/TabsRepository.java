package com.arcade.repository;

import com.arcade.model.Tab;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TabsRepository extends JpaRepository<Tab, Long> {
    List<Tab> findAllByIsDeletedFalse();
    @NonNull Optional<Tab> findById(@NonNull Long id);
    Optional<Tab> findByExternalIdAndIsOpen(Long externalId, boolean isOpen);
}
