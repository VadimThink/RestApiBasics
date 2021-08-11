package com.epam.esm.constant;

import org.springframework.beans.factory.annotation.Value;

public final class Endpoints {

    @Value("${server.servlet.context-path}")
    public static String VERSION_ENDPOINT;

    public static final String ADMIN_ENDPOINT = VERSION_ENDPOINT + "/admin/**";
    public static final String LOGIN_ENDPOINT = VERSION_ENDPOINT + "/auth/login";
}
