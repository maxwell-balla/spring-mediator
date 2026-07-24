package com.mediator.demo.infrastructures;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateOrderRequest(
        @NotBlank String product,
        @Positive int quantity
) {
}
