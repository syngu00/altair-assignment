package hu.syngu00.dataprocessor.logging.filter;

import hu.syngu00.dataprocessor.logging.RequestLogCommandService;
import hu.syngu00.dataprocessor.logging.RequireRequestLogging;
import hu.syngu00.dataprocessor.logging.wrapper.SpringRequestWrapper;
import hu.syngu00.dataprocessor.logging.wrapper.SpringResponseWrapper;
import hu.syngu00.dataprocessor.model.RequestLog;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Objects;


@Slf4j
@Component
@AllArgsConstructor
public class RequestLogFilter extends OncePerRequestFilter {

    private final ApplicationContext context;
    private final RequestLogCommandService requestLogCommandService;

    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {
        if (!isLoggingNecessary(request)) {
            chain.doFilter(request, response);
            return;
        }

        long startTime = System.currentTimeMillis();
        SpringRequestWrapper wrappedRequest = new SpringRequestWrapper(request);
        SpringResponseWrapper wrappedResponse = new SpringResponseWrapper(response);

        try {
            chain.doFilter(wrappedRequest, wrappedResponse);
        } catch (Exception e) {
            this.logResponse(startTime, wrappedRequest, wrappedResponse, 500);
            throw e;
        }

        this.logResponse(startTime, wrappedRequest, wrappedResponse, wrappedResponse.getStatus());
    }

    private void logResponse(long startTime, SpringRequestWrapper request, SpringResponseWrapper response, int overriddenStatus) throws IOException {
        response.setCharacterEncoding("UTF-8");
        long duration = System.currentTimeMillis() - startTime;
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String requestBody = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
        String responseBody = IOUtils.toString(response.getContentAsByteArray(), response.getCharacterEncoding());

        log.info("Response({} ms): method: {}, uri: {}, status: {}, request: {}, response: {}", duration, method, uri, overriddenStatus, requestBody, responseBody);
        try {
            requestLogCommandService.createEntry(new RequestLog(ZonedDateTime.now(), duration, method, uri, overriddenStatus, requestBody, responseBody));
        }catch (Exception e){
            log.error("Unable to create log entry caused by: ", e);
        }
    }

    private boolean isLoggingNecessary(HttpServletRequest request) {
        RequestMappingHandlerMapping mappings = (RequestMappingHandlerMapping) this.context.getBean("requestMappingHandlerMapping");
        HandlerExecutionChain handler;

        try {
            handler = mappings.getHandler(request);
        } catch (Exception e) {
            return false;
        }

        if (Objects.nonNull(handler) && handler.getHandler() instanceof HandlerMethod handlerMethod) {
            return handlerMethod.hasMethodAnnotation(RequireRequestLogging.class);
        }

        return false;
    }
}
