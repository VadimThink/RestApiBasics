package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.UpdateGiftCertificateDto;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.constant.RequestParams.*;


@RestController
@Profile("test")
@RequestMapping("/gift_certificates")
public class TestGiftCertificateController {
    @PostMapping
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setName("Fake");
        return giftCertificateDto;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public GiftCertificateDto getById(@PathVariable("id") long id) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName("Fake");
        return giftCertificateDto;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public GiftCertificateDto updateById(@PathVariable("id") long id,
                                         @RequestBody UpdateGiftCertificateDto giftCertificateDto) {
        GiftCertificateDto newGiftCertificateDto = new GiftCertificateDto();
        newGiftCertificateDto.setName("Fake");
        return newGiftCertificateDto;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public GiftCertificateDto replaceById(@PathVariable("id") long id,
                                          @RequestBody UpdateGiftCertificateDto giftCertificateDto) {
        GiftCertificateDto newGiftCertificateDto = new GiftCertificateDto();
        newGiftCertificateDto.setName("Fake");
        return newGiftCertificateDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public void deleteById(@PathVariable("id") long id) {
    }

    @GetMapping
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public List<GiftCertificateDto> getAllByTags(
            @RequestParam(name = TAG_NAME, required = false) String tagName,
            @RequestParam(name = PART_NAME, required = false) String partName,
            @RequestParam(name = SORT, required = false) List<String> sortColumns,
            @RequestParam(name = ORDER, required = false) List<String> orderTypes) {
        return new ArrayList<>();
    }
}
