package ru.job4j.dreamjob.repository;


import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.util.*;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {
    private int nextId = 1;
    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(1, "Денис Лазыкин", "Опыт работы 7 месяцев"));
        save(new Candidate(2, "Вася Медведев", "Опыт работы 4 года"));
        save(new Candidate(3, "Сева Ловкачев", "Опыт работы 6 лет"));
        save(new Candidate(4, "Сергей Орлов", "Опыт работы 2 года"));
        save(new Candidate(5, "Идрак Мерзализаде", "Опыт работы 3 года"));
        save(new Candidate(6, "Артур Чапорян", "Без опыта"));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(),
                (id, oldCandidate) -> new Candidate(
                        oldCandidate.getId(),
                        candidate.getName(),
                        candidate.getDescription()
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
