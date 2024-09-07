package com.ventas;

import java.io.*;
import java.util.*;

/**
 * Clase para generar archivos de información de vendedores y productos.
 */
public class GenerateInfoFiles {

    public static String[] NOMBRES = {"Juan", "Ana", "Luis", "Maria", "Pedro", "Laura", "Jorge", "Marta"};
    public static final String[] APELLIDOS = {"Perez", "Gomez", "Rodriguez", "Lopez", "Diaz", "Martinez"};
    public static final String[] PRODUCTOS = {"Laptop", "Telefono", "Tablet", "Monitor", "Teclado", "Raton"};

    // Carpeta donde se generarán los archivos
    private static final String INFO_FOLDER = "informacion/";

    /**
     * Genera un archivo de ventas para un vendedor.
     * 
     * @param randomSalesCount Número de ventas aleatorias.
     * @param name Nombre del vendedor.
     * @param id Identificación del vendedor.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public static void createSalesMenFile(int randomSalesCount, String name, long id) throws IOException {
        try (FileWriter writer = new FileWriter(INFO_FOLDER + "vendedor_" + id + ".txt")) {
            Random random = new Random();
            writer.write("ID_Producto;Cantidad\n"); // Encabezado para el archivo de ventas
            for (int i = 0; i < randomSalesCount; i++) {
                int productId = random.nextInt(PRODUCTOS.length) + 1;
                int cantidad = random.nextInt(10) + 1;
                writer.write(productId + ";" + cantidad + "\n");
            }
        }
        System.out.println("Archivo de ventas para " + name + " generado.");
    }

    /**
     * Genera un archivo con información de productos.
     * 
     * @param productsCount Número de productos a generar.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public static void createProductsFile(int productsCount) throws IOException {
        try (FileWriter writer = new FileWriter(INFO_FOLDER + "productos.csv")) {
            writer.write("'ID del Producto;Nombre del Producto;Precio\n");  // Encabezado CSV
            Random random = new Random();
            for (int i = 0; i < productsCount; i++) {
                int productId = i + 1;
                String productName = PRODUCTOS[random.nextInt(PRODUCTOS.length)];
                double price = 100 + (500 - 100) * random.nextDouble(); // Precios entre 100 y 500
                writer.write(productId + ";" + productName + ";" + String.format("%.2f", price) + "\n");
            }
        }
        System.out.println("Archivo de productos generado.");
    }

    /**
     * Genera un archivo con información de vendedores.
     * 
     * @param salesmanCount Número de vendedores a generar.
     * @throws IOException Si ocurre un error al escribir el archivo.
     * @return Lista de IDs generados para los vendedores.
     */
    public static List<Long> createSalesManInfoFile(int salesmanCount) throws IOException {
        List<Long> ids = new ArrayList<>();
        try (FileWriter writer = new FileWriter(INFO_FOLDER + "vendedores.csv")) {
            writer.write("Tipo de Documento;ID del Vendedor;Nombre;Apellido\n");  // Encabezado CSV
            Random random = new Random();
            for (int i = 0; i < salesmanCount; i++) {
                String nombre = NOMBRES[random.nextInt(NOMBRES.length)];
                String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)];
                long id = 1000000000L + random.nextInt(900000000);
                ids.add(id);
                writer.write("CC;" + id + ";" + nombre + ";" + apellido + "\n");
            }
        }
        System.out.println("Archivo de vendedores generado.");
        return ids;
    }

    /**
     * Método principal para ejecutar la generación de archivos.
     * 
     * @param args Argumentos de línea de comando.
     */
    public static void main(String[] args) {
        try {
            new File(INFO_FOLDER).mkdir();  // Asegurarse de que la carpeta existe
            List<Long> ids = createSalesManInfoFile(10);
            createProductsFile(6);

            // Genera archivos de ventas para cada vendedor (con 5 ventas por vendedor)
            for (int i = 0; i < ids.size(); i++) {
                createSalesMenFile(5, NOMBRES[i % NOMBRES.length], ids.get(i));
            }
        } catch (IOException e) {
            System.err.println("Error al generar archivos: " + e.getMessage());
        }
    }
}
