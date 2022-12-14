package org.cy.java.jdbc;

import org.cy.java.jdbc.modelo.Producto;
import org.cy.java.jdbc.repositorio.ProductoRepositorio;
import org.cy.java.jdbc.repositorio.Repositorio;
import org.cy.java.jdbc.util.ConexionBasedeDatos;

import java.sql.Connection;
import java.sql.SQLException;

public class EjemploJdbcDelete {
    public static void main(String[] args) {


        try  (Connection conn = ConexionBasedeDatos.getInstance()){

            Repositorio<Producto>repositorio = new ProductoRepositorio();

            System.out.println("==================== Listar ====================");
            repositorio.listar().forEach(System.out::println);// lista la completa

            System.out.println("==================== Obtener por Id ====================");
            System.out.println(repositorio.porId(2L));// lista solo el Id 2

            System.out.println("==================== Eliminar Producto ====================");

            repositorio.eliminar(3L);
            System.out.println("Producto eliminado con éxito");
            repositorio.listar().forEach(System.out::println);// lista la completa


            



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

