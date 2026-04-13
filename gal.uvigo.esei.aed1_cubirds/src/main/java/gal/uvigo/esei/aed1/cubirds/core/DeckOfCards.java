package gal.uvigo.esei.aed1.cubirds.core;
 
import java.util.ArrayList;
import java.util.Collections;
 
public class DeckOfCards {
 
    private ArrayList<Card> cards;
 
    /**
     * Constructor: crea una baraja de cartas ordenada a partir del enumerado
     */
    public DeckOfCards() {
        cards = new ArrayList<>();
        for (Card card : Card.values()) {
            cards.add(card);
        }
    }
 
    // Funcion encargada de mezclar la baraja
    public void Shuffle() {
        Collections.shuffle(cards);
    }
 
    // Funcion encargada de repartir una carta
    public Card dealCard() {
        return cards.remove(0);
    }
 
    // Funcion encargada de devolver una carta al final de la baraja
    public void returnCard(Card card) {
        cards.add(card);
    }
}
 