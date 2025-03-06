package com.gullycricket.match_service.repositories;

import com.gullycricket.match_service.entities.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface Match_Service_Repo extends JpaRepository<Matches, UUID> {
}
