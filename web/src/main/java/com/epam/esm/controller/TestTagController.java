package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Profile("test")
@RequestMapping("/tags")
public class TestTagController {

    @PostMapping
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public TagDto create(@RequestBody TagDto tagDto) {
        TagDto newTagDto = new TagDto();
        newTagDto.setName("Fake");
        return newTagDto;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public TagDto getById(@PathVariable("id") long id) {
        TagDto newTagDto = new TagDto();
        newTagDto.setName("Fake");
        return newTagDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public List<TagDto> getAll() {
        return new ArrayList<>();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public void deleteById(@PathVariable("id") long id) {
    }

}
