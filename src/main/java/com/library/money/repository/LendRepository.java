package com.library.money.repository;

import com.library.money.models.Lend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendRepository extends JpaRepository<Lend, Long> {
}
