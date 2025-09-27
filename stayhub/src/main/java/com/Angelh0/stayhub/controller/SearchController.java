package com.Angelh0.stayhub.controller;

import com.Angelh0.stayhub.dto.SearchRoomDTO;
import com.Angelh0.stayhub.service.SearchService;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/checks")
    public SearchRoomDTO getChecks() {
        return searchService.getChecks();
    }
}