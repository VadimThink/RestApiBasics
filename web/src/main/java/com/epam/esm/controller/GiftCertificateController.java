package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/gift_certificate")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody GiftCertificateDto giftCertificateDto,
                       HttpServletResponse response) throws DuplicateException {
        long id = giftCertificateService.create(giftCertificateDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto getById(@PathVariable("id") long id) throws NoSuchEntityException {
        return giftCertificateService.getById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateById(@PathVariable("id") long id,
                                         @RequestBody GiftCertificateDto giftCertificateDto) throws NoSuchEntityException {
        return giftCertificateService.updateById(id, giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") long id) {
        giftCertificateService.deleteById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> getAllByTags(
            @RequestParam(name = "tag_name", required = false) String tagName,
            @RequestParam(name = "part_name", required = false) String partName,
            @RequestParam(name = "sort", required = false) List<String> sortColumns,
            @RequestParam(name = "order", required = false) List<String> orderTypes) {
        return giftCertificateService.getBySearchParams(tagName, partName, sortColumns, orderTypes);
    }
}
