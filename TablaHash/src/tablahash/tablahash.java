/*
Daniel Estuardo Saban Monroy     0901 19 1587
 */
package tablahash;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class tablahash {

    private int tamaño;
    private List<Entrada>[] tabla;

    public tablahash(int tamaño) {
        this.tamaño = tamaño;
        tabla = new ArrayList[tamaño];
        for (int i = 0; i < tamaño; i++) {
            tabla[i] = new ArrayList<>();
        }
    }

    public int hash(Object clave) {
        int hash = clave.hashCode();
        hash = hash % tamaño;
        return hash;
    }

    public void insertar(Object clave, Object valor) {
        int hash = hash(clave);
        if (tabla[hash].contains(clave)) {
            System.out.println("  Se produjo una colisión al insertar la clave: " + clave);
        } else if (tabla[hash].size() == tamaño) {
            System.out.println("  La tabla está llena, no se puede insertar la clave: " + clave);
        } else {
            tabla[hash].add(new Entrada(clave, valor));
            System.out.println("  Se ha insertado la clave: " + clave);
        }
    }

    public Object buscar(Object clave) {
        int hash = hash(clave);
        for (Entrada entrada : tabla[hash]) {
            if (entrada.clave.equals(clave)) {
                return entrada.valor;
            }
        }
        return null;
    }

    public void eliminar(Object clave) {
        int hash = hash(clave);
        for (int i = 0; i < tabla[hash].size(); i++) {
            if (tabla[hash].get(i).clave.equals(clave)) {
                System.out.println("  La clave " + clave + " ha sido eliminada.");
                tabla[hash].remove(i);
                break;
            } else {
                System.out.println("  La clave no existe");
            }
        }
    }

    public void reporte() {
        for (int i = 0; i < tamaño; i++) {
            for (Entrada entrada : tabla[i]) {
                System.out.println("|  Índice: " + i + " Clave: " + entrada.clave + " Valor: " + entrada.valor);
            }
        }
    }

    private class Entrada {

        private Object clave;
        private Object valor;

        public Entrada(Object clave, Object valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int tamañoTabla = 10;
        int numClaves = 0;
        tablahash tablaHash = new tablahash(tamañoTabla);

        int opcion = 0;
        Scanner escaner = new Scanner(System.in);
        while (true) {
            System.out.println("|------------------------------------------------|");
            System.out.println("|             Ingrese una Opcion (1-6)           |");
            System.out.println("|------------------------------------------------|");
            System.out.println("|  1. Ingreso de Claves(Manual)                  |");
            System.out.println("|  2. Ingreso de Claves(Archivo)                 |");
            System.out.println("|  3. Eliminacion de Claves                      |");
            System.out.println("|  4. Buscar                                     |");
            System.out.println("|  5. Reporte                                    |");
            System.out.println("|  6. Salir                                      |");
            System.out.println("|------------------------------------------------|");
            opcion = escaner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("|------------------------------------------------|");
                    System.out.println("|         Ingrese una clave( " + numClaves + " )-----------------|");
                    System.out.println("|------------------------------------------------|");
                    System.out.println("|   Ingreso de Clave...                         |");
                    String claves = escaner.next();
                    System.out.println("|   Ingreso de nombre de Clave...               |");
                    String nombre = escaner.next();
                    tablaHash.insertar(claves, nombre);
                    numClaves++;

                    break;
                case 2:
                    System.out.println("|------------------------------------------------|");
                    System.out.println("|             Ingreso de Archivo                 |");
                    System.out.println("|------------------------------------------------|");
                    System.out.print("  Ingrese el nombre del archivo: ");
                    String nombreArchivo = escaner.next();
                    leerDesdeArchivo(nombreArchivo, tablaHash);

                    break;

                case 3:
                    System.out.println("|------------------------------------------------|");
                    System.out.println("|             Eliminacion de Clave               |");
                    System.out.println("|------------------------------------------------|");
                    System.out.println("  Clave a Eliminar: ");
                    String elim = escaner.next();
                    tablaHash.eliminar(elim);
                    break;

                case 4:
                    System.out.println("|------------------------------------------------|");
                    System.out.println("|             Buscar Clave                       |");
                    System.out.println("|------------------------------------------------|");
                    System.out.println("   Buscar clave: ");
                    String clave = escaner.next();
                    Object resultado = tablaHash.buscar(clave);
                    if (resultado != null) {
                        System.out.println("  El valor asociado a la clave '" + clave + "' es: " + resultado);
                    } else {
                        System.out.println("  No se encontró ningún valor para la clave: " + clave);
                    }
                    break;
                case 5:
                    System.out.println("|------------------------------------------------|");
                    System.out.println("|             Reporte de Datos                   |");
                    System.out.println("|------------------------------------------------|");
                    tablaHash.reporte();
                    break;
                case 6:
                        
                    System.out.println("  Saliendo del programa...");
                    escaner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("  Opcion no valida...");
                    break;

            }

        }
    }

    private static void leerDesdeArchivo(String nombreArchivo, tablahash tablaHash) {
        File archivo = new File(nombreArchivo);

        try {
            Scanner archivoScanner = new Scanner(archivo);

            while (archivoScanner.hasNextLine()) {
                String linea = archivoScanner.nextLine();
                String[] parClaveValor = linea.split(",");
                tablaHash.insertar(parClaveValor[0], parClaveValor[1]);
            }

            archivoScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("  El archivo no existe.");
        }
    }
}
