package hu.netcode.openapikotlindemo

import hu.netcode.openapikotlindemo.service.ExceptionService
import java.lang.IllegalArgumentException
import javax.persistence.EntityExistsException
import javax.persistence.EntityNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val exceptionService: ExceptionService
) {
    @ExceptionHandler(value = [
        DataIntegrityViolationException::class,
        EntityExistsException::class,
        IllegalArgumentException::class,
        MethodArgumentNotValidException::class
    ])
    fun handleBadRequestExceptions(ex: Exception): ResponseEntity<Map<String, Any>> {
        return exceptionService.createExceptionResponseEntity(ex.toString(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [
        EntityNotFoundException::class
    ])
    fun handleNotFoundExceptions(ex: Exception): ResponseEntity<Map<String, Any>> {
        return exceptionService.createExceptionResponseEntity(ex.toString(), HttpStatus.NOT_FOUND)
    }
}
