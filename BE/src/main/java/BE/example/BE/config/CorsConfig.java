package BE.example.BE.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://example.com")); // Cho phép origin cụ thể
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Phương thức HTTP
                                                                                                   // được phép
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept")); // Header được phép
        configuration.setAllowCredentials(true); // Cho phép gửi thông tin xác thực (cookies, authorization headers)
        configuration.setMaxAge(3600L); // Cache pre-flight request trong 3600 giây (1 giờ)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng cho tất cả các endpoint

        return source;
    }
}
