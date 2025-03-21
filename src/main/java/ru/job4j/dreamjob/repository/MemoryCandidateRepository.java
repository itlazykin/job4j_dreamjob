package ru.job4j.dreamjob.repository;


import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class MemoryCandidateRepository implements CandidateRepository {
    private final AtomicInteger nextId = new AtomicInteger(1);
    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(1, "Денис Лазыкин", "Опыт работы 7 месяцев", LocalDateTime.now(), 1, 0));
        save(new Candidate(2, "Вася Медведев", "Опыт работы 4 года", LocalDateTime.now(), 2, 0));
        save(new Candidate(3, "Сева Ловкачев", "Опыт работы 6 лет", LocalDateTime.now(), 3, 0));
        save(new Candidate(4, "Сергей Орлов", "Опыт работы 2 года", LocalDateTime.now(), 1, 0));
        save(new Candidate(5, "Идрак Мерзализаде", "Опыт работы 3 года", LocalDateTime.now(), 2, 0));
        save(new Candidate(6, "Артур Чапорян", "Без опыта", LocalDateTime.now(), 3, 0));
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
                        candidate.getCreationDate(),
                        candidate.getCityId(),
                        candidate.getFileId()
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