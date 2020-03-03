package hu.netcode.openapikotlindemo.data.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class Product(
    @NotBlank
    @Schema(required = true)
    val name: String,
    @NotBlank
    @Schema(required = true)
    val description: String,
    @Min(value = 1)
    @NotBlank
    @Schema(required = true)
    val price: Long = 0
)
