package com.epam.esm.link;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class OrderLinkProvider extends AbstractLinkProvider<OrderDto> {
    private static final Class<UserController> CONTROLLER_CLASS = UserController.class;

    private final LinkProvider<UserDto> userDtoLinkProvider;
    private final LinkProvider<GiftCertificateDto> giftCertificateDtoLinkProvider;

    public OrderLinkProvider(LinkProvider<UserDto> userDtoLinkProvider,
                             LinkProvider<GiftCertificateDto> giftCertificateDtoLinkProvider) {
        this.userDtoLinkProvider = userDtoLinkProvider;
        this.giftCertificateDtoLinkProvider = giftCertificateDtoLinkProvider;
    }

    @Override
    public void provideLinks(OrderDto dto) {
        UserDto userDto = dto.getUser();
        dto.add(linkTo(CONTROLLER_CLASS)
                .slash(userDto.getId())
                .slash("orders")
                .slash(dto.getId())
                .withRel(SELF_LINK));
        userDtoLinkProvider.provideLinks(userDto);
        giftCertificateDtoLinkProvider.provideLinks(dto.getGiftCertificate());
    }
}