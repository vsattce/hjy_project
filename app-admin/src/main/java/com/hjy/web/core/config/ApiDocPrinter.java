package com.hjy.web.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ApiDocPrinter implements ApplicationRunner {

    @Autowired
    private Environment environment;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String port = environment.getProperty("server.port", "8080");
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        String swaggerPath = environment.getProperty("springdoc.swagger-ui.path", "doc.html");
        String apiDocsPath = environment.getProperty("springdoc.api-docs.path", "v3/api-docs");

        String swaggerUrl = String.format("http://localhost:%s%s%s", port, contextPath, swaggerPath);
        String apiDocsUrl = String.format("http://localhost:%s%s%s", port, contextPath, apiDocsPath);

        log.info(ANSI_CYAN + "----------------------------------------------------------" + ANSI_RESET);
        log.info(ANSI_GREEN + "\t\tAPI 文档 (Knife4j UI) 已启动!" + ANSI_RESET);
        log.info(ANSI_YELLOW + "\t Swagger UI 地址: \t" + swaggerUrl + ANSI_RESET);
        log.info(ANSI_YELLOW + "\t OpenAPI 文档地址: \t" + apiDocsUrl + ANSI_RESET);
        log.info(ANSI_CYAN + "----------------------------------------------------------" + ANSI_RESET);
    }
}
