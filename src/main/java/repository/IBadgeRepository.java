package repository;

import java.util.List;

public interface IBadgeRepository<E> {
    List<E> getAll();
}
