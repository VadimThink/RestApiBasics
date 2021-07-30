package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.InvalidParametersException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.exception.ValidationExceptionChecker;
import com.epam.esm.link.TagLinkProvider;
import com.epam.esm.logic.TagService;
import com.epam.esm.validation.RequestParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.constant.RequestParams.*;

@RestController
@Profile("prod")
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    private final TagLinkProvider tagLinkProvider;

    @Autowired
    public TagController(TagService tagService, TagLinkProvider tagLinkProvider) {
        this.tagService = tagService;
        this.tagLinkProvider = tagLinkProvider;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody @Valid TagDto tagDto, BindingResult bindingResult) throws DuplicateException {
        ValidationExceptionChecker.checkDtoValidation(bindingResult);
        TagDto newTagDto = tagService.create(tagDto);
        tagLinkProvider.provideLinks(newTagDto);
        return newTagDto;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getById(@PathVariable(ID_PATH_VARIABLE) long id) throws NoSuchEntityException {
        RequestParametersValidator.validateId(id);
        TagDto tagDto = tagService.getById(id);
        tagLinkProvider.provideLinks(tagDto);
        return tagDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getAll(
            @RequestParam(name = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(name = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size)
            throws InvalidParametersException {
        RequestParametersValidator.validatePaginationParams(page, size);
        List<TagDto> tagDtoList = tagService.getAll(page, size);
        return tagDtoList.stream()
                .peek(tagLinkProvider::provideLinks)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(ID_PATH_VARIABLE) long id) throws NoSuchEntityException {
        RequestParametersValidator.validateId(id);
        tagService.deleteById(id);
    }

    @GetMapping("/best")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getTheMostWidelyUsedTagOfUserWithHighestOrderCost() throws NoSuchEntityException {
        TagDto tagDto = tagService.findTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders();
        tagLinkProvider.provideLinks(tagDto);
        return tagDto;
    }
}
