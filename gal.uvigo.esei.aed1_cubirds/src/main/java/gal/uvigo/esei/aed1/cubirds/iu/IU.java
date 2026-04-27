package gal.uvigo.esei.aed1.cubirds.iu;

import java.util.Scanner;
import es.uvigo.esei.aed1.tads.list.List;
import gal.uvigo.esei.aed1.cubirds.core.TypeBird;

public class IU {

    private final Scanner keyboard;

    public IU() {
        keyboard = new Scanner(System.in);
    }

    /**
     * Lee un num. de teclado
     *
     * @param msg El mensaje a visualizar.
     * @return El num., como entero
     */
    public int readNumber(String msg) {
        boolean repeat;
        int toret = 0;

        do {
            repeat = false;
            System.out.print(msg);
            try {
                toret = Integer.parseInt(keyboard.nextLine());
            } catch (NumberFormatException exc) {
                repeat = true;
            }
        } while (repeat);

        return toret;
    }

    public int askNumberOfPlayers() {
        int num;
        do {
            num = readNumber("¿Cuántos jugadores van a jugar? (2-5): ");
        } while (num < 2 || num > 5);
        return num;
    }

    public String askPlayerName(int i) {
        System.out.print("Introduce el nombre del jugador " + i + ": ");
        return keyboard.nextLine();
    }

    /**
     * Lee un string de teclado
     *
     * @param msg mensaje a mostrar antes de la lectura
     * @return el string leido
     */
    public String readString(String msg) {
        String toret;
        System.out.print(msg);
        toret = keyboard.nextLine();
        return toret;
    }

    /**
     * muestra un mensaje por pantalla
     *
     * @param msg el mensaje a mostrar
     */
    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    // Muestra las especies disponibles y pide al usuario que escoja una
    public TypeBird askSpecies(List<TypeBird> species) {
        // Muestra las especies numeradas
        int i = 1;
        for (TypeBird type : species) {
            System.out.println(i + ". " + type);
            i++;
        }

        // Pide un numero valido
        int choice;
        do {
            choice = readNumber("Escoge una especie: ");
        } while (choice < 1 || choice > species.size());

        // Devuelve la especie escogida
        return species.get(choice - 1);
    }

    // Pide al usuario que escoja una fila y devuelve el indice (0-3)
    public int askRow() {
        int row;
        do {
            row = readNumber("Escoge una fila (1-4): ");
        } while (row < 1 || row > 4);
        return row - 1; // devuelve 0-3 para usar como indice
    }

    // Pide al usuario que escoja un lado
    public boolean askSide() {
        int side;
        do {
            side = readNumber("Coloca a la izquierda (1) o derecha (2): ");
        } while (side < 1 || side > 2);
        return side == 1; // true si izquierda, false si derecha
    }

}
