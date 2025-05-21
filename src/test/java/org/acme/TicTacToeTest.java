package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TicTacToeTest {

    // Nommage des tests selon la convention Should_When_Expect
    @Test
    void shouldPlaceXAt0_0_WhenBoardIsEmpty() {
        // Given: Un nouveau jeu de Tic Tac Toe
        TicTacToe game = new TicTacToe(); // <--- Cette classe n'existe pas encore, c'est normal !

        // When: Le premier joueur (X) place sa marque à (0,0)
        game.play(0, 0); // <--- Cette méthode n'existe pas encore

        // Then: La case (0,0) devrait contenir 'X'
        // Nous aurons besoin d'une méthode pour inspecter le plateau
        assertEquals('X', game.getBoard()[0][0], "La case (0,0) devrait contenir 'X'");
    }

    // Nous ajouterons d'autres tests ici...
}
