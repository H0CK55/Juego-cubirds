package gal.uvigo.esei.aed1.cubirds.core;

import gal.uvigo.esei.aed1.cubirds.iu.IU;
import es.uvigo.esei.aed1.tads.list.List;

public class Game {

    private DeckOfCards deck;
    private DiscardedCards discarded;
    private Player[] players;
    private Table table;
    private IU iu;

    public Game(IU iu) {
        this.iu = iu;
    }

    // Metodo principal para jugar
    public void play() {
        createPlayers();
        setupGame();
        dealInitialCards();
        fillTable();
        playGame();
    }

    // Pregunta el numero de jugadores y los crea
    private void createPlayers() {
        int num = iu.askNumberOfPlayers();
        players = new Player[num];
        for (int i = 0; i < num; i++) {
            players[i] = new Player(iu.askPlayerName(i + 1));
        }
    }

    // Crea y mezcla la baraja, el monton de descartes y la mesa
    private void setupGame() {
        deck = new DeckOfCards();
        deck.shuffle();
        discarded = new DiscardedCards();
        table = new Table();
    }

    // Reparte 8 cartas a cada jugador
    private void dealInitialCards() {
        for (Player p : players) {
            for (int i = 0; i < 8; i++) {
                p.addCard(deck.dealCard());
            }
        }
    }

    // Coloca 3 cartas en cada fila de la mesa sin repetir especie
    private void fillTable() {
        for (int i = 0; i < 4; i++) {
            int colocadas = 0;
            while (colocadas < 3) {
                Card c = deck.dealCard();
                if (table.canPlace(i, c)) {
                    table.placeCard(i, c);
                    colocadas++;
                } else {
                    deck.returnCard(c);
                }
            }
        }
    }

    // Controla el flujo del juego hasta que haya un ganador
    private void playGame() {
        while (true) {
            for (Player p : players) {
                playTurn(p);

                // Caso de victoria principal: 7 especies en la zona de juego
                if (p.hasWon()) {
                    iu.displayMessage(
                        p.getName() + " ha ganado al conseguir 7 especies de pájaros"
                    );
                    return;
                }

                // Si el jugador se queda sin cartas
                if (p.hasEmptyHand()) {
                    if (!restartRound()) {
                        // Caso de victoria alternativo: no se puede repartir
                        declareWinnerByCollection();
                        return;
                    }
                }
            }
        }
    }

    // Ejecuta el turno de un jugador
    private void playTurn(Player player) {
        iu.displayMessage(table.showTable());
        iu.displayMessage(player.showHand());

        TypeBird type = iu.askSpecies(player.getSpecies());
        int row = iu.askRow();
        boolean left = iu.askSide();

        List<Card> played = player.removeSpecies(type);

        if (left) {
            table.addLeft(row, played);
        } else {
            table.addRight(row, played);
        }

        List<Card> surrounded = table.collectSurrounded(row, type, left);
        for (Card c : surrounded) {
            player.addCard(c);
        }

        // Comprueba si la fila queda con una sola especie
        refillRowIfSingleSpecies(row);

        iu.displayMessage(player.showHand());

        // Pregunta si desea bajar una especie a la zona de juego
        if (iu.askIfDiscard()) {
            handleDiscardToCollection(player);
        }
    }

    // Rellena la fila si todas las cartas son de la misma especie
    private void refillRowIfSingleSpecies(int row) {
        if (!table.hasOnlyOneSpecies(row)) return;

        TypeBird current = table.getFirstSpecies(row);

        while (true) {
            if (deckIsEmpty()) {
                discarded.transferToDeck(deck);
                deck.shuffle();
            }

            Card c = deck.dealCard();
            table.placeCard(row, c);

            if (c.getTypeBird() != current) {
                break;
            }
        }
    }

    // Gestiona bajar una especie a la zona de juego
    private void handleDiscardToCollection(Player player) {
        TypeBird chosen = iu.askSpeciesForCollection(player.getSpecies());
        int count = player.countCards(chosen);
        int required = getSmallFlock(chosen);

        if (count >= required) {
            List<Card> removed = player.removeSpecies(chosen);
            discarded.addCards(removed);
            player.incrementSpecies(chosen);
        } else {
            iu.displayMessage("No tienes suficientes cartas para bajar esa especie");
        }
    }

    // Devuelve el tamaño de la bandada pequeña de una especie
    private int getSmallFlock(TypeBird type) {
        for (Card c : Card.values()) {
            if (c.getTypeBird() == type) {
                return c.getSmallFlock();
            }
        }
        return Integer.MAX_VALUE;
    }

    // Reinicia la ronda cuando un jugador se queda sin cartas
    private boolean restartRound() {
        for (Player p : players) {
            discarded.addCards(p.discardHand());
        }

        discarded.transferToDeck(deck);
        deck.shuffle();

        for (int i = 0; i < 8; i++) {
            for (Player p : players) {
                if (deckIsEmpty()) {
                    return false;
                }
                p.addCard(deck.dealCard());
            }
        }
        return true;
    }

    // Determina el ganador por mayor numero de especies en la coleccion
    private void declareWinnerByCollection() {
        Player winner = players[0];

        for (Player p : players) {
            if (p.getCollectionSize() > winner.getCollectionSize()) {
                winner = p;
            }
        }

        iu.displayMessage(
            winner.getName() +
            " ha ganado porque no ha sido posible repartir cartas"
        );
    }

    // Comprueba si la baraja esta vacia
    private boolean deckIsEmpty() {
        try {
            deck.dealCard();
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}