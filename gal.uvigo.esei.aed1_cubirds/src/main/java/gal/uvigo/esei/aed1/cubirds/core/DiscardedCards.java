package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;

public class DiscardedCards {

    private List<Card> discarded;

    // Constructor: crea el monton de descartes vacio
    public DiscardedCards() {
        discarded = new LinkedList<>();
    }

    // Añade una carta al monton de descartes
    public void addCard(Card card) {
        discarded.addLast(card);
    }

    // Añade varias cartas al monton de descartes
    public void addCards(List<Card> cards) {
        for (Card card : cards) {
            discarded.addLast(card);
        }
    }

    // Pasa todas las cartas del monton a la baraja y vacia el monton
    public void transferToDeck(DeckOfCards deck) {
        while (!discarded.isEmpty()) {
            deck.returnCard(discarded.removeFirst());
        }
    }

    // Devuelve el numero de cartas en el monton
    public int size() {
        return discarded.size();
    }
}