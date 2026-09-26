package org.paymentSystemApp;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class PaymentSystemApplication {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentSystemApplication.class);


    public static void main(String[] args) {

        SpringApplication app =
                new SpringApplication(PaymentSystemApplication.class);


        // =========================================================
        // 1. APPLICATION STARTING
        // =========================================================

        app.addListeners(
                (ApplicationListener<ApplicationStartingEvent>) event ->
                        logger.info(
                                "[1-STARTING] Spring Boot application is starting..."
                        )
        );


        // =========================================================
        // 2. ENVIRONMENT PREPARED
        // =========================================================

        app.addListeners(
                (ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {

                    var environment = event.getEnvironment();

                    logger.info(
                            "[2-ENVIRONMENT] Environment prepared"
                    );

                    logger.info(
                            "[2-ENVIRONMENT] Active Profiles = {}",
                            Arrays.toString(
                                    environment.getActiveProfiles()
                            )
                    );

                    logger.info(
                            "[2-ENVIRONMENT] Persistence Type = {}",
                            environment.getProperty(
                                    "app.persistence.type"
                            )
                    );

                    logger.info(
                            "[2-ENVIRONMENT] Datasource URL Present = {}",
                            environment.getProperty(
                                    "spring.datasource.url"
                            ) != null
                    );

                    logger.info(
                            "[2-ENVIRONMENT] Mongo URI Present = {}",
                            environment.getProperty(
                                    "spring.data.mongodb.uri"
                            ) != null
                    );
                }
        );


        // =========================================================
        // 3. APPLICATION PREPARED
        // =========================================================

        app.addListeners(
                (ApplicationListener<ApplicationPreparedEvent>) event -> {

                    ConfigurableApplicationContext context =
                            event.getApplicationContext();

                    logger.info(
                            "[3-PREPARED] Application context prepared"
                    );

                    logger.info(
                            "[3-PREPARED] Bean definitions registered = {}",
                            context.getBeanDefinitionCount()
                    );
                }
        );


        // =========================================================
        // 4. APPLICATION STARTED
        // =========================================================

        app.addListeners(
                (ApplicationListener<ApplicationStartedEvent>) event ->
                        logger.info(
                                "[4-STARTED] Application context refreshed. " +
                                        "Singleton beans initialized."
                        )
        );


        // =========================================================
        // 5. APPLICATION READY
        // =========================================================

        app.addListeners(
                (ApplicationListener<ApplicationReadyEvent>) event -> {

                    ConfigurableApplicationContext context =
                            event.getApplicationContext();


                    logger.info(
                            "[5-READY] Application is ready. " +
                                    "All runners executed."
                    );


                    // -------------------------------------------------
                    // WEB SERVER INFORMATION
                    // -------------------------------------------------

                    if (context instanceof WebServerApplicationContext webContext) {

                        int port =
                                webContext
                                        .getWebServer()
                                        .getPort();


                        String contextPath =
                                context.getEnvironment()
                                        .getProperty(
                                                "server.servlet.context-path",
                                                ""
                                        );


                        if (contextPath == null) {
                            contextPath = "";
                        }


                        if (!contextPath.isBlank()
                                && !contextPath.startsWith("/")) {

                            contextPath =
                                    "/" + contextPath;
                        }


                        if (contextPath.endsWith("/")) {

                            contextPath =
                                    contextPath.substring(
                                            0,
                                            contextPath.length() - 1
                                    );
                        }


                        String baseUrl =
                                "http://localhost:"
                                        + port
                                        + contextPath;


                        logger.info(
                                "[WEB] Server running on port = {}",
                                port
                        );

                        logger.info(
                                "[WEB] Application URL = {}",
                                baseUrl
                        );

                        logger.info(
                                "[WEB] Swagger UI = {}" +
                                        "/swagger-ui/index.html",
                                baseUrl
                        );

                        logger.info(
                                "[WEB] OpenAPI JSON = {}" +
                                        "/v3/api-docs",
                                baseUrl
                        );
                    }


                    // -------------------------------------------------
                    // BEAN INFORMATION
                    // -------------------------------------------------

                    logBeanSummary(context);
                }
        );


        // =========================================================
        // 6. APPLICATION FAILED
        // =========================================================

        app.addListeners(
                (ApplicationListener<ApplicationFailedEvent>) event ->
                        logger.error(
                                "[FAILED] Application failed to start",
                                event.getException()
                        )
        );


        // =========================================================
        // START SPRING APPLICATION
        // =========================================================

        ConfigurableApplicationContext context =
                app.run(args);


        // =========================================================
        // FINAL SUMMARY
        // =========================================================

        logger.info(
                "[SUMMARY] Application = {}",
                context.getEnvironment()
                        .getProperty(
                                "spring.application.name"
                        )
        );

        logger.info(
                "[SUMMARY] Total bean definitions = {}",
                context.getBeanDefinitionCount()
        );
    }


    // =============================================================
    // BEAN SUMMARY
    // =============================================================

    private static void logBeanSummary(
            ConfigurableApplicationContext context) {

        var beanFactory =
                context.getBeanFactory();


        String[] beanNames =
                context.getBeanDefinitionNames();


        int singletonCount = 0;
        int prototypeCount = 0;
        int otherCount = 0;


        logger.info(
                "=================================================="
        );

        logger.info(
                "              SPRING IOC BEAN SUMMARY"
        );

        logger.info(
                "=================================================="
        );


        /*
         * IMPORTANT:
         *
         * Spring's default scope is "singleton".
         *
         * When BeanDefinition.getScope() returns an empty
         * string, it means the default scope is being used.
         *
         * Therefore we explicitly treat blank scope as
         * BeanDefinition.SCOPE_SINGLETON.
         */


        for (String beanName : beanNames) {

            try {

                BeanDefinition beanDefinition =
                        beanFactory.getBeanDefinition(
                                beanName
                        );


                String scope =
                        beanDefinition.getScope();


                // Spring default scope = singleton
                if (scope == null || scope.isBlank()) {

                    scope =
                            BeanDefinition.SCOPE_SINGLETON;
                }


                // -------------------------------------------------
                // SINGLETON
                // -------------------------------------------------

                if (BeanDefinition.SCOPE_SINGLETON.equals(scope)) {

                    singletonCount++;


                    /*
                     * ApplicationReadyEvent occurs after the
                     * application context has been refreshed.
                     *
                     * Therefore normal singleton beans have
                     * already been created.
                     *
                     * getBean() returns the existing singleton
                     * instance.
                     */

                    Object bean =
                            context.getBean(beanName);


                    Class<?> beanType =
                            context.getType(beanName);


                    int objectId =
                            System.identityHashCode(bean);


                    logger.info(
                            "[SINGLETON] " +
                                    "Bean = {} | " +
                                    "Type = {} | " +
                                    "Scope = {} | " +
                                    "Object ID = {}",
                            beanName,
                            beanType != null
                                    ? beanType.getName()
                                    : "unknown",
                            scope,
                            objectId
                    );
                }


                // -------------------------------------------------
                // PROTOTYPE
                // -------------------------------------------------

                else if (
                        BeanDefinition.SCOPE_PROTOTYPE
                                .equals(scope)
                ) {

                    prototypeCount++;


                    logger.debug(
                            "[PROTOTYPE] Bean = {} | Scope = {}",
                            beanName,
                            scope
                    );
                }


                // -------------------------------------------------
                // OTHER SCOPES
                // -------------------------------------------------

                else {

                    otherCount++;


                    logger.debug(
                            "[OTHER] Bean = {} | Scope = {}",
                            beanName,
                            scope
                    );
                }


            } catch (Exception exception) {

                logger.warn(
                        "[BEAN] Could not inspect bean = {}",
                        beanName
                );
            }
        }


        // =========================================================
        // SCOPE SUMMARY
        // =========================================================

        logger.info(
                "--------------------------------------------------"
        );

        logger.info(
                "[BEAN COUNT] Total       = {}",
                beanNames.length
        );

        logger.info(
                "[BEAN COUNT] Singletons  = {}",
                singletonCount
        );

        logger.info(
                "[BEAN COUNT] Prototypes  = {}",
                prototypeCount
        );

        logger.info(
                "[BEAN COUNT] Other       = {}",
                otherCount
        );

        logger.info(
                "=================================================="
        );
    }
}