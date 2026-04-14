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
}