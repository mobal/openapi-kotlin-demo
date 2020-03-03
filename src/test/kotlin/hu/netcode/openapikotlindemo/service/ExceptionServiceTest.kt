package hu.netcode.openapikotlindemo.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ExceptionServiceTest {
    @Autowired
    private lateinit var exceptionService: ExceptionService

    private val msg = "msg"

    @DisplayName(value = "Tests for function createExceptionResponseEntity")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CreateExceptionResponseEntity() {
        @Test
        fun `successfully create ResponseEntity`() {
            val result = exceptionService.createExceptionResponseEntity(msg, HttpStatus.OK)
            assertEquals(HttpStatus.OK.value(), result.statusCodeValue)
            assertEquals(HttpStatus.OK.value(), result.body!!["code"])
            assertEquals(msg, result.body!!["message"])
        }
    }
}
