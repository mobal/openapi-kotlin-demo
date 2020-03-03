package hu.netcode.openapikotlindemo.service

import hu.netcode.openapikotlindemo.data.dto.Product as ProductDto
import hu.netcode.openapikotlindemo.data.entity.Product
import hu.netcode.openapikotlindemo.data.repository.ProductRepository
import javax.persistence.EntityExistsException
import javax.persistence.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun save(productDto: ProductDto): Product {
        if (productRepository.existsByName(productDto.name)) {
            throw EntityExistsException("The product with name ${productDto.name} is already exists")
        }
        return productRepository.save(
            Product(name = productDto.name, description = productDto.name, price = productDto.price)
        )
    }

    fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    fun findById(productId: Long): Product {
        return productRepository.findById(productId).orElseThrow {
            EntityNotFoundException("The requested product with id $productId was not found")
        }
    }
}
