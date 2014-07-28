package com.getbro.api;

import javax.ws.rs.*;
import javax.ws.rs.container;
public class ExceptionsLoggingContainerResponseFilter implements ContainerResponseFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionsLoggingContainerResponseFilter.class);

    @Override
    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
        Throwable throwable = response.getMappedThrowable();
        if (throwable != null) {
            LOGGER.info(buildErrorMessage(request), throwable);
        }

        return response;
    }

    private String buildErrorMessage(ContainerRequest request) {
        StringBuilder message = new StringBuilder();

        message.append("Uncaught REST API exception:\n");
        message.append("URL: ").append(request.getRequestUri()).append("\n");
        message.append("Method: ").append(request.getMethod()).append("\n");
        message.append("Entity: ").append(extractDisplayableEntity(request)).append("\n");

        return message.toString();
    }

    private String extractDisplayableEntity(ContainerRequest request) {
        String entity = request.getEntity(String.class);
        return entity.equals("") ? "(blank)" : entity;
    }

}