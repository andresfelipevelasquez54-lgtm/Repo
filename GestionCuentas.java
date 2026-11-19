import java.util.ArrayList;
import java.util.Scanner;

class CuentaBancaria {
    private String titular;
    private String numeroCuenta;
    private double saldo;
    private double limiteRetiro;

    public CuentaBancaria() {
        titular = "Sin asignar";
        numeroCuenta = "0000";
        saldo = 0.0;
        limiteRetiro = 500000.0;
    }

    public CuentaBancaria(String titular, String numeroCuenta, double saldo, double limiteRetiro) {
        this.titular = titular;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.limiteRetiro = limiteRetiro;
    }

    public String getTitular() { return titular; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }
    public double getLimiteRetiro() { return limiteRetiro; }

    public void setTitular(String titular) { this.titular = titular; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public void setLimiteRetiro(double limiteRetiro) { this.limiteRetiro = limiteRetiro; }

    public boolean retirar(double monto) {
        if (monto > 0 && monto <= saldo && monto <= limiteRetiro) {
            saldo -= monto;
            return true;
        }
        return false;
    }

    public void depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            if (monto > 2000000) {
                double bonificacion = monto * 0.01;
                saldo += bonificacion;
                System.out.println("Bonificacion del 1% aplicada: $" + bonificacion);
            }
        }
    }

    public String toString() {
        return "Titular: " + titular + " | Cuenta: " + numeroCuenta +
               " | Saldo: $" + String.format("%,.2f", saldo) +
               " | Limite Retiro: $" + String.format("%,.2f", limiteRetiro);
    }
}

public class GestionCuentas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<CuentaBancaria> listaCuentas = new ArrayList<>();
        int opcion;

        do {
            System.out.println("\n===== SISTEMA DE GESTION DE CUENTAS =====");
            System.out.println("1. Crear cuenta por defecto");
            System.out.println("2. Crear cuenta con datos");
            System.out.println("3. Modificar datos de una cuenta");
            System.out.println("4. Ver todas las cuentas");
            System.out.println("5. Realizar retiro");
            System.out.println("6. Realizar deposito");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opcion: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Ingrese un numero valido: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    listaCuentas.add(new CuentaBancaria());
                    System.out.println("Cuenta creada con valores por defecto");
                    break;

                case 2:
                    System.out.print("Nombre del titular: ");
                    String titular = scanner.nextLine();
                    System.out.print("Numero de cuenta: ");
                    String numCuenta = scanner.nextLine();
                    System.out.print("Saldo inicial: $");
                    while (!scanner.hasNextDouble()) scanner.next();
                    double saldo = scanner.nextDouble();
                    System.out.print("Limite de retiro: $");
                    while (!scanner.hasNextDouble()) scanner.next();
                    double limite = scanner.nextDouble();
                    scanner.nextLine();

                    if (saldo >= 0 && limite > 0) {
                        listaCuentas.add(new CuentaBancaria(titular, numCuenta, saldo, limite));
                        System.out.println("Cuenta registrada exitosamente");
                    } else {
                        System.out.println("El saldo y el limite deben ser positivos");
                    }
                    break;

                case 3:
                    if (listaCuentas.isEmpty()) {
                        System.out.println("No hay cuentas registradas");
                        break;
                    }
                    System.out.println("\n--- Cuentas registradas ---");
                    for (int i = 0; i < listaCuentas.size(); i++) {
                        System.out.println((i + 1) + ". " + listaCuentas.get(i).getTitular());
                    }
                    System.out.print("Seleccione el numero de cuenta a modificar: ");
                    int indice = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (indice >= 0 && indice < listaCuentas.size()) {
                        CuentaBancaria cuenta = listaCuentas.get(indice);
                        System.out.print("Nuevo titular: ");
                        String nuevoTitular = scanner.nextLine();
                        if (!nuevoTitular.isBlank()) cuenta.setTitular(nuevoTitular);

                        System.out.print("Nuevo numero de cuenta: ");
                        String nuevoNum = scanner.nextLine();
                        if (!nuevoNum.isBlank()) cuenta.setNumeroCuenta(nuevoNum);

                        System.out.print("Nuevo limite de retiro: $");
                        if (scanner.hasNextDouble()) {
                            double nuevoLimite = scanner.nextDouble();
                            if (nuevoLimite > 0) cuenta.setLimiteRetiro(nuevoLimite);
                        }
                        scanner.nextLine();
                        System.out.println("Datos actualizados correctamente");
                    } else {
                        System.out.println("Numero de cuenta invalido");
                    }
                    break;

                case 4:
                    if (listaCuentas.isEmpty()) {
                        System.out.println("No hay cuentas registradas");
                    } else {
                        System.out.println("\n=== LISTA DE CUENTAS ===");
                        for (int i = 0; i < listaCuentas.size(); i++) {
                            System.out.println((i + 1) + ". " + listaCuentas.get(i));
                        }
                    }
                    break;

                case 5:
                    if (listaCuentas.isEmpty()) {
                        System.out.println("No hay cuentas registradas");
                        break;
                    }
                    System.out.println("\n--- Seleccione cuenta ---");
                    for (int i = 0; i < listaCuentas.size(); i++) {
                        System.out.println((i + 1) + ". " + listaCuentas.get(i).getTitular() +
                                           " | Saldo: $" + listaCuentas.get(i).getSaldo());
                    }
                    System.out.print("Numero de cuenta: ");
                    int idxRetiro = scanner.nextInt() - 1;
                    System.out.print("Monto a retirar: $");
                    double montoRetiro = scanner.nextDouble();
                    scanner.nextLine();

                    if (idxRetiro >= 0 && idxRetiro < listaCuentas.size()) {
                        boolean exito = listaCuentas.get(idxRetiro).retirar(montoRetiro);
                        if (exito) {
                            System.out.println("Retiro realizado. Saldo actual: $" +
                                               listaCuentas.get(idxRetiro).getSaldo());
                        } else {
                            System.out.println("No se pudo retirar. Verifique el monto, saldo o limite");
                        }
                    } else {
                        System.out.println("Cuenta no encontrada");
                    }
                    break;

                case 6:
                    if (listaCuentas.isEmpty()) {
                        System.out.println("No hay cuentas registradas");
                        break;
                    }
                    System.out.println("\n--- Seleccione cuenta ---");
                    for (int i = 0; i < listaCuentas.size(); i++) {
                        System.out.println((i + 1) + ". " + listaCuentas.get(i).getTitular());
                    }
                    System.out.print("Numero de cuenta: ");
                    int idxDep = scanner.nextInt() - 1;
                    System.out.print("Monto a depositar: $");
                    double montoDep = scanner.nextDouble();
                    scanner.nextLine();

                    if (idxDep >= 0 && idxDep < listaCuentas.size()) {
                        listaCuentas.get(idxDep).depositar(montoDep);
                        System.out.println("Deposito realizado. Saldo actual: $" +
                                           listaCuentas.get(idxDep).getSaldo());
                    } else {
                        System.out.println("Cuenta no encontrada");
                    }
                    break;

                case 7:
                    System.out.println("Saliendo del sistema... Hasta luego!");
                    break;

                default:
                    System.out.println("Opcion no valida. Intente nuevamente");
            }
        } while (opcion != 7);

        scanner.close();
    }
}