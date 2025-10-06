/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package teatromoro;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class TeatroMoro {

    static int TotalEntradasVendidas = 0;
    static double TotalIngresos = 0;

    static ArrayList<Entrada> EntradasVendidas = new ArrayList<>();
    static HashMap<String, Asiento[][]> asientosPorTipo = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        // Inicializar asientos por tipo
        String[] tipos = {"VIP", "Platea baja", "Platea alta", "Palcos"};
        for (String tipo : tipos) {
            Asiento[][] asientos = new Asiento[5][5];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    asientos[i][j] = new Asiento(i + 1, j + 1, "Disponible");
                }
            }
            asientosPorTipo.put(tipo, asientos);
        }

        while (!salir) {
            System.out.println("\n--- Menu Teatro Moro ---");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Mostrar entradas vendidas");
            System.out.println("3. Mostrar promociones");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Mostrar estado de asientos");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> comprarEntrada(scanner);
                case "2" -> mostrarEntradasVendidas();
                case "3" -> mostrarPromociones();
                case "4" -> eliminarEntrada(scanner);
                case "5" -> mostrarTodosAsientos();
                case "6" -> {
                    salir = true;
                    System.out.println("Sesion finalizada ¡Adios!");
                }
                default -> System.out.println("Opcion no valida. Intente nuevamente.");
            }
        }
    }

    // Compra de entradas
    public static void comprarEntrada(Scanner scanner) {
        System.out.print("¿Cuantas entradas desea comprar?: ");
        int cantidad;
        try {
            cantidad = Integer.parseInt(scanner.nextLine());
            if (cantidad <= 0) {
                System.out.println("Cantidad invalida.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un numero valido.");
            return;
        }

        ArrayList<Entrada> entradasTemporal = new ArrayList<>();
        ArrayList<Asiento> asientosReservados = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\n--- Entrada " + (i + 1) + " ---");
            String tipoEntrada = "";
            String tipoTarifa = "";
            int edad = 0;
            int precio = 0;
            boolean datosCorrectos = false;

            while (!datosCorrectos) {
                System.out.print("Ingrese tipo de entrada (VIP, Platea baja, Platea alta, Palcos): ");
                tipoEntrada = scanner.nextLine();
                System.out.print("Ingrese tarifa (Estudiante, General): ");
                tipoTarifa = scanner.nextLine();

                if (!tipoTarifa.equalsIgnoreCase("Estudiante") && !tipoTarifa.equalsIgnoreCase("General")) {
                    System.out.println("Opcion invalida. Se aplicara tarifa \"General\" sin descuento.");
                    tipoTarifa = "General";
                }

                System.out.print("Ingrese su edad: ");
                try {
                    edad = Integer.parseInt(scanner.nextLine());
                    if (edad <= 0 || edad > 150) {
                        System.out.println("Edad invalida.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Debe ingresar un numero.");
                    continue;
                }

                switch (tipoEntrada.toLowerCase()) {
                    case "vip" -> precio = tipoTarifa.equalsIgnoreCase("Estudiante") ? 20000 : 35000;
                    case "platea baja" -> precio = tipoTarifa.equalsIgnoreCase("Estudiante") ? 15000 : 25000;
                    case "platea alta" -> precio = tipoTarifa.equalsIgnoreCase("Estudiante") ? 10000 : 15000;
                    case "palcos" -> precio = tipoTarifa.equalsIgnoreCase("Estudiante") ? 5000 : 11000;
                    default -> {
                        System.out.println("Tipo de entrada invalido.");
                        continue;
                    }
                }
                datosCorrectos = true;
            }

            // Mostrar asientos disponibles
            System.out.println("\nSeleccione un asiento disponible:");
            // Ignora mayusculas minusculas 
                Asiento[][] asientos = null;
                for (String key : asientosPorTipo.keySet()) {
                if (key.equalsIgnoreCase(tipoEntrada)) {
                asientos = asientosPorTipo.get(key);
                tipoEntrada = key; 
                break;
    }
}

if (asientos == null) {
    System.out.println("Tipo de entrada invalido.");
    continue; 
}

            mostrarAsientos(asientos);

            boolean asientoValido = false;
            int filaSeleccionada = 0, columnaSeleccionada = 0;

            while (!asientoValido) {
                System.out.print("Seleccione fila (1-5): ");
                filaSeleccionada = Integer.parseInt(scanner.nextLine()) - 1;
                System.out.print("Seleccione columna (1-5): ");
                columnaSeleccionada = Integer.parseInt(scanner.nextLine()) - 1;

                if (filaSeleccionada >= 0 && filaSeleccionada < 5 && columnaSeleccionada >= 0 && columnaSeleccionada < 5) {
                    if (asientos[filaSeleccionada][columnaSeleccionada].getEstado().equals("Disponible")) {
                        asientoValido = true;
                        asientos[filaSeleccionada][columnaSeleccionada].setEstado("Reservado");
                        asientosReservados.add(asientos[filaSeleccionada][columnaSeleccionada]);
                    } else {
                        System.out.println("Asiento ocupado, elija otro.");
                    }
                } else {
                    System.out.println("Fila o columna invalida.");
                }
            }

            // Aplicar descuento
            double descuento = 0;
            String mensajeDescuento = "";
            if (tipoTarifa.equalsIgnoreCase("Estudiante") && edad >= 60) {
                System.out.println("Usted califica para dos descuentos: \n1. 10% estudiante, \n2. 15% tercera edad");
                System.out.print("Seleccione descuento (1 o 2): ");
                String opcion = scanner.nextLine();
                if (opcion.equals("1")) {
                    descuento = precio * 0.10;
                    mensajeDescuento = "10% estudiante";
                } else {
                    descuento = precio * 0.15;
                    mensajeDescuento = "15% tercera edad";
                }
            } else if (tipoTarifa.equalsIgnoreCase("Estudiante")) {
                descuento = precio * 0.10;
                mensajeDescuento = "10% estudiante";
            } else if (edad >= 60) {
                descuento = precio * 0.15;
                mensajeDescuento = "15% tercera edad";
            } else {
                mensajeDescuento = "Sin descuento";
            }

            double totalPagar = precio - descuento;
            System.out.println("Descuento aplicado: " + mensajeDescuento);
            System.out.println("Total a pagar: $" + (int) totalPagar);

            Entrada entrada = new Entrada(tipoEntrada, tipoTarifa, edad, precio, descuento, totalPagar);
            entradasTemporal.add(entrada);
        }

        // Confirmar compra
        System.out.print("\n¿Desea confirmar la compra de todas las entradas? (si/no): ");
        String confirmar = scanner.nextLine();
        if (confirmar.equalsIgnoreCase("si")) {
            double totalCompra = 0;
            System.out.println("\n--- Entradas Compradas ---");

            for (int i = 0; i < entradasTemporal.size(); i++) {
                Entrada entrada = entradasTemporal.get(i);
                Asiento asiento = asientosReservados.get(i);

                EntradasVendidas.add(entrada);
                TotalEntradasVendidas++;
                TotalIngresos += entrada.total;
                totalCompra += entrada.total;

                asiento.setEstado("Vendido");

                System.out.println("\nEntrada " + (i + 1));
                System.out.println("Tipo: " + entrada.getTipoEntrada());
                System.out.println("Tarifa: " + entrada.getTipoTarifa());
                System.out.println("Edad: " + entrada.getEdad());
                System.out.println("Asiento: F" + asiento.getFila() + " C" + asiento.getColumna());
                System.out.println("Descuento: $" + (int) entrada.descuento);
                System.out.println("Total a pagar: $" + (int) entrada.total);
            }

            System.out.println("\n--- Resumen de la compra ---");
            System.out.println("Cantidad de entradas: " + entradasTemporal.size());
            System.out.println("Total a pagar: $" + (int) totalCompra);
            System.out.println("Gracias por su compra, disfrute la funcion.");

        } else {
            System.out.println("Compra cancelada. Los asientos reservados se liberan.");
            for (Asiento asiento : asientosReservados) {
                asiento.setEstado("Disponible");
            }
        }
    }

    // Mostrar asientos por tipo
    public static void mostrarAsientos(Asiento[][] asientos) {
        System.out.print("    ");
        for (int c = 1; c <= asientos[0].length; c++) System.out.print("C" + c + " ");
        System.out.println();
        for (int f = 0; f < asientos.length; f++) {
            System.out.print("F" + (f + 1) + " ");
            for (int c = 0; c < asientos[f].length; c++) {
                String estado = switch (asientos[f][c].getEstado()) {
                    case "Disponible" -> "D";
                    case "Reservado" -> "R";
                    case "Vendido" -> "V";
                    default -> " ";
                };
                System.out.print("[" + estado + "]");
            }
            System.out.println();
        }
    }

    public static void mostrarTodosAsientos() {
        for (String tipo : asientosPorTipo.keySet()) {
            System.out.println("\n--- Asientos " + tipo + " ---");
            mostrarAsientos(asientosPorTipo.get(tipo));
        }
        System.out.println("\nTotal de entradas vendidas: " + TotalEntradasVendidas);
        System.out.println("Total recaudado: $" + (int) TotalIngresos);
    }

  
    public static void mostrarEntradasVendidas() {
        System.out.println("\n--- Resumen de Entradas Vendidas ---");

        if (EntradasVendidas.isEmpty()) {
            System.out.println("No hay entradas vendidas aun.");
            return;
        }

        int vip = 0, plateaBaja = 0, plateaAlta = 0, palcos = 0;
        int vipDesc = 0, plateaBajaDesc = 0, plateaAltaDesc = 0, palcosDesc = 0;
        double totalVIP = 0, totalPlateaBaja = 0, totalPlateaAlta = 0, totalPalcos = 0;

        for (Entrada e : EntradasVendidas) {
            switch(e.getTipoEntrada().toLowerCase()) {
                case "vip" -> { vip++; if (e.descuento>0) vipDesc++; totalVIP += e.total; }
                case "platea baja" -> { plateaBaja++; if(e.descuento>0) plateaBajaDesc++; totalPlateaBaja+=e.total;}
                case "platea alta" -> { plateaAlta++; if(e.descuento>0) plateaAltaDesc++; totalPlateaAlta+=e.total;}
                case "palcos" -> { palcos++; if(e.descuento>0) palcosDesc++; totalPalcos+=e.total;}
            }
        }

        System.out.printf("VIP: %d vendidas, %d con descuento, total recaudado $%.0f\n", vip, vipDesc, totalVIP);
        System.out.printf("Platea baja: %d vendidas, %d con descuento, total recaudado $%.0f\n", plateaBaja, plateaBajaDesc, totalPlateaBaja);
        System.out.printf("Platea alta: %d vendidas, %d con descuento, total recaudado $%.0f\n", plateaAlta, plateaAltaDesc, totalPlateaAlta);
        System.out.printf("Palcos: %d vendidas, %d con descuento, total recaudado $%.0f\n", palcos, palcosDesc, totalPalcos);
    }
//Mostrar promociones
    public static void mostrarPromociones() {
        System.out.println("\n--- Promociones disponibles ---");
        System.out.println("1. 2 entradas VIP: 5% descuento");
        System.out.println("2. 2 entradas Platea baja: 10% descuento");
        System.out.println("3. 2 entradas Platea alta: 10% descuento");
   

    }
//Eliminar entradas
   public static void eliminarEntrada(Scanner scanner) {
    System.out.println("\n--- Estado actual de entradas por tipo ---");
    for (String tipo : asientosPorTipo.keySet()) {
        System.out.println("\n--- Asientos " + tipo + " ---");
        mostrarAsientos(asientosPorTipo.get(tipo));
    }

    System.out.print("\nIngrese el tipo de entrada de la que desea eliminar (VIP, Platea baja, Platea alta, Palcos): ");
    String tipoEntrada = scanner.nextLine();

    Asiento[][] asientos = null;
    for (String key : asientosPorTipo.keySet()) {
        if (key.equalsIgnoreCase(tipoEntrada)) {
            asientos = asientosPorTipo.get(key);
            tipoEntrada = key; 
            break;
        }
    }

    if (asientos == null) {
        System.out.println("Tipo de entrada inválido.");
        return;
    }

    // Mostrar solo los asientos vendidos
    System.out.println("\nAsientos vendidos de tipo " + tipoEntrada + ":");
    boolean hayVendidos = false;
    for (int f = 0; f < asientos.length; f++) {
        for (int c = 0; c < asientos[f].length; c++) {
            if (asientos[f][c].getEstado().equals("Vendido")) {
                System.out.println("Fila " + (f+1) + ", Columna " + (c+1));
                hayVendidos = true;
            }
        }
    }

    if (!hayVendidos) {
        System.out.println("No hay entradas vendidas de este tipo.");
        return;
    }

    // Pedir fila y columna a eliminar
    int filaEliminar = 0, colEliminar = 0;
    try {
        System.out.print("Ingrese fila del asiento a eliminar: ");
        filaEliminar = Integer.parseInt(scanner.nextLine()) - 1;
        System.out.print("Ingrese columna del asiento a eliminar: ");
        colEliminar = Integer.parseInt(scanner.nextLine()) - 1;
    } catch (NumberFormatException e) {
        System.out.println("Debe ingresar números válidos.");
        return;
    }

    if (filaEliminar < 0 || filaEliminar >= 5 || colEliminar < 0 || colEliminar >= 5) {
        System.out.println("Fila o columna inválida.");
        return;
    }

    if (!asientos[filaEliminar][colEliminar].getEstado().equals("Vendido")) {
        System.out.println("Ese asiento no está vendido.");
        return;
    }

    
    for (int i = 0; i < EntradasVendidas.size(); i++) {
        Entrada e = EntradasVendidas.get(i);
        if (e.getTipoEntrada().equalsIgnoreCase(tipoEntrada)) {
            
            EntradasVendidas.remove(i);
            TotalEntradasVendidas--;
            TotalIngresos -= e.total;
            break;
        }
    }

    // Liberar el asiento
    asientos[filaEliminar][colEliminar].setEstado("Disponible");
    System.out.println("Entrada eliminada y asiento liberado correctamente.");
}
}

// Class para almacenar informacion
class Entrada {
    private String tipoEntrada;
    private String tipoTarifa;
    private int edad;
    private int precio;
    double descuento;
    double total;

    public Entrada(String tipoEntrada, String tipoTarifa, int edad, int precio, double descuento, double total) {
        this.tipoEntrada = tipoEntrada;
        this.tipoTarifa = tipoTarifa;
        this.edad = edad;
        this.precio = precio;
        this.descuento = descuento;
        this.total = total;
    }

    public String getTipoEntrada() { return tipoEntrada; }
    public String getTipoTarifa() { return tipoTarifa; }
    public int getEdad() { return edad; }

    @Override
    public String toString() {
        return "Entrada{" +
                "tipoEntrada='" + tipoEntrada + '\'' +
                ", tipoTarifa='" + tipoTarifa + '\'' +
                ", edad=" + edad +
                ", precio=" + precio +
                ", descuento=" + (int)descuento +
                ", total=" + (int)total +
                '}';
    }
}

// Class para mostrar los asientos
class Asiento {
    private int fila;
    private int columna;
    private String estado; // Disponible (D), Reservado(R), Vendido(V).

    public Asiento(int fila, int columna, String estado) {
        this.fila = fila;
        this.columna = columna;
        this.estado = estado;
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getFila() { return fila; }
    public int getColumna() { return columna; }
}

