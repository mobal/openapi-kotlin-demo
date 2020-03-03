package hu.netcode.openapikotlindemo.controller

import hu.netcode.openapikotlindemo.data.dto.Product as ProductDto
import hu.netcode.openapikotlindemo.data.entity.Product as ProductEntity
import hu.netcode.openapikotlindemo.service.ProductService
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    produces = [MediaType.APPLICATION_JSON_VALUE],
    value = ["/api/v1/products"]
)
class ProductController(
    private val productService: ProductService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping(value = [""])
    fun create(@RequestBody @Valid productDto: ProductDto, @Autowired request: HttpServletRequest):
            ResponseEntity<Unit> {
        val product = productService.save(productDto)
        return ResponseEntity.noContent()
            .header("Location", "${request.requestURI}/${product.id}")
            .build()
    }

    @GetMapping(value = [""])
    fun findAll(): List<ProductEntity> {
        return productService.findAll()
    }

    @GetMapping(value = ["/{productId}"])
    fun findById(@PathVariable productId: Long): ProductEntity {
        return productService.findById(productId)
    }
}
