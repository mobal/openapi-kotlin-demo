package hu.netcode.openapikotlindemo.service

import com.ninjasquad.springmockk.MockkBean
import hu.netcode.openapikotlindemo.data.dto.Product as ProductDto
import hu.netcode.openapikotlindemo.data.entity.Product as ProductEntity
import hu.netcode.openapikotlindemo.data.repository.ProductRepository
import io.mockk.clearAllMocks
import io.mockk.every
import java.util.Optional
import javax.persistence.EntityExistsException
import javax.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ProductServiceTest {
    private companion object {
        const val HAMMER = "Hammer"
        const val ID = 1L
        const val PRICE = 100L
    }
    @Autowired
    private lateinit var productService: ProductService

    @MockkBean
    private lateinit var productRepository: ProductRepository

    private val productDto = ProductDto(HAMMER, HAMMER, PRICE)
    private val productEntity = ProductEntity(ID, HAMMER, HAMMER, PRICE)

    @BeforeEach
    fun beforeEach() {
        clearAllMocks()
    }

    @DisplayName(value = "Tests for function findAll")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class FindAll {
        @Test
        fun `successfully find all products`() {
            every {
                productRepository.findAll()
            } returns listOf(productEntity)
            val productList = productService.findAll()
            assertTrue(productList.isNotEmpty())
            assertEquals(1, productList.size)
            assertEquals(productEntity, productList[0])
        }

        @Test
        fun `successfully find all products and return empty product list`() {
            every {
                productRepository.findAll()
            } returns emptyList()
            val productList = productService.findAll()
            assertTrue(productList.isEmpty())
        }
    }

    @DisplayName(value = "Tests for function findById")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class FindById {
        @Test
        fun `successfully find product by id`() {
            every {
                productRepository.findById(any())
            } returns Optional.of(productEntity)
            val product = productService.findById(1L)
            assertEquals(ID, product.id)
            assertEquals(HAMMER, product.name)
            assertEquals(HAMMER, product.description)
            assertEquals(PRICE, product.price)
        }

        @Test
        fun `fail to find by id`() {
            every {
                productRepository.findById(any())
            } throws EntityNotFoundException()
            assertThrows<EntityNotFoundException> { productService.findById(1L) }
        }
    }

    @DisplayName(value = "Tests for function save")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Save {
        @Test
        fun `successfully save new product`() {
            every {
                productRepository.existsByName(any())
            } returns false
            every {
                productRepository.save(any<ProductEntity>())
            } returns productEntity
            val result = productService.save(productDto)
            assertEquals(ID, result.id)
            assertEquals(HAMMER, result.name)
            assertEquals(HAMMER, result.description)
            assertEquals(PRICE, result.price)
        }

        @Test
        fun `fail to save new product because already exists`() {
            every {
                productRepository.existsByName(any())
            } returns true
            assertThrows<EntityExistsException> { productService.save(productDto) }
        }
    }
}
