package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.InvalidParametersException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.exception.ValidationExceptionChecker;
import com.epam.esm.logic.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.epam.esm.constant.RequestParams.PAGE;
import static com.epam.esm.constant.RequestParams.SIZE;

@RestController
@Profile("prod")
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody @Valid TagDto tagDto, BindingResult bindingResult) throws DuplicateException {
        ValidationExceptionChecker.checkDtoValidation(bindingResult);
        return tagService.create(tagDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getById(@PathVariable("id") long id) throws NoSuchEntityException {
        return tagService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getAll(
            @RequestParam(name = PAGE, required = false) int page,
            @RequestParam(name = SIZE, required = false) int size) throws InvalidParametersException {
        return tagService.getAll(page, size);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") long id) throws NoSuchEntityException {
        tagService.deleteById(id);
    }
}
