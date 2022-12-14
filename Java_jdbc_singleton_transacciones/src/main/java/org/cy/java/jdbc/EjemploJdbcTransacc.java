package org.cy.java.jdbc;

import org.cy.java.jdbc.modelo.Categoria;
import org.cy.java.jdbc.modelo.Producto;
import org.cy.java.jdbc.repositorio.ProductoRepositorio;
import org.cy.java.jdbc.repositorio.Repositorio;
import org.cy.java.jdbc.util.ConexionBasedeDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class EjemploJdbcTransacc {
    public static void main(String[] args) throws SQLException {


        try (Connection conn = ConexionBasedeDatos.getInstance()) {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false); // es true x defecto
            }

            try {

                Repositorio<Producto> repositorio = new ProductoRepositorio();

                System.out.println("==================== Listar ====================");
                repositorio.listar().forEach(System.out::println);// lista la completa

                System.out.println("==================== Obtener por Id ====================");
                System.out.println(repositorio.porId(2L));// lista solo el Id 2

                System.out.println("==================== Insertar nuevo producto ====================");

                Producto producto = new Producto();
                producto.setNombre("Teclado IBM");
                producto.setPrecio(1950);
                producto.setFechaRegistro(new Date());
                Categoria categoria = new Categoria();
                categoria.setId(3L);
                producto.setCategoria(categoria);
                producto.setSku("abcde12345");
                repositorio.guardar(producto);
                System.out.println("Producto guardado con éxito");


                System.out.println("==================== Actualizar Producto ====================");

                producto.setId(5L);
                producto.setNombre("Teclado Razer Blue mecánico");
                producto.setPrecio(1000);
                producto.setSku("abcd1234");
                categoria.setId(2L);
                producto.setCategoria(categoria);
                repositorio.guardar(producto);
                System.out.println("Producto actualizado con éxito");
                repositorio.listar().forEach(System.out::println);// lista la completa

                conn.commit();// si se ejecuta el bloq, pero da algún error se ejecuta rollback y quedan sin efectos los cambios, de lo contrario se llevan a cabo los cambios.
            } catch (SQLException exception) {
                conn.rollback();
                exception.printStackTrace();
            }
        }
    }
}

