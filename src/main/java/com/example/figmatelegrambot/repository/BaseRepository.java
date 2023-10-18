package com.example.figmatelegrambot.repository;

import com.example.figmatelegrambot.error.IllegalRequestDataException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T> extends CrudRepository<T, Long> {


    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id")
    T get(Long id);

    default T getExisted(Long id) {
        T t = get(id);
        if (t == null) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
        return t;
    }
}
