package dao;

import entities.Producto;

public interface ProductoDAO extends EntityDAO<Producto>{
    Producto getProductoMasVendido();
}
