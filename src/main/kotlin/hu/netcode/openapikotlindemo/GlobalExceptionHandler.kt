package hu.netcode.openapikotlindemo

import hu.netcode.openapikotlindemo.service.ExceptionService
import java.lang.IllegalArgumentException
import javax.persistence.EntityExistsException
import javax.persistence.EntityNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val exceptionService: ExceptionService
) {
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<Map<String, Any>> {
        return exceptionService.createExceptionResponseEntity(ex.toString(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(EntityExistsException::class)
    fun handleEntityExistsFoundException(ex: EntityExistsException): ResponseEntity<Map<String, Any>> {
        return exceptionService.createExceptionResponseEntity(ex.toString(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException): ResponseEntity<Map<String, Any>> {
        return exceptionService.createExceptionResponseEntity(ex.toString(), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Map<String, Any>> {
        return exceptionService.createExceptionResponseEntity(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Map<String, Any>> {
        return exceptionService.createExceptionResponseEntity(ex.toString(), HttpStatus.BAD_REQUEST)
    }
}
