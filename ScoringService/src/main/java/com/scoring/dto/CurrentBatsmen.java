package com.scoring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentBatsmen
{
    private CurrentBatsman currentBatsman;
    private CurrentBatsman nonStrikerBatsman;
}
