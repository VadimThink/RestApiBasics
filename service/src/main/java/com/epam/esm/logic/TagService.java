package com.epam.esm.logic;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.InvalidParametersException;
import com.epam.esm.exception.NoSuchEntityException;

import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService {

    /**
     * Create tag dto.
     *
     * @param tagDto the tag dto
     * @return the tag dto
     * @throws DuplicateException the duplicate exception
     */
    TagDto create(TagDto tagDto) throws DuplicateException;

    /**
     * Gets all.
     *
     * @return the all
     */
    List<TagDto> getAll(int page, int size) throws InvalidParametersException;

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws NoSuchEntityException the no such entity exception
     */
    TagDto getById(long id) throws NoSuchEntityException;

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(long id) throws NoSuchEntityException;
}
