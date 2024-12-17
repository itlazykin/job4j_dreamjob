package ru.job4j.dreamjob.repository;


import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {
    private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();
    private int nextId = 1;
    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(1, "Денис Лазыкин", "Опыт работы 7 месяцев",
                LocalDateTime.of(2024, 6, 24, 14, 21)));
        save(new Candidate(2, "Вася Медведев", "Опыт работы 4 года",
                LocalDateTime.of(2024, 6, 23, 14, 28)));
        save(new Candidate(3, "Сева Ловкачев", "Опыт работы 6 лет",
                LocalDateTime.of(2024, 6, 23, 14, 43)));
        save(new Candidate(4, "Сергей Орлов", "Опыт работы 2 года",
                LocalDateTime.of(2024, 6, 27, 15, 28)));
        save(new Candidate(5, "Идрак Мерзализаде", "Опыт работы 3 года",
                LocalDateTime.of(2024, 6, 28, 14, 31)));
        save(new Candidate(6, "Артур Чапорян", "Без опыта",
                LocalDateTime.of(2024, 6, 29, 17, 13)));
    }

    public static MemoryCandidateRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public void deleteById(int id) {
        candidates.remove(id);
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(),
                (id, oldCandidate) -> new Candidate(
                        oldCandidate.getId(),
                        candidate.getName(),
                        candidate.getDescription(),
                        candidate.getCreateDate()
                )) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
