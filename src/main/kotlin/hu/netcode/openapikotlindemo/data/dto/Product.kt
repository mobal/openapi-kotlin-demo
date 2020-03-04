package hu.netcode.openapikotlindemo.data.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class Product(
    @get:NotBlank
    @field:Schema(required = true)
    val name: String,
    @get:NotBlank
    @field:Schema(required = true)
    val description: String,
    @get:Min(value = 1)
    @field:Schema(required = true)
    val price: Long = 0
)
