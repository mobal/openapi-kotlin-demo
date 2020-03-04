package hu.netcode.openapikotlindemo.controller

import hu.netcode.openapikotlindemo.data.dto.Product as ProductDto
import hu.netcode.openapikotlindemo.data.entity.Product as ProductEntity
import hu.netcode.openapikotlindemo.service.ProductService
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE],
    value = ["/api/v1/products"]
)
class ProductController(
    private val productService: ProductService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping(value = [""])
    fun create(@RequestBody @Valid productDto: ProductDto, @Autowired request: HttpServletRequest):
            ResponseEntity<ProductEntity> {
        return ResponseEntity(productService.save(productDto), HttpStatus.CREATED)
    }

    @GetMapping(value = [""])
    fun findAll(): ResponseEntity<List<ProductEntity>> {
        return ResponseEntity(productService.findAll(), HttpStatus.OK)
    }

    @GetMapping(value = ["/{productId}"])
    fun findById(@PathVariable productId: Long): ResponseEntity<ProductEntity> {
        return ResponseEntity(productService.findById(productId), HttpStatus.OK)
    }
}
