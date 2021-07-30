package com.epam.esm.link;

import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public abstract class AbstractLinkProvider<T extends RepresentationModel<T>> implements LinkProvider<T> {

    protected final static String SELF_LINK = "self";
    protected final static String DELETE_LINK = "delete";
    protected final static String UPDATE_LINK = "update";
    protected final static String REPLACE_LINK = "replace";

    protected void provideIdLink(Class<?> controllerClass, T dto, long id, String linkName) {
        dto.add(linkTo(controllerClass).slash(id).withRel(linkName));
    }

    protected void provideIdLinks(Class<?> controllerClass, T dto, long id, String... linkNames) {
        for (String linkName : linkNames) {
            provideIdLink(controllerClass, dto, id, linkName);
        }
    }

}
