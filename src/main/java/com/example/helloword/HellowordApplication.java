package com.example.helloword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HellowordApplication {

    // SpringApplication.run(App.class, args) chính là câu lệnh để tạo ra container.
    // Sau đó nó tìm toàn bộ các dependency trong project của bạn và đưa vào đó.
    // Spring đặt tên cho container là ApplicationContext
    // và đặt tên cho các dependency là Bean
    public static void main(String[] args) {
        // trả về 1 ApplicationContext chính là container, chứa toàn bộ các Bean
        SpringApplication.run(HellowordApplication.class, args);
    }

}
