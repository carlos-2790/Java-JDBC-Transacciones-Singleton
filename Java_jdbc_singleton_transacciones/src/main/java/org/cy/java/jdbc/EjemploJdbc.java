package org.cy.java.jdbc;

import org.cy.java.jdbc.modelo.Categoria;
import org.cy.java.jdbc.modelo.Producto;
import org.cy.java.jdbc.repositorio.ProductoRepositorio;
import org.cy.java.jdbc.repositorio.Repositorio;
import org.cy.java.jdbc.util.ConexionBasedeDatos;

import java.sql.*;
import java.util.Date;

public class EjemploJdbc {
    public static void main(String[] args) {


        try  (Connection conn = ConexionBasedeDatos.getInstance()){

            Repositorio<Producto>repositorio = new ProductoRepositorio();

            System.out.println("==================== Listar ====================");
            repositorio.listar().forEach(System.out::println);// lista la completa

            System.out.println("==================== Obtener por Id ====================");
            System.out.println(repositorio.porId(2L));// lista solo el Id 2

            System.out.println("==================== Insertar nuevo producto ====================");

            Producto producto = new Producto();
            producto.setNombre("Monitor Led");
            producto.setPrecio(950);
            producto.setFechaRegistro(new Date());
            Categoria categoria =new Categoria();
            categoria.setId(3L);
            producto.setCategoria(categoria);
            repositorio.guardar(producto);
            System.out.println("Producto guardado con éxito");
            repositorio.listar().forEach(System.out::println);// lista la completa








        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

