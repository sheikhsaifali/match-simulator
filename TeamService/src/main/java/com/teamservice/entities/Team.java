package com.teamservice.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.teamservice.dto.PlayerIds;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Team
{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID teamId;
    private String teamName;
    @Transient
    private List<Player> playerList;
    @Transient
    private PlayerIds playerIds;
    private int matchesWon;
    private int matchesLost;
}
