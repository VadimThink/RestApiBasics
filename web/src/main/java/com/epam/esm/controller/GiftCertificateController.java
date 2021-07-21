package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.UpdateGiftCertificateDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.InvalidParametersException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.exception.ValidationExceptionChecker;
import com.epam.esm.logic.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.epam.esm.constant.RequestParams.*;

/**
 * The type Gift certificate controller.
 */
@RestController
@Profile("prod")
@RequestMapping("/gift_certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    /**
     * Instantiates a new Gift certificate controller.
     *
     * @param giftCertificateService the gift certificate service
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Create gift certificate dto.
     *
     * @param giftCertificateDto the gift certificate dto
     * @param bindingResult      the binding result
     * @return the gift certificate dto
     * @throws DuplicateException    the duplicate exception
     * @throws NoSuchEntityException the no such entity exception
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody @Valid GiftCertificateDto giftCertificateDto,
                                     BindingResult bindingResult) throws DuplicateException, NoSuchEntityException {
        ValidationExceptionChecker.checkDtoValidation(bindingResult);
        return giftCertificateService.create(giftCertificateDto);
    }

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws NoSuchEntityException the no such entity exception
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto getById(@PathVariable("id") long id) throws NoSuchEntityException {
        GiftCertificateDto result = giftCertificateService.findById(id);
        return result;
    }

    /**
     * Update by id gift certificate dto.
     *
     * @param id                 the id
     * @param giftCertificateDto the gift certificate dto
     * @param bindingResult      the binding result
     * @return the gift certificate dto
     * @throws NoSuchEntityException the no such entity exception
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateById(@PathVariable("id") long id,
                                         @RequestBody @Valid UpdateGiftCertificateDto giftCertificateDto,
                                         BindingResult bindingResult) throws NoSuchEntityException {
        ValidationExceptionChecker.checkDtoValidation(bindingResult);
        return giftCertificateService.updateById(id, giftCertificateDto);
    }

    /**
     * Replace by id gift certificate dto.
     *
     * @param id                 the id
     * @param giftCertificateDto the gift certificate dto
     * @param bindingResult      the binding result
     * @return the gift certificate dto
     * @throws NoSuchEntityException the no such entity exception
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto replaceById(@PathVariable("id") long id,
                                          @RequestBody @Valid GiftCertificateDto giftCertificateDto,
                                          BindingResult bindingResult) throws NoSuchEntityException {
        ValidationExceptionChecker.checkDtoValidation(bindingResult);
        return giftCertificateService.replaceById(id, giftCertificateDto);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @throws NoSuchEntityException the no such entity exception
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") long id) throws NoSuchEntityException {
        giftCertificateService.deleteById(id);
    }

    /**
     * Gets all by tags.
     *
     * @param tagNames     the tag names
     * @param partName    the part name
     * @param sortColumns the sort columns
     * @param orderTypes  the order types
     * @param page the page number
     * @param size the size
     * @return the all by tags
     * @throws NoSuchEntityException the no such entity exception
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> getAllByTags(
            @RequestParam(name = TAG_NAME, required = false) List<String> tagNames,
            @RequestParam(name = PART_NAME, required = false) String partName,
            @RequestParam(name = SORT, required = false) List<String> sortColumns,
            @RequestParam(name = ORDER, required = false) List<String> orderTypes,
            @RequestParam(name = PAGE, required = false) int page,
            @RequestParam(name = SIZE, required = false) int size)
    throws NoSuchEntityException, InvalidParametersException {
        return giftCertificateService.findBySearchParams(tagNames, partName, sortColumns, orderTypes, page, size);
    }
}
