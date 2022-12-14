package org.cy.java.jdbc.repositorio;

import org.cy.java.jdbc.modelo.Categoria;
import org.cy.java.jdbc.modelo.Producto;
import org.cy.java.jdbc.util.ConexionBasedeDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorio implements  Repositorio<Producto>{

    private Connection getConnection() throws SQLException {
        return ConexionBasedeDatos.getInstance();
    }


    @Override
    public List<Producto> listar() throws SQLException {

        List<Producto> productos = new ArrayList<>();

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.*, c.nombre AS categoria FROM productos AS p inner join categorias AS c ON (p.categoriaId = c.id)")) {
            while (rs.next()) {
                Producto p = crearPoducto(rs);
                productos.add(p);
            }

        }
        return productos;
    }


    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;
        try (PreparedStatement stmt = getConnection().prepareStatement("SELECT p.*, c.nombre AS categoria FROM productos AS p inner join categorias AS c ON (p.categoriaId = c.id) WHERE p.id = ?")) {

            stmt.setLong(1, id);   //asignamos el parametro del id
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = crearPoducto(rs);
                }
            }
        }
        return producto;
    }

    @Override
    public void guardar(Producto producto) throws SQLException {

        String sql;
        if (producto.getId() != null && producto.getId() > 0) {  // si el id es mayor a 0 es un update
            sql = "UPDATE productos SET nombre =?, precio=? , categoriaId=?, sku=? WHERE id=?";
        } else {
            sql = "INSERT INTO productos(nombre,precio,categoriaId,sku,fecha_registro)VALUES(?,?,?,?,?)";
        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setLong(2, producto.getPrecio());
            stmt.setLong(3, producto.getCategoria().getId());
            stmt.setString(4, producto.getSku());

            if (producto.getId() != null && producto.getId() > 0) { // si el id es mayor a 0 es un update, por lo tanto setea el id sino hace un inserte de date en un nuevo registro
                stmt.setLong(5, producto.getId());
            } else {
                stmt.setDate(5, new Date(producto.getFechaRegistro().getTime()));
            }
            stmt.executeUpdate();

        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM productos WHERE id = ?")) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        }
    }

    private static Producto crearPoducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setFechaRegistro(rs.getDate("fecha_registro"));
        p.setSku(rs.getString("sku"));
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("categoriaId"));
        categoria.setNombre(rs.getString("categoria"));
        p.setCategoria(categoria);
        return p;
    }
}
