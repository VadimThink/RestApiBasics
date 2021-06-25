package com.epam.esm.logic;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;

import java.util.List;

public interface TagService {

    TagDto create(TagDto tagDto) throws DuplicateException;
    List<TagDto> getAll();
    TagDto getById(long id) throws NoSuchEntityException;
    void deleteById(long id);
}
