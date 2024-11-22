package kiyotakeshi.com.example.playground.rest

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {
    var logger = LoggerFactory.getLogger(javaClass)

    fun FieldError.toValidationErrorDetail(): ValidationErrorDetail =
        ValidationErrorDetail(
            field = this.field,
            rejectedValue = this.rejectedValue,
            code = this.code,
            objectName = this.objectName,
            message = this.defaultMessage
        )

    data class ValidationErrorDetail(
        val field: String,
        val rejectedValue: Any?,
        val code: String?,
        val objectName: String,
        val message: String?
    )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(ex: MethodArgumentNotValidException): ProblemDetail {
        logger.error("exception occurs", ex)
        val errors = ex.bindingResult.fieldErrors.map { it.toValidationErrorDetail() }

        val problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST).apply {
            detail = ex.message
            setProperty("errors", errors)
        }
        return problemDetail
    }

    @ExceptionHandler(CustomerException::class)
    fun handleCustomerException(ex: CustomerException): ProblemDetail {
        logger.error("exception occurs", ex)
        when (ex) {
            is CustomerNotFound -> {
                val problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND).apply {
                    detail = ex.message
                }
                return problemDetail
            }
        }
    }
}
