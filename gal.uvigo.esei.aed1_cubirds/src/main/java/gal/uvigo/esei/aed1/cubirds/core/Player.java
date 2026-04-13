package gal.uvigo.esei.aed1.cubirds.core;
 
import java.util.ArrayList;
 
public class Player {
 
    private String name;
    private ArrayList<Card> mano;
 
    public Player(String name) {
        this.name = name;
        this.mano = new ArrayList<>();
    }
 
    public String getName() {
        return name;
    }
 
    // Funcion que añade una carta a la mano
    public void addCard(Card card) {
        int i = 0;
        while (i < mano.size() && mano.get(i).getTypeBird().ordinal() <= card.getTypeBird().ordinal()) {
            i++;
        }
        mano.add(i, card);
    }
 
    // Funcion que devuelve la mano como String
    public String showHand() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mano de ").append(name).append(": ");
        for (Card card : mano) {
            sb.append(card).append(" ");
        }
        return sb.toString();
    }
}
 