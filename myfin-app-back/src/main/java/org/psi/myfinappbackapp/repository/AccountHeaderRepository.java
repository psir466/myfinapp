package org.psi.myfinappbackapp.repository;

import org.psi.myfinappbackapp.entities.AccountHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHeaderRepository extends JpaRepository<AccountHeader, Long>{


}
