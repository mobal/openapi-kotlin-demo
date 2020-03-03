package hu.netcode.openapikotlindemo.data.repository

import hu.netcode.openapikotlindemo.data.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun existsByName(name: String): Boolean
}
