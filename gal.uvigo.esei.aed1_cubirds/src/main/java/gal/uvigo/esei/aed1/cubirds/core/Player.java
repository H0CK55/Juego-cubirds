package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.LinkedList;
import es.uvigo.esei.aed1.tads.list.List;

public class Player {

    private String name;
    private List<Card> mano;
    private List<TypeBird> collection; // zona de juego

    public Player(String name) {
        this.name = name;
        this.mano = new LinkedList<>();
        this.collection = new LinkedList<>();
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

    // Retira todas las cartas de una especie y las devuelve
    public List<Card> removeSpecies(TypeBird type) {
        List<Card> removed = new LinkedList<>();
        int i = 0;
        while (i < mano.size()) {
            if (mano.get(i).getTypeBird() == type) {
                removed.addLast(mano.remove(i));
            } else {
                i++;
            }
        }
        return removed;
    }

    // Devuelve las especies disponibles en la mano sin repetir
    public List<TypeBird> getSpecies() {
        List<TypeBird> species = new LinkedList<>();
        for (Card card : mano) {
            if (!species.contains(card.getTypeBird())) {
                species.addLast(card.getTypeBird());
            }
        }
        return species;
    }

    // Devuelve cuantas cartas tiene de una especie concreta
    public int countCards(TypeBird type) {
        int count = 0;
        for (Card card : mano) {
            if (card.getTypeBird() == type) {
                count++;
            }
        }
        return count;
    }

    // Añade una especie a la zona de juego
    public void incrementSpecies(TypeBird type) {
        collection.addLast(type);
    }

    // Devuelve cuantas especies tiene en la zona de juego
    public int getCollectionSize() {
        return collection.size();
    }

    // Devuelve true si tiene 7 especies en la zona de juego
    public boolean hasWon() {
        return collection.size() >= 7;
    }

    // Devuelve true si la mano esta vacia
    public boolean hasEmptyHand() {
        return mano.isEmpty();
    }

    // Devuelve la mano completa y la vacia
    public List<Card> discardHand() {
        List<Card> all = new LinkedList<>();
        while (!mano.isEmpty()) {
            all.addLast(mano.removeFirst());
        }
        return all;
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

    // Devuelve la zona de juego como String
    public String showCollection() {
        StringBuilder sb = new StringBuilder();
        sb.append("Zona de juego de ").append(name).append(": ");
        for (TypeBird type : collection) {
            sb.append(type).append(" ");
        }
        return sb.toString();
    }
}