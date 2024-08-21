package kiyotakeshi.com.example.playground.log.mdc

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.IOException
import java.util.*


@Component
class MDCFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val customId = "X-Custom-Id"
        val uniqueId = UUID.randomUUID()
        val customIdValue = request.getHeader(customId)?.lowercase()
        logger.info("Request IP address is ${request.remoteAddr}")
        logger.info("Request content type is ${request.contentType}")

        val responseWrapper = ContentCachingResponseWrapper(
            response
        )
        filterChain.doFilter(request, responseWrapper)
        responseWrapper.setHeader("requestId", uniqueId.toString())
        responseWrapper.setHeader(customId, customIdValue ?: "")
        responseWrapper.copyBodyToResponse()

        logger.info("Response header is set with uuid ${responseWrapper.getHeader("requestId")}")
        MDC.put(customId, customIdValue);
        MDC.put("requestURI", request.requestURI)
        MDC.put("uniqueTrackingNumber", uniqueId.toString())
        try {
            filterChain.doFilter(request, response)
        } finally {
            MDC.remove("requestURI")
            MDC.remove("uniqueTrackingNumber")
        }
    }
}