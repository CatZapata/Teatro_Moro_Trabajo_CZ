/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package teatromoro;

import java.util.Scanner;

public class TeatroMoro {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;  // Controla si el usuario quiere salir del menu

        while (!salir) {  // Creando un bucle infinito hasta que el usuario elije salir
            System.out.println("\n--- Menu Teatro Moro ---");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opcion: ");
            String opcion = scanner.nextLine();

            if (opcion.equals("1")) {
                comprarEntrada(scanner);  // Llama a la funcion de comprar entrada
            } else if (opcion.equals("2")) {
                salir = true;
                System.out.println("Sesion finalizada ¡Adios!");
            } else {
                System.out.println("Opcion no valida. Intente nuevamente.");
            }
        }
    }

    public static void comprarEntrada(Scanner scanner) {
        String tipoEntrada = null;  // Null porque no tiene valor asignado
        String tipoTarifa = null;
        int edad = 0;  // Para poder aplicar el descuento de tercera edad
        int precio = 0; // Sin valor inicial hasta que el usuario lo asigne
        boolean datosCorrectos = false;

        while (!datosCorrectos) {
            System.out.print("Ingrese el tipo de entrada (VIP, Platea baja, Platea alta, Palcos): ");
            tipoEntrada = scanner.nextLine();  // Lee la seleccion de la persona

            System.out.print("Ingrese la tarifa (Estudiante, General): ");
            tipoTarifa = scanner.nextLine();   // Eliges la tarifa

            System.out.print("Ingrese su edad: ");  // Aqui voy a pelear con la edad y los descuentos
            boolean edadValida = false;  // Nuevo boolean para validar edad, de esta manera no tiene que reiniciar hasta arriba el codigo
            try {  // Evita que el programa explote si se pone algo que no es numero
                edad = Integer.parseInt(scanner.nextLine());  // Aqui lee lo que escribe el usuario
                if (edad <= 0 || edad > 150) { // Para los chistosos
                    System.out.println("Edad invalida. Intente nuevamente.");
                    continue; // Reinicia el bucle si la edad es imposible
                } else {
                    edadValida = true; //Sigue el codigo
                }
            } catch (NumberFormatException e) {   // Aqui toma el error de NumberFormatException e imprime el mensaje reiniciando el bucle
                System.out.println("Ingrese solo numeros. Intente nuevamente.");
                continue; // Reinicia el boolean
            }

            // Asignacion de precios, precios unicos por tipo de entrada
            if (tipoEntrada.equalsIgnoreCase("VIP")) {
                if (tipoTarifa.equalsIgnoreCase("Estudiante")) {
                    precio = 20000;
                    datosCorrectos = true;
                } else if (tipoTarifa.equalsIgnoreCase("General")) {
                    precio = 35000;
                    datosCorrectos = true;
                }
            } else if (tipoEntrada.equalsIgnoreCase("Platea baja")) {
                if (tipoTarifa.equalsIgnoreCase("Estudiante")) {
                    precio = 15000;
                    datosCorrectos = true;
                } else if (tipoTarifa.equalsIgnoreCase("General")) {
                    precio = 25000;
                    datosCorrectos = true;
                }
            } else if (tipoEntrada.equalsIgnoreCase("Platea alta")) {
                if (tipoTarifa.equalsIgnoreCase("Estudiante")) {
                    precio = 10000;
                    datosCorrectos = true;
                } else if (tipoTarifa.equalsIgnoreCase("General")) {
                    precio = 15000;
                    datosCorrectos = true;
                }
            } else if (tipoEntrada.equalsIgnoreCase("Palcos")) {
                if (tipoTarifa.equalsIgnoreCase("Estudiante")) {
                    precio = 5000;
                    datosCorrectos = true;
                } else if (tipoTarifa.equalsIgnoreCase("General")) {
                    precio = 11000;
                    datosCorrectos = true;
                }
            }

            if (!datosCorrectos) {
                System.out.println("Datos no validos. Intente nuevamente.");
                continue;
            }

            // Aqui en caso de que la persona pueda acceder a dos descuentos, como un estudiante universatario de 63 años.
            double descuento = 0;
            String MensajeDescuento = "";  //string vacio por que voy a asignarle un valor
            if (tipoTarifa.equalsIgnoreCase("Estudiante") && edad >= 60) {
                System.out.println("Usted califica para dos descuentos:");
                System.out.println("1. 10% estudiante");
                System.out.println("2. 15% tercera edad");
                System.out.print("Seleccione el descuento a aplicar (1 o 2): ");
                String opcionDescuento = scanner.nextLine();
                if (opcionDescuento.equals("1")) {
                    descuento = precio * 0.10;
                    MensajeDescuento = "Se aplicara 10% de descuento estudiante";
                } else if (opcionDescuento.equals("2")) {
                    descuento = precio * 0.15;
                    MensajeDescuento = "Se aplicara 15% de descuento tercera edad";
                } else { // Al no elegir una opcion valida se elige automaticamente el descuento mayor para evitar problemas
                    System.out.println("Opcion invalida, se aplicara automaticamente el mayor descuento.");
                    descuento = precio * 0.15;
                    MensajeDescuento = "Se aplicara 15% de descuento por tercera edad";
                }
            } else if (tipoTarifa.equalsIgnoreCase("Estudiante")) {
                descuento = precio * 0.10;
                MensajeDescuento = "Se aplicara 10% de descuento estudiante";
            } else if (edad >= 60) {
                descuento = precio * 0.15;
                MensajeDescuento = "Se aplicara 15% de descuento tercera edad";
            } else {
                descuento = 0;
                MensajeDescuento = "No aplica descuento";
            }

            double totalPagar = precio - descuento;

            // Muestra mensaje de descuento antes de terminar para confirmar que este correcto.
            System.out.println("\n" + MensajeDescuento);
            System.out.print("Desea confirmar la compra? (si/no): ");
            String confirmar = scanner.nextLine();
            if (confirmar.equalsIgnoreCase("si")) {
                System.out.println("\n--- Resumen de la compra ---");
                System.out.println("Tipo de entrada: " + tipoEntrada);
                System.out.println("Tarifa: " + tipoTarifa);
                System.out.println("Edad: " + edad);
                System.out.println("Precio base: $" + precio);
                System.out.println("Descuento aplicado: $" + (int)descuento);
                System.out.println("Total a pagar: $" + (int)totalPagar);
                System.out.println("Gracias por su compra, disfrute la funcion.");
                break; // punto de quiebre para detener el bucle.
            } else {
                System.out.println("Compra cancelada. Reiniciando programa.");
            }
        }
    }
}
