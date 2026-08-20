package dao;

import java.util.Collection;

public interface EntityDAO <T>{
  void crear(T entity);
  void crear(Collection<T> entites);
}
