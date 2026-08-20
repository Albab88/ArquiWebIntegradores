package dao;

import entities.Factura;

public interface EntityDAO <T>{
  void crear(T entity);
}
