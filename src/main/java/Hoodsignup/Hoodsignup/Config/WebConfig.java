package Hoodsignup.Hoodsignup.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // ✅ For local testing
                        .allowedOrigins(
                                "http://localhost:19006", // React Native web (Expo)
                                "http://localhost:8081",  // React Native Metro bundler
                                "http://localhost:3000",  // React web app
                                "http://127.0.0.1:5500"   // optional
                        )
                        // ✅ For deployment (when hosted)
                        // You can later replace the above with your Render frontend domain:
                        // .allowedOrigins("https://hoood.social")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
