package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;

public class Table {

    private List<List<Card>> rows;

    public Table() {
        rows = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            rows.addLast(new LinkedList<>());
        }
    }

    // Devuelve true si se puede colocar la carta en esa fila sin repetir especie
    public boolean canPlace(int fila, Card card) {
        for (Card c : rows.get(fila)) {
            if (c.getTypeBird() == card.getTypeBird()) {
                return false;
            }
        }
        return true;
    }

    // Coloca una carta en la fila indicada si hay espacio disponible
    public void placeCard(int fila, Card card) {
        if (rows.get(fila).size() < 3) {
            rows.get(fila).addLast(card);
        }
    }

    // Devuelve el estado de la mesa como String
    public String showTable() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append("Fila ").append(i + 1).append(": ");
            for (Card card : rows.get(i)) {
                sb.append(card).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // Añade cartas al lado izquierdo o derecho de la fila indicada

    public void addLeft(int fila, List<Card> cards) {
        for (Card card : cards) {
            rows.get(fila).addFirst(card);
        }
    }

    public void addRight(int fila, List<Card> cards) {
        for (Card card : cards) {
            rows.get(fila).addLast(card);
        }
    }

    // Recoge las cartas rodeadas de la especie dada en la fila indicada
    // Recoge las cartas rodeadas de una especie en una fila
    public List<Card> collectSurrounded(int fila, TypeBird type, boolean left) {
        List<Card> surrounded = new LinkedList<>();
        List<Card> row = rows.get(fila);

        int first = -1;
        int last = -1;

        for (int i = 0; i < row.size(); i++) {
            if (row.get(i).getTypeBird() == type) {
                if (first == -1) {
                    first = i;
                }
                last = i;
            }
        }

        // Si no hay al menos dos cartas de la especie, no se rodea nada
        if (first == -1 || first == last) {
            return surrounded;
        }

        // Recoge las cartas entre ambas especies
        for (int i = first + 1; i < last; i++) {
            surrounded.addLast(row.get(i));
        }

        // Elimina esas cartas de la fila
        for (int i = last - 1; i > first; i--) {
            row.remove(i);
        }

        return surrounded;
    }

    // Devuelve true si todas las cartas de la fila son de la misma especie
    public boolean hasOnlyOneSpecies(int fila) {
        if (rows.get(fila).isEmpty()) {
            return false;
        }
        TypeBird type = rows.get(fila).getFirst().getTypeBird();
        for (Card card : rows.get(fila)) {
            if (card.getTypeBird() != type) {
                return false;
            }
        }
        return true;
    }

    // Devuelve la especie de la primera carta de la fila
    public TypeBird getFirstSpecies(int fila) {
        if (rows.get(fila).isEmpty()) {
            return null;
        }
        return rows.get(fila).getFirst().getTypeBird();
    }

}