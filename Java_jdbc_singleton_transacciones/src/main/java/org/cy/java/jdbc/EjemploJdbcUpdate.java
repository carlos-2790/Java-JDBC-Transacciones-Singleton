package org.cy.java.jdbc;

import org.cy.java.jdbc.modelo.Categoria;
import org.cy.java.jdbc.modelo.Producto;
import org.cy.java.jdbc.repositorio.ProductoRepositorio;
import org.cy.java.jdbc.repositorio.Repositorio;
import org.cy.java.jdbc.util.ConexionBasedeDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class EjemploJdbcUpdate {
    public static void main(String[] args) {


        try  (Connection conn = ConexionBasedeDatos.getInstance()){

            Repositorio<Producto>repositorio = new ProductoRepositorio();

            System.out.println("==================== Listar ====================");
            repositorio.listar().forEach(System.out::println);// lista la completa

            System.out.println("==================== Obtener por Id ====================");
            System.out.println(repositorio.porId(2L));// lista solo el Id 2

            System.out.println("==================== Actualizar Producto ====================");

            Producto producto = new Producto();
            producto.setId(5L);
            producto.setNombre("Teclado Razer Blue mecánico");
            producto.setPrecio(700);
            Categoria categoria = new Categoria();
            categoria.setId(2L);
            producto.setCategoria(categoria);
            repositorio.guardar(producto);
            System.out.println("Producto actualizado con éxito");
            repositorio.listar().forEach(System.out::println);// lista la completa


            



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

