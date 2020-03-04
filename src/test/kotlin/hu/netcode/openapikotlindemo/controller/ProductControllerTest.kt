package hu.netcode.openapikotlindemo.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import hu.netcode.openapikotlindemo.data.dto.Product as ProductDto
import hu.netcode.openapikotlindemo.data.entity.Product as ProductEntity
import hu.netcode.openapikotlindemo.service.ProductService
import io.mockk.clearAllMocks
import io.mockk.every
import javax.persistence.EntityExistsException
import javax.persistence.EntityNotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@ExtendWith(value = [SpringExtension::class])
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@WebAppConfiguration
class ProductControllerTest {
    private companion object {
        const val HAMMER = "Hammer"
        const val PRICE = 100L
        const val URL = "/api/v1/products"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var productService: ProductService

    private val objectMapper = jacksonObjectMapper()
    private val productDto = ProductDto(HAMMER, HAMMER, PRICE)
    private val productEntity = ProductEntity(1L, HAMMER, HAMMER, PRICE)

    @BeforeEach
    fun beforeEach() {
        clearAllMocks()
    }

    @DisplayName(value = "Test for function create")
    @Nested
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    inner class Create {
        private fun validate(
            dto: ProductDto,
            httpStatus: HttpStatus
        ) {
            mockMvc.perform(
                    MockMvcRequestBuilders.request(HttpMethod.POST, URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().`is`(httpStatus.value()))
        }

        @Test
        fun `successfully create product`() {
            every {
                productService.save(any())
            } returns productEntity
            validate(productDto, HttpStatus.CREATED)
        }

        @Test
        fun `fail to create product because already exists`() {
            every {
                productService.save(any())
            } throws EntityExistsException()
            validate(productDto, HttpStatus.BAD_REQUEST)
        }

        @Test
        fun `fail to create product because validation fails`() {
            validate(ProductDto("", "", 0L), HttpStatus.BAD_REQUEST)
        }
    }

    @DisplayName(value = "Test for function findAll")
    @Nested
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    inner class FindAll {
        @Test
        fun `successfully find all products`() {
            every {
                productService.findAll()
            } returns listOf(productEntity)
            mockMvc.get(URL) {
                contentType = MediaType.APPLICATION_JSON
            }.andExpect {
                content { json(objectMapper.writeValueAsString(listOf(productEntity))) }
                content { contentType(MediaType.APPLICATION_JSON) }
                status { isOk }
            }
        }
    }

    @DisplayName(value = "Test for function findById")
    @Nested
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    inner class FindById {
        @Test
        fun `successfully find product by id`() {
            every {
                productService.findById(any())
            } returns productEntity
            mockMvc.get("$URL/1") {
                contentType = MediaType.APPLICATION_JSON
            }.andExpect {
                content { json(objectMapper.writeValueAsString(productEntity)) }
                content { contentType(MediaType.APPLICATION_JSON) }
                status { isOk }
            }
        }

        @Test
        fun `fail to find product by id`() {
            every {
                productService.findById(any())
            } throws EntityNotFoundException()
            mockMvc.get("$URL/1") {
                contentType = MediaType.APPLICATION_JSON
            }.andExpect {
                content { contentType(MediaType.APPLICATION_JSON) }
                status { isNotFound }
            }
        }
    }
}
