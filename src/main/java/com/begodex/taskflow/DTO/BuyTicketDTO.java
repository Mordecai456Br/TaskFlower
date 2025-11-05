package com.begodex.taskflow.DTO;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BuyTicketDTO(@NotNull Long raceId, @NotNull BigDecimal price) {}
