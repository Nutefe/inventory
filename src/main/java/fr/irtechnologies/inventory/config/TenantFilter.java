package fr.irtechnologies.inventory.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getRequestURI();
            boolean isAdminPath = path != null && path.startsWith("/admin");

            String tenantId = request.getHeader("X-Tenant-Id");

            if (!isAdminPath) {
                if (tenantId == null || tenantId.isBlank()) {
                    throw new BadRequestException("Missing X-Tenant-Id header");
                }
                TenantContext.setTenantId(tenantId.trim());
            } else {
                // admin endpoints: tenant optional (overall counts)
                if (tenantId != null && !tenantId.isBlank()) {
                    TenantContext.setTenantId(tenantId.trim());
                }
            }

            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
