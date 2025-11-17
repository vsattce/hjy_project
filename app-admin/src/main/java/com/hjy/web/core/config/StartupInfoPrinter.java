package com.hjy.web.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class StartupInfoPrinter implements ApplicationRunner {

    @Autowired
    private Environment environment;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 获取端口号
        String port = environment.getProperty("server.port", "8080");

        // 获取项目上下文路径 (如果配置了 server.servlet.context-path)
        String contextPath = environment.getProperty("server.servlet.context-path", "");

        // 构建后端服务的基础地址
        String baseUrl = String.format("http://localhost:%s%s", port, contextPath);

        // 构建 API 文档的路径 (使用配置的路径，或 Knife4j 的默认路径 /doc.html)
        String swaggerPath = environment.getProperty("springdoc.swagger-ui.path", "doc.html");
        String swaggerUrl = baseUrl + swaggerPath;

        // 构建 OpenAPI JSON 文件的路径
        String apiDocsPath = environment.getProperty("springdoc.api-docs.path", "v3/api-docs");
        String apiDocsUrl = baseUrl + apiDocsPath;

        // 打印漂亮的启动横幅
        log.info(ANSI_CYAN + "------------------------------------------" + ANSI_RESET);
        log.info(ANSI_GREEN + "\t\t🚀 应用启动成功!" + ANSI_RESET);
        log.info(ANSI_CYAN + "------------------------------------------" + ANSI_RESET);
        log.info(ANSI_YELLOW + "\t📍 后端服务地址: \t" + baseUrl + ANSI_RESET);
        log.info(ANSI_YELLOW + "\t📚 API 文档 (Knife4j): \t" + swaggerUrl + ANSI_RESET);
        log.info(ANSI_YELLOW + "\t📄 OpenAPI 文档 (JSON): \t" + apiDocsUrl + ANSI_RESET);
        log.info(ANSI_CYAN + "------------------------------------------" + ANSI_RESET);
    }
}
