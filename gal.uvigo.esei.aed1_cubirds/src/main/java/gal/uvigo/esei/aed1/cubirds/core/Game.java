package gal.uvigo.esei.aed1.cubirds.core;

import gal.uvigo.esei.aed1.cubirds.iu.IU;

public class Game {

    private DeckOfCards deck;
    private Player[] players;
    private Table table;
    private IU iu;

    public Game(IU iu) {
        this.iu = iu;
    }

    /**
     * Metodo principal para jugar
     */
    public void play() {
        createPlayers();
        setupDeckAndTable();
        dealCards();
        fillTable();
        showResults();
        playRounds();
        playTurn();
        playCrds();
    }

    // Pregunta el numero de jugadores y los crea
    private void createPlayers() {
        int numJugadores = iu.askNumberOfPlayers();
        players = new Player[numJugadores];
        for (int i = 0; i < numJugadores; i++) {
            String nombre = iu.askPlayerName(i + 1);
            players[i] = new Player(nombre);
        }
    }

    // Crea y mezcla la baraja y crea la mesa
    private void setupDeckAndTable() {
        deck = new DeckOfCards();
        deck.shuffle();
        table = new Table();
    }

    // Reparte 8 cartas a cada jugador, ya se insertan ordenadas por especie
    private void dealCards() {
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < 8; j++) {
                players[i].addCard(deck.dealCard());
            }
        }
    }

    // Coloca 3 cartas en cada fila de la mesa controlando especies repetidas
    private void fillTable() {
        for (int i = 0; i < 4; i++) {
            int cartasColocadas = 0;
            while (cartasColocadas < 3) {
                Card card = deck.dealCard();
                if (table.canPlace(i, card)) {
                    table.placeCard(i, card);
                    cartasColocadas++;
                } else {
                    deck.returnCard(card);
                }
            }
        }
    }

    // Muestra la mesa y las manos de cada jugador
    private void showResults() {
        iu.displayMessage(table.showTable());
        for (int i = 0; i < players.length; i++) {
            iu.displayMessage(players[i].showHand());
        }
    }
}
private void playRounds() {
    boolean fin = false;

    while (!fin) {
        for (Player player : players) {

            playTurn(player);

            if (player.getHandSize() == 0) {
                iu.displayMessage("¡" + player.getName() + " se ha quedado sin cartas!");
                fin = true;
                break;
            }
        }
    }
}
private void playTurn(Player player) {

    iu.displayMessage("Turno de " + player.getName());
    iu.displayMessage(player.showHand());

    TypeBird tipo = iu.askTypeBird();     // tendrás que implementarlo en IU
    int fila = iu.askRow();               // 0–3
    boolean izquierda = iu.askSide();     // true = izq

    playCards(player, tipo, fila, izquierda);

    iu.displayMessage("Mano actualizada:");
    iu.displayMessage(player.showHand());
}

private void playCards(Player player, TypeBird tipo, int fila, boolean izquierda) {

    // Obtener cartas de ese tipo
    Card[] cartas = player.getCardsByType(tipo);

    // Quitarlas de la mano
    player.removeCards(cartas);

    // Colocarlas en la mesa
    if (izquierda) {
        table.placeCardsLeft(fila, cartas);
    } else {
        table.placeCardsRight(fila, cartas);
    }

    // Resolver rodeo
    resolveEncircle(player, fila, tipo);
}
