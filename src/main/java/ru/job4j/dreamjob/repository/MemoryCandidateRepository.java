package ru.job4j.dreamjob.repository;


import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class MemoryCandidateRepository implements CandidateRepository {
    private final AtomicInteger nextId = new AtomicInteger(1);
    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(1, "Денис Лазыкин", "Опыт работы 7 месяцев", 1));
        save(new Candidate(2, "Вася Медведев", "Опыт работы 4 года", 2));
        save(new Candidate(3, "Сева Ловкачев", "Опыт работы 6 лет", 3));
        save(new Candidate(4, "Сергей Орлов", "Опыт работы 2 года", 1));
        save(new Candidate(5, "Идрак Мерзализаде", "Опыт работы 3 года", 2));
        save(new Candidate(6, "Артур Чапорян", "Без опыта", 3));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId.incrementAndGet());
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
                        candidate.getDescription(),
                        candidate.getCityId()
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
