package kiyotakeshi.com.example.playground.log.mdc

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


// TODO: MDCInsertingServletFilter を継承するでもいけそう
// https://logback.qos.ch/manual/mdc.html#mis
@Component
class MDCFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        MDC.put("requestURI", request.requestURI)
        // TODO: presumably this is extracted from the request (or defaulted, if not supplied)
        MDC.put("uniqueTrackingNumber", request.requestId)
        try {
            filterChain.doFilter(request, response)
        } finally {
            MDC.remove("requestURI")
            MDC.remove("uniqueTrackingNumber")
        }
    }
}