package hu.netcode.openapikotlindemo.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ExceptionService {
    fun createExceptionResponseEntity(msg: String, status: HttpStatus): ResponseEntity<Map<String, Any>> {
        return ResponseEntity(linkedMapOf("code" to status.value(), "message" to msg), status)
    }
}
