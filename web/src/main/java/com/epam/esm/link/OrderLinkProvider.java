package com.epam.esm.link;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserResponseDto;
import com.epam.esm.mapper.UserDtoResponseMapper;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class OrderLinkProvider extends AbstractLinkProvider<OrderDto> {
    private static final Class<UserController> CONTROLLER_CLASS = UserController.class;

    private final LinkProvider<UserResponseDto> userDtoLinkProvider;
    private final LinkProvider<GiftCertificateDto> giftCertificateDtoLinkProvider;
    private final UserDtoResponseMapper userDtoResponseMapper;

    public OrderLinkProvider(LinkProvider<UserResponseDto> userDtoLinkProvider,
                             LinkProvider<GiftCertificateDto> giftCertificateDtoLinkProvider,
                             UserDtoResponseMapper userDtoResponseMapper) {
        this.userDtoLinkProvider = userDtoLinkProvider;
        this.giftCertificateDtoLinkProvider = giftCertificateDtoLinkProvider;
        this.userDtoResponseMapper = userDtoResponseMapper;
    }

    @Override
    public void provideLinks(OrderDto dto) {
        UserDto userDto = dto.getUser();
        dto.add(linkTo(CONTROLLER_CLASS)
                .slash(userDto.getId())
                .slash("orders")
                .slash(dto.getId())
                .withRel(SELF_LINK));
        UserResponseDto userResponseDto = userDtoResponseMapper.mapToDto(userDto);
        userDtoLinkProvider.provideLinks(userResponseDto);
        giftCertificateDtoLinkProvider.provideLinks(dto.getGiftCertificate());
    }
}