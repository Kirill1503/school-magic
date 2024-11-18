package ru.hogwarts.school_magic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school_magic.service.ParallelStreamService;

@RestController
@RequestMapping("/parallelStream")
public class ParallelStreamController {

    private final ParallelStreamService parallelStreamService;

    public ParallelStreamController(ParallelStreamService parallelStreamService) {
        this.parallelStreamService = parallelStreamService;
    }

    @GetMapping("/getSum")
    public Integer getSum() {
        return parallelStreamService.sumAll();
    }
}
