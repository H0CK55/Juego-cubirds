package gal.uvigo.esei.aed1.cubirds.core;
 
import java.util.ArrayList;
 
public class Table {
 
    private ArrayList<ArrayList<Card>> rows;
 
    public Table() {
        rows = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rows.add(new ArrayList<>());
        }
    }
 
    // Funcion que devuelve true si se puede colocar la carta en esa fila
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
            rows.get(fila).add(card);
        }
    }
 
    // Funcion que devuelve el estado de la mesa como String
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
 