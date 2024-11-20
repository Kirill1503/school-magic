package ru.hogwarts.school_magic.service;

import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class ParallelStreamService {
    public Integer sumAll() {
        return Stream
                .iterate(1, a -> a +1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
    }
}
