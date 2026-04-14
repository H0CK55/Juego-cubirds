package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;

public class Player {

    private String name;
    private List<Card> mano;

    public Player(String name) {
        this.name = name;
        this.mano = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    // Añade una carta en la posicion correcta para mantener la mano ordenada por especie
    public void addCard(Card card) {
        int i = 0;
        while (i < mano.size() && mano.get(i).getTypeBird().ordinal() <= card.getTypeBird().ordinal()) {
            i++;
        }
        mano.add(i, card);
    }

    // Devuelve la mano como String
    public String showHand() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mano de ").append(name).append(": ");
        for (Card card : mano) {
            sb.append(card).append(" ");
        }
        return sb.toString();
    }
}