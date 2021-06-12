package de.vms.agent.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.vms.agent.model.Agent;

public interface AgentRepository extends JpaRepository<Agent, UUID> {

    Agent findAgentByIdentifier(String identifier);

    @Query("select count(a) from Agent a where a.lastSeen >= :lastFiveMintutes")
    Long countActiveAgents(@Param("lastFiveMintutes") LocalDateTime activeThreshold);

}
