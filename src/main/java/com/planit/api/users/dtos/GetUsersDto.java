package com.planit.api.users.dtos;

import com.planit.api.models.ClimateModel;
import com.planit.api.models.CountryModel;
import com.planit.api.models.Role;
import com.planit.api.models.SeasonModel;

import java.util.List;

public record GetUsersDto(Long id, String name, String email, String password, List<Role> roles, ClimateModel climatePreference, SeasonModel seasonPreference, CountryModel countryDesired  ) {}