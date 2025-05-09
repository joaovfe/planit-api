package com.planit.api.users.dtos;

import com.planit.api.enums.ERoleUser;
import com.planit.api.models.ClimateModel;
import com.planit.api.models.CountryModel;
import com.planit.api.models.SeasonModel;

public record CreateUserDto(
    String name,
    String email,
    String password,
    ERoleUser role,
    ClimateModel climatePreference,
    SeasonModel seasonPreference,
    CountryModel countryDesired
) {}
