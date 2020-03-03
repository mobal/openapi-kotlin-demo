package hu.netcode.openapikotlindemo.data.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @NotBlank
    val name: String,
    @NotBlank
    val description: String,
    @Min(value = 0)
    val price: Long = 0
)
