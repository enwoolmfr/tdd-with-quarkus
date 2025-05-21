## Workshop TDD avec Quarkus : Implémentation du Tic Tac Toe (Morpion)

*Durée estimée : 1 heure*

### Objectif

Apprendre et pratiquer le Test Driven Development (TDD) en implémentant la logique du jeu de Tic Tac Toe (Morpion) au sein d'un projet Quarkus.

### Pré-requis

*   JDK 11 ou plus installé.
*   Maven 3.8.2 ou plus installé.
*   Un IDE (IntelliJ IDEA, VS Code avec l'extension Quarkus, Eclipse).
*   Accès à Internet pour la création du projet Quarkus.

### Qu'est-ce que le TDD ?

Le TDD est une approche de développement logiciel qui suit un cycle simple et répétitif :

1.  **RED :** Écrire un test qui échoue pour une petite fonctionnalité que vous souhaitez ajouter.
2.  **GREEN :** Écrire le *minimum* de code nécessaire pour que ce test passe.
3.  **REFACTOR :** Améliorer le code (structure, lisibilité) sans changer son comportement.
4.  Répéter le cycle.

Le TDD permet d'obtenir un code plus robuste, mieux conçu et avec une meilleure couverture de tests.

### Le Projet : Tic Tac Toe Logic

Nous allons implémenter la logique centrale du jeu :
*   Représenter le plateau de jeu 3x3.
*   Permettre aux joueurs (X et O) de placer leurs marques.
*   Vérifier les conditions de victoire (lignes, colonnes, diagonales).
*   Vérifier le cas d'un match nul.
*   Gérer les coups invalides (case déjà prise, hors plateau).

Nous ne ferons *pas* d'interface utilisateur (console ou web) dans ce workshop pour nous concentrer sur la logique et le TDD.

### Étape 1 : Création du projet Quarkus

Ouvrez votre terminal et créez un nouveau projet Quarkus. Vous pouvez utiliser la commande `quarkus create` ou l'interface web code.quarkus.io.

Nous aurons besoin d'au moins la dépendance `resteasy-reactive` (souvent incluse par défaut) et `junit5` pour les tests.

```bash
# Option 1: Via Quarkus CLI
# Assurez-vous que la CLI Quarkus est installée
# quarkus extension list | grep rest -- si besoin
quarkus create app org.acme:tictactoe-tdd --no-examples --extension=resteasy-reactive --extension=junit5

# Option 2: Via Maven
mvn io.quarkus.platform:quarkus-maven-plugin:3.8.3:create \
    -DprojectGroupId=org.acme \
    -DprojectArtifactId=tictactoe-tdd \
    -Dextensions="resteasy-reactive,junit5" \
    -DnoCode

cd tictactoe-tdd
```

Importez le projet dans votre IDE.

### Étape 2 : Démarrer avec le TDD - Le premier test (RED)

Dans le TDD, on commence par le test. Notre premier test sera très simple : "Quand un joueur place sa marque (X) sur une case vide, cette case devrait contenir 'X'".

Créez une classe de test `TicTacToeTest` dans `src/test/java/org/acme`.

Voici le contenu initial de `TicTacToeTest.java` :

```java
package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest // Indique à JUnit 5 que c'est un test Quarkus
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
```

Maintenant, essayez d'exécuter ce test (via votre IDE ou la commande `mvn test` / `./mvnw test`).
Il devrait échouer en indiquant qu'il ne trouve pas la classe `TicTacToe` ou la méthode `play`. C'est notre état **RED**.

### Étape 3 : Écrire le minimum de code pour passer le test (GREEN)

Créez la classe `TicTacToe` dans `src/main/java/org/acme`. Pour l'instant, elle a juste besoin d'un plateau et d'une méthode `play` très basique pour passer le test précédent.

Voici le contenu initial de `TicTacToe.java` :

```java
package org.acme;

public class TicTacToe {

    private char[][] board; // Représente le plateau de jeu 3x3

    // Constructeur pour initialiser le plateau
    public TicTacToe() {
        board = new char[3][3];
        initializeBoard();
    }

    // Méthode privée pour mettre le plateau à vide (par exemple avec des espaces)
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' '; // ' ' représente une case vide
            }
        }
    }

    // Méthode pour placer une marque sur le plateau
    // Pour le moment, faisons-la passer le premier test: place X à (0,0)
    public void play(int row, int col) {
        // Validation basique pour commencer (nous ajouterons d'autres tests/validations plus tard)
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw new IllegalArgumentException("Position hors limites");
        }
        // Dans ce premier test, on sait que c'est X et que c'est vide.
        board[row][col] = 'X';
        // TODO: Ajouter la gestion des joueurs (X et O) et des cases occupées
    }

    // Méthode pour obtenir le plateau (utile pour les tests)
    public char[][] getBoard() {
        return board;
    }

    // TODO: Ajouter les méthodes checkWin(), checkDraw(), getCurrentPlayer(), etc.
}
```

Maintenant, exécutez à nouveau les tests. Le test `shouldPlaceXAt0_0_WhenBoardIsEmpty` devrait maintenant passer. Nous sommes en état **GREEN**.

### Étape 4 : Refactor (Optionnel mais recommandé)

Pour l'instant, notre méthode `play` est très rudimentaire. Elle ne gère que le placement de 'X' et n'alterne pas les joueurs. C'est le moment de penser à la suite.
*   Nous avons besoin d'une variable pour suivre le joueur actuel ('X' ou 'O').
*   La méthode `play` devrait utiliser cette variable et la mettre à jour.
*   Nous aurons besoin d'un test pour vérifier que le joueur change après un coup valide.

Pour l'objectif du workshop de 1h, nous allons passer directement à l'ajout de nouveaux tests (étape 5), intégrant le refactoring au fur et à mesure.

### Étape 5 : Continuer le cycle TDD avec de nouveaux tests

Suivons le cycle Red -> Green -> Refactor pour ajouter d'autres fonctionnalités.

**Fonctionnalité : Alternance des joueurs**

*   **RED:** Écrire un test `shouldSwitchPlayerAfterValidMove()`. Il devrait vérifier qu'après que X joue, le joueur suivant est O.

    ```java
    // Dans TicTacToeTest.java
    @Test
    void shouldSwitchPlayerAfterValidMove() {
        // Given: Un nouveau jeu de Tic Tac Toe
        TicTacToe game = new TicTacToe();

        // When: Le premier joueur (X) joue à (0,0)
        game.play(0, 0);

        // Then: Le joueur actuel devrait être O
        assertEquals('O', game.getCurrentPlayer(), "Le joueur actuel devrait être O après le coup de X");
    }
    ```
    Ce test échoue car `getCurrentPlayer` et la gestion du joueur n'existent pas.

*   **GREEN:** Modifier `TicTacToe.java` pour ajouter la gestion du joueur actuel et la méthode `getCurrentPlayer`.

    ```java
    // Dans TicTacToe.java, ajouter un champ:
    private char currentPlayer;

    // Modifier le constructeur:
    public TicTacToe() {
        board = new char[3][3];
        initializeBoard();
        currentPlayer = 'X'; // X commence
    }

    // Ajouter la méthode pour obtenir le joueur actuel:
    public char getCurrentPlayer() {
        return currentPlayer;
    }

    // Modifier la méthode play pour utiliser et changer le joueur:
    public void play(int row, int col) {
         // (Gardez la validation de limites pour l'instant)
         if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw new IllegalArgumentException("Position hors limites");
        }
        // TODO: Gérer case occupée

        board[row][col] = currentPlayer; // Utiliser le joueur actuel
        // Changer de joueur après un coup valide
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        // TODO: Vérifier victoire ou nul
    }
    ```
    Exécutez les tests. `shouldSwitchPlayerAfterValidMove` devrait passer. Le premier test `shouldPlaceXAt0_0_WhenBoardIsEmpty` pourrait échouer maintenant si vous n'aviez pas initialisé le plateau à vide, mais avec `initializeBoard`, il devrait toujours passer.

*   **REFACTOR:** La logique est simple, pas de refactor majeur ici pour l'instant.

**Fonctionnalité : Gérer les coups sur une case occupée**

*   **RED:** Écrire un test `shouldThrowException_WhenPlayingOnOccupiedCell()`.

    ```java
    // Dans TicTacToeTest.java
    @Test
    void shouldThrowException_WhenPlayingOnOccupiedCell() {
        // Given: Un jeu où la case (0,0) est déjà occupée par X
        TicTacToe game = new TicTacToe();
        game.play(0, 0); // X joue en (0,0)

        // When/Then: Essayer de jouer sur la même case devrait lever une exception
        assertThrows(IllegalArgumentException.class, () -> {
            game.play(0, 0); // O essaie de jouer en (0,0)
        }, "Devrait lancer une exception si la case est déjà occupée");
    }
    ```
    Ce test échoue car `play` ne vérifie pas si la case est vide.

*   **GREEN:** Modifier `TicTacToe.java` pour vérifier si la case est vide dans la méthode `play`.

    ```java
    // Dans TicTacToe.java, modifier la méthode play:
    public void play(int row, int col) {
        // Validation de limites
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw new IllegalArgumentException("Position hors limites");
        }

        // *** Nouvelle validation: case occupée ***
        if (board[row][col] != ' ') {
            throw new IllegalArgumentException("Case déjà occupée");
        }
        // ******************************************

        board[row][col] = currentPlayer;
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        // TODO: Vérifier victoire ou nul
    }
    ```
    Exécutez les tests. Tous les tests devraient passer.

*   **REFACTOR:** Pas de refactor majeur.

**Fonctionnalité : Vérifier la victoire (Exemple: ligne)**

*   **RED:** Écrire un test `shouldReturnX_WhenXWinsHorizontallyOnTopRow()`.

    ```java
    // Dans TicTacToeTest.java
    @Test
    void shouldReturnX_WhenXWinsHorizontallyOnTopRow() {
        // Given: Un jeu où X a rempli la première ligne
        TicTacToe game = new TicTacToe();
        game.play(0, 0); // X
        game.play(1, 0); // O (hors de la ligne pour X)
        game.play(0, 1); // X
        game.play(1, 1); // O
        game.play(0, 2); // X -- Ceci complète la ligne pour X

        // When: On vérifie s'il y a un gagnant
        char winner = game.checkWin(); // <--- Cette méthode n'existe pas encore

        // Then: Le gagnant devrait être X
        assertEquals('X', winner, "X devrait gagner horizontalement sur la première ligne");
    }

    // On aurait aussi besoin de tests pour:
    // - shouldReturnX_WhenXWinsHorizontallyOnMiddleRow()
    // - shouldReturnX_WhenXWinsHorizontallyOnBottomRow()
    // - shouldReturnO_WhenOWinsHorizontallyOnTopRow()
    // ... et pour les victoires verticales et diagonales.
    ```
    Ce test échoue car `checkWin` n'existe pas et la logique de vérification n'est pas implémentée.

*   **GREEN:** Modifier `TicTacToe.java` pour ajouter une méthode `checkWin` qui *au minimum* vérifie la première ligne horizontale pour 'X'.

    ```java
    // Dans TicTacToe.java, ajouter la méthode:
    public char checkWin() {
        // *** Nouvelle logique: Vérifier la première ligne horizontale ***
        if (board[0][0] != ' ' && board[0][0] == board[0][1] && board[0][1] == board[0][2]) {
            return board[0][0]; // Retourne 'X' ou 'O'
        }
        // **************************************************************

        // TODO: Vérifier les autres lignes, colonnes, diagonales
        return ' '; // Retourne ' ' s'il n'y a pas de gagnant
    }

    // Modifier play pour vérifier le gagnant après chaque coup (si besoin, pas strictement nécessaire pour ce test)
    // Pour l'instant, laissons play simple et testons checkWin directement.
    ```
    Exécutez les tests. `shouldReturnX_WhenXWinsHorizontallyOnTopRow` devrait maintenant passer. Les tests précédents devraient toujours passer.

*   **REFACTOR:** C'est le bon moment pour refactorer `checkWin`. La logique pour vérifier les autres lignes/colonnes/diagonales est similaire. On peut extraire des méthodes privées pour vérifier les lignes, les colonnes, les diagonales et les appeler depuis `checkWin`.

    ```java
    // Dans TicTacToe.java
    public char checkWin() {
        char winner = ' ';

        // Vérifier les lignes
        winner = checkRows();
        if (winner != ' ') return winner;

        // Vérifier les colonnes
        winner = checkColumns();
        if (winner != ' ') return winner;

        // Vérifier les diagonales
        winner = checkDiagonals();
        if (winner != ' ') return winner;

        return ' '; // Pas de gagnant
    }

    private char checkRows() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
        }
        return ' ';
    }

    private char checkColumns() {
         for (int j = 0; j < 3; j++) {
            if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return board[0][j];
            }
        }
        return ' ';
    }

    private char checkDiagonals() {
        // Diagonale principale (haut-gauche à bas-droite)
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        // Diagonale secondaire (haut-droite à bas-gauche)
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }
        return ' ';
    }
    ```
    Ajoutez les tests pour les autres cas de victoire (colonnes, diagonales, victoire de O). Exécutez les tests après chaque ajout pour confirmer qu'ils échouent (RED), puis modifiez le code pour les faire passer (GREEN), puis refactorez si possible.

**Fonctionnalité : Gérer le match nul**

*   **RED:** Écrire un test `shouldReturnDraw_WhenBoardIsFullAndNoWinner()`.

    ```java
    // Dans TicTacToeTest.java
    @Test
    void shouldReturnDraw_WhenBoardIsFullAndNoWinner() {
        // Given: Un jeu où le plateau est rempli sans gagnant (un scénario typique de nul)
        TicTacToe game = new TicTacToe();
        // Simuler une partie nulle
        game.play(0, 0); // X
        game.play(0, 1); // O
        game.play(0, 2); // X
        game.play(1, 2); // O
        game.play(1, 1); // X
        game.play(2, 1); // O
        game.play(1, 0); // X
        game.play(2, 0); // O
        game.play(2, 2); // X -- Plateau plein

        // When: On vérifie s'il y a match nul
        boolean isDraw = game.checkDraw(); // <--- Cette méthode n'existe pas encore

        // Then: Le jeu devrait être un match nul
        assertTrue(isDraw, "Le jeu devrait être un match nul");
        assertEquals(' ', game.checkWin(), "Il ne devrait pas y avoir de gagnant dans un match nul"); // Optionnel mais bonne vérif
    }
    ```
    Ce test échoue car `checkDraw` n'existe pas.

*   **GREEN:** Ajouter la méthode `checkDraw`. Elle doit vérifier si le plateau est plein (aucune case vide) ET qu'il n'y a pas de gagnant.

    ```java
    // Dans TicTacToe.java
    public boolean checkDraw() {
        // S'il y a déjà un gagnant, ce n'est pas un match nul
        if (checkWin() != ' ') {
            return false;
        }

        // Vérifier si le plateau est plein
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false; // Trouvé une case vide, pas un match nul
                }
            }
        }

        // Plateau plein et pas de gagnant = match nul
        return true;
    }
    ```
    Exécutez les tests. Tous devraient passer.

*   **REFACTOR:** Pas de refactor majeur ici.

**Fonctionnalité : Interdire de jouer après la fin du jeu (victoire ou nul)**

*   **RED:** Écrire des tests `shouldThrowException_WhenPlayingAfterWin()` et `shouldThrowException_WhenPlayingAfterDraw()`.

    ```java
    // Dans TicTacToeTest.java
    @Test
    void shouldThrowException_WhenPlayingAfterWin() {
        // Given: Un jeu qui a déjà un gagnant (par exemple, X gagne sur la première ligne)
        TicTacToe game = new TicTacToe();
        game.play(0, 0); // X
        game.play(1, 0); // O
        game.play(0, 1); // X
        game.play(1, 1); // O
        game.play(0, 2); // X -- X gagne

        // When/Then: Essayer de jouer un coup supplémentaire devrait lancer une exception
         assertThrows(IllegalStateException.class, () -> { // Peut-être une autre exception, à définir
            game.play(2, 2); // O essaie de jouer après la victoire de X
        }, "Devrait lancer une exception si on joue après une victoire");
    }

    @Test
    void shouldThrowException_WhenPlayingAfterDraw() {
         // Given: Un jeu qui s'est terminé par un match nul
        TicTacToe game = new TicTacToe();
        // Simuler une partie nulle (réutilisez la logique du test de match nul)
        game.play(0, 0); game.play(0, 1); game.play(0, 2);
        game.play(1, 2); game.play(1, 1); game.play(2, 1);
        game.play(1, 0); game.play(2, 0); game.play(2, 2); // Match nul

        // When/Then: Essayer de jouer un coup supplémentaire devrait lancer une exception
         assertThrows(IllegalStateException.class, () -> { // Peut-être une autre exception
            game.play(0, 0); // Essayer de rejouer n'importe où
        }, "Devrait lancer une exception si on joue après un match nul");
    }
    ```
    Ces tests échouent car `play` ne vérifie pas si le jeu est terminé. Notez l'utilisation d'une `IllegalStateException`, qui est plus appropriée pour un état invalide du jeu que `IllegalArgumentException` qui est pour des arguments invalides.

*   **GREEN:** Modifier `TicTacToe.java` pour ajouter une méthode `isGameOver` et l'appeler au début de `play`.

    ```java
    // Dans TicTacToe.java
    // Ajouter la méthode pour vérifier si le jeu est terminé
    public boolean isGameOver() {
        return checkWin() != ' ' || checkDraw();
    }

    // Modifier la méthode play:
    public void play(int row, int col) {
        // *** Nouvelle vérification: jeu terminé? ***
        if (isGameOver()) {
            throw new IllegalStateException("Le jeu est terminé");
        }
        // *****************************************

        // Validation de limites
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw new IllegalArgumentException("Position hors limites");
        }

        // Validation: case occupée
        if (board[row][col] != ' ') {
            throw new IllegalArgumentException("Case déjà occupée");
        }

        board[row][col] = currentPlayer;
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        // Note: checkWin et checkDraw sont maintenant appelés par isGameOver(),
        // donc play n'a pas besoin de les appeler directement pour la logique de fin.
        // Mais on pourrait vouloir les appeler pour stocker le résultat final dans des champs si besoin.
    }
    ```
    Exécutez les tests. Tous devraient passer.

*   **REFACTOR:** Les méthodes `checkWin`, `checkDraw`, `isGameOver` sont bien séparées. `play` gère maintenant toutes les validations nécessaires. Le code semble raisonnablement propre pour l'objectif.

### Conclusion (Dans le Workshop)

Félicitations ! Vous avez utilisé le TDD pour implémenter la logique essentielle d'un jeu de Tic Tac Toe.

*   Vous avez commencé par écrire des tests qui échouaient.
*   Vous avez écrit juste assez de code pour les faire passer.
*   Vous avez (peut-être) refactoré pour améliorer la structure.
*   Vous avez répété ce cycle pour ajouter de nouvelles fonctionnalités.

Même en 1 heure, le TDD vous a guidé dans l'implémentation étape par étape et vous a donné confiance que votre logique de jeu fonctionne correctement grâce à la suite de tests grandissante.

Le projet peut être étendu pour ajouter une interface, stocker l'historique des coups, etc., en continuant avec le même cycle TDD.

---

### Code Final (Pour référence rapide après le workshop)

**`src/main/java/org/acme/TicTacToe.java`**

```java
package org.acme;

// Utile pour les exceptions
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;

public class TicTacToe {

    private char[][] board; // Représente le plateau de jeu 3x3
    private char currentPlayer; // 'X' ou 'O'

    // Constructeur pour initialiser le plateau et le joueur
    public TicTacToe() {
        board = new char[3][3];
        initializeBoard();
        currentPlayer = 'X'; // X commence
    }

    // Méthode privée pour mettre le plateau à vide (par exemple avec des espaces)
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' '; // ' ' représente une case vide
            }
        }
    }

    // Méthode pour placer une marque sur le plateau
    public void play(int row, int col) {
        // 1. Vérifier si le jeu est terminé
        if (isGameOver()) {
             throw new IllegalStateException("Le jeu est terminé");
        }

        // 2. Validation des limites
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw new IllegalArgumentException("Position hors limites");
        }

        // 3. Validation de la case occupée
        if (board[row][col] != ' ') {
            throw new IllegalArgumentException("Case déjà occupée");
        }

        // 4. Placer la marque et changer de joueur
        board[row][col] = currentPlayer;
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        // Note: checkWin et checkDraw sont appelés par isGameOver(),
        // la logique de fin est gérée par la première vérification.
    }

    // Méthode pour obtenir le plateau (utile pour les tests ou l'affichage)
    public char[][] getBoard() {
        return board;
    }

    // Méthode pour obtenir le joueur actuel
    public char getCurrentPlayer() {
        return currentPlayer;
    }

    // Vérifie s'il y a un gagnant
    public char checkWin() {
        char winner = ' ';

        // Vérifier les lignes
        winner = checkRows();
        if (winner != ' ') return winner;

        // Vérifier les colonnes
        winner = checkColumns();
        if (winner != ' ') return winner;

        // Vérifier les diagonales
        winner = checkDiagonals();
        if (winner != ' ') return winner;

        return ' '; // Pas de gagnant
    }

    private char checkRows() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
        }
        return ' ';
    }

    private char checkColumns() {
         for (int j = 0; j < 3; j++) {
            if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return board[0][j];
            }
        }
        return ' ';
    }

    private char checkDiagonals() {
        // Diagonale principale (haut-gauche à bas-droite)
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        // Diagonale secondaire (haut-droite à bas-gauche)
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }
        return ' ';
    }


    // Vérifie s'il y a match nul
    public boolean checkDraw() {
        // S'il y a déjà un gagnant, ce n'est pas un match nul
        if (checkWin() != ' ') {
            return false;
        }

        // Vérifier si le plateau est plein
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false; // Trouvé une case vide, pas un match nul
                }
            }
        }

        // Plateau plein et pas de gagnant = match nul
        return true;
    }

    // Vérifie si le jeu est terminé (victoire ou nul)
    public boolean isGameOver() {
        return checkWin() != ' ' || checkDraw();
    }

    // Méthode d'affichage simple pour le debug (pas essentielle pour la logique TDD)
    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + (j < 2 ? "|" : ""));
            }
            System.out.println();
            if (i < 2) {
                System.out.println("-----");
            }
        }
        System.out.println();
    }
}
```

**`src/test/java/org/acme/TicTacToeTest.java`**

```java
package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TicTacToeTest {

    // --- Tests d'initialisation et de coups basiques ---

    @Test
    void shouldInitializeBoardWithEmptyCells() {
        // Given: Un nouveau jeu de Tic Tac Toe
        TicTacToe game = new TicTacToe();

        // When: On récupère le plateau
        char[][] board = game.getBoard();

        // Then: Toutes les cellules devraient être vides (' ')
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(' ', board[i][j], "La case (" + i + "," + j + ") devrait être vide");
            }
        }
    }

    @Test
    void shouldStartWithPlayerX() {
        // Given: Un nouveau jeu de Tic Tac Toe
        TicTacToe game = new TicTacToe();

        // When: On récupère le joueur actuel
        char currentPlayer = game.getCurrentPlayer();

        // Then: Le joueur actuel devrait être 'X'
        assertEquals('X', currentPlayer, "Le jeu devrait commencer avec le joueur 'X'");
    }

    @Test
    void shouldPlaceXAt0_0_WhenBoardIsEmpty() {
        // Given: Un nouveau jeu de Tic Tac Toe
        TicTacToe game = new TicTacToe();

        // When: Le premier joueur (X) place sa marque à (0,0)
        game.play(0, 0);

        // Then: La case (0,0) devrait contenir 'X'
        assertEquals('X', game.getBoard()[0][0], "La case (0,0) devrait contenir 'X'");
    }

    @Test
    void shouldSwitchPlayerAfterValidMove() {
        // Given: Un nouveau jeu de Tic Tac Toe
        TicTacToe game = new TicTacToe();

        // When: Le premier joueur (X) joue à (0,0)
        game.play(0, 0);

        // Then: Le joueur actuel devrait être O
        assertEquals('O', game.getCurrentPlayer(), "Le joueur actuel devrait être O après le coup de X");

        // When: Le joueur O joue à (1,1)
        game.play(1, 1);

        // Then: Le joueur actuel devrait être X à nouveau
        assertEquals('X', game.getCurrentPlayer(), "Le joueur actuel devrait être X après le coup de O");
    }

    // --- Tests des coups invalides ---

    @Test
    void shouldThrowException_WhenPlayingOutOfBounds() {
        // Given: Un nouveau jeu
        TicTacToe game = new TicTacToe();

        // When/Then: Essayer de jouer en dehors des limites (ex: 3,0) devrait lancer une exception
        assertThrows(IllegalArgumentException.class, () -> {
            game.play(3, 0);
        }, "Devrait lancer une exception si la ligne est hors limites");

        assertThrows(IllegalArgumentException.class, () -> {
            game.play(0, 3);
        }, "Devrait lancer une exception si la colonne est hors limites");

         assertThrows(IllegalArgumentException.class, () -> {
            game.play(-1, 0);
        }, "Devrait lancer une exception si la ligne est négative");
    }

    @Test
    void shouldThrowException_WhenPlayingOnOccupiedCell() {
        // Given: Un jeu où la case (0,0) est déjà occupée par X
        TicTacToe game = new TicTacToe();
        game.play(0, 0); // X joue en (0,0)

        // When/Then: Essayer de jouer sur la même case devrait lever une exception
        assertThrows(IllegalArgumentException.class, () -> {
            game.play(0, 0); // O essaie de jouer en (0,0)
        }, "Devrait lancer une exception si la case est déjà occupée");
    }

    // --- Tests des conditions de victoire ---

    @Test
    void shouldReturnX_WhenXWinsHorizontallyOnTopRow() {
        TicTacToe game = new TicTacToe();
        game.play(0, 0); game.play(1, 0); // X, O
        game.play(0, 1); game.play(1, 1); // X, O
        game.play(0, 2); // X gagne
        assertEquals('X', game.checkWin(), "X devrait gagner horizontalement (ligne 0)");
        assertTrue(game.isGameOver(), "Le jeu devrait être terminé");
    }

    @Test
    void shouldReturnO_WhenOWinsHorizontallyOnMiddleRow() {
        TicTacToe game = new TicTacToe();
        game.play(0, 0); game.play(1, 0); // X, O
        game.play(2, 2); game.play(1, 1); // X, O
        game.play(0, 1); game.play(1, 2); // X, O gagne
        assertEquals('O', game.checkWin(), "O devrait gagner horizontalement (ligne 1)");
         assertTrue(game.isGameOver(), "Le jeu devrait être terminé");
    }

     @Test
    void shouldReturnX_WhenXWinsVerticallyOnRightColumn() {
        TicTacToe game = new TicTacToe();
        game.play(0, 2); game.play(0, 0); // X, O
        game.play(1, 2); game.play(0, 1); // X, O
        game.play(2, 2); // X gagne
        assertEquals('X', game.checkWin(), "X devrait gagner verticalement (colonne 2)");
         assertTrue(game.isGameOver(), "Le jeu devrait être terminé");
    }

    @Test
    void shouldReturnO_WhenOWinsDiagonallyFromTopLeft() {
        TicTacToe game = new TicTacToe();
        game.play(0, 1); game.play(0, 0); // X, O
        game.play(0, 2); game.play(1, 1); // X, O
        game.play(1, 0); game.play(2, 2); // X, O gagne
        assertEquals('O', game.checkWin(), "O devrait gagner diagonalement (haut-gauche)");
         assertTrue(game.isGameOver(), "Le jeu devrait être terminé");
    }

     @Test
    void shouldReturnX_WhenXWinsDiagonallyFromTopRight() {
        TicTacToe game = new TicTacToe();
        game.play(0, 2); game.play(0, 0); // X, O
        game.play(1, 1); game.play(0, 1); // X, O
        game.play(2, 0); // X gagne
        assertEquals('X', game.checkWin(), "X devrait gagner diagonalement (haut-droite)");
         assertTrue(game.isGameOver(), "Le jeu devrait être terminé");
    }

    @Test
    void shouldReturnEmptyChar_WhenNoWinnerYet() {
         TicTacToe game = new TicTacToe();
        game.play(0, 0); game.play(1, 0); // X, O
        game.play(0, 1); // X
        assertEquals(' ', game.checkWin(), "Il ne devrait pas y avoir de gagnant après quelques coups");
         assertFalse(game.isGameOver(), "Le jeu ne devrait pas être terminé");
    }


    // --- Tests du match nul ---

    @Test
    void shouldReturnDraw_WhenBoardIsFullAndNoWinner() {
        TicTacToe game = new TicTacToe();
        // Simuler une partie nulle typique
        game.play(0, 0); game.play(0, 1); game.play(0, 2); // X, O, X
        game.play(1, 2); game.play(1, 1); game.play(2, 1); // O, X, O
        game.play(1, 0); game.play(2, 0); game.play(2, 2); // X, O, X (Board plein)

        assertTrue(game.checkDraw(), "Le jeu devrait être un match nul");
        assertTrue(game.isGameOver(), "Le jeu devrait être terminé");
        assertEquals(' ', game.checkWin(), "Il ne devrait pas y avoir de gagnant dans un match nul");
    }

     @Test
    void shouldNotReturnDraw_WhenBoardIsNotFull() {
         TicTacToe game = new TicTacToe();
        game.play(0, 0); // X
        assertFalse(game.checkDraw(), "Le jeu ne devrait pas être un match nul si le plateau n'est pas plein");
    }

    @Test
     void shouldNotReturnDraw_WhenThereIsAWinner() {
         TicTacToe game = new TicTacToe();
        game.play(0, 0); game.play(1, 0); // X, O
        game.play(0, 1); game.play(1, 1); // X, O
        game.play(0, 2); // X gagne

        assertFalse(game.checkDraw(), "Le jeu ne devrait pas être un match nul s'il y a un gagnant");
     }


    // --- Tests de fin de jeu ---

    @Test
    void shouldThrowException_WhenPlayingAfterWin() {
        TicTacToe game = new TicTacToe();
        // Simuler une partie gagnée par X
        game.play(0, 0); game.play(1, 0);
        game.play(0, 1); game.play(1, 1);
        game.play(0, 2); // X gagne

        // When/Then: Essayer de jouer un coup supplémentaire devrait lancer une exception
         assertThrows(IllegalStateException.class, () -> {
            game.play(2, 2); // O essaie de jouer après la victoire de X
        }, "Devrait lancer une exception si on joue après une victoire");
    }

    @Test
    void shouldThrowException_WhenPlayingAfterDraw() {
         TicTacToe game = new TicTacToe();
        // Simuler un match nul
        game.play(0, 0); game.play(0, 1); game.play(0, 2);
        game.play(1, 2); game.play(1, 1); game.play(2, 1);
        game.play(1, 0); game.play(2, 0); game.play(2, 2); // Match nul

        // When/Then: Essayer de jouer un coup supplémentaire devrait lancer une exception
         assertThrows(IllegalStateException.class, () -> {
            game.play(0, 0); // Essayer de rejouer n'importe où
        }, "Devrait lancer une exception si on joue après un match nul");
    }
}
```