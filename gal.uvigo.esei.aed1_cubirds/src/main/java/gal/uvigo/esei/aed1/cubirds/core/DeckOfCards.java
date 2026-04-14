package gal.uvigo.esei.aed1.cubirds.core;
 
import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;
 
public class DeckOfCards {
 
    private List<Card> cards;
 
    /**
     * Constructor: crea una baraja de cartas ordenada a partir del enumerado
     */
    public DeckOfCards() {
        cards = new LinkedList<>();
        for (Card card : Card.values()) {
            cards.addLast(card);
        }
    }
 
    // Funcion encargada de mezclar la baraja 
    public void shuffle() {
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = (int)(Math.random() * (i + 1));
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }
 
    // Funcion encargada de repartir una carta
    public Card dealCard() {
        return cards.removeFirst();
    }
 
    // Funcion encargada de devolver una carta al final de la baraja
    public void returnCard(Card card) {
        cards.addLast(card);
    }
}