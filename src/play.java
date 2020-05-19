import java.util.*;

public class play {

	public static char player1 = 'X';
	public static char player2 = 'O';

	public static boolean areMovesLeft(char state[][]) {

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				if (state[i][j] == '_')
					return true;
			}
		return false;
	}

	public static int reward(char state[][]) {
		// Checks for X's or O's in a row.
		for (int row = 0; row < 3; row++) {
			if (state[row][0] == state[row][1] && state[row][1] == state[row][2]) {
				if (state[row][0] == player1)
					return +10;
				else if (state[row][0] == player2)
					return -10;
			}
		}

		// Checks for X's or O's in a column.
		for (int col = 0; col < 3; col++) {
			if (state[0][col] == state[1][col] && state[1][col] == state[2][col]) {
				if (state[0][col] == player1)
					return +10;

				else if (state[0][col] == player2)
					return -10;
			}
		}

		// Checks for X's or O's diagonally.
		if (state[0][0] == state[1][1] && state[1][1] == state[2][2]) {
			if (state[0][0] == player1)
				return +10;
			else if (state[0][0] == player2)
				return -10;
		}

		if (state[0][2] == state[1][1] && state[1][1] == state[2][0]) {
			if (state[0][2] == player1)
				return +10;
			else if (state[0][2] == player2)
				return -10;
		}

		// Else if none of them have won then return 0
		return 0;
	}

	public static int minimax(char state[][], int depth, boolean isMax) {
		int score = reward(state);
		// System.out.println(score);
		// If Maximizer has won the game return his/her
		// evaluated score
		if (score == 10)
			return score;

		// If Minimizer has won the game return his/her
		// evaluated score
		if (score == -10)
			return score;

		// If there are no more moves and no winner then
		// it is a tie
		if (areMovesLeft(state) == false)
			return 0;

		// If this maximizer's move
		if (isMax) {
			int best = -1000;

			// Traverse all cells
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					// Check if cell is empty
					if (state[i][j] == '_') {
						// Make the move
						state[i][j] = player1;

						// Call minimax recursively and choose
						// the maximum value
						best = Math.max(best, minimax(state, depth + 1, !isMax));
						best = best - depth;
						// Undo the move
						state[i][j] = '_';
					}
				}
			}
			return best;
		}

		// If this minimizer's move
		else {
			int best = 1000;

			// Traverse all cells
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					// Check if cell is empty
					if (state[i][j] == '_') {
						// Make the move
						state[i][j] = player2;

						// Call minimax recursively and choose
						// the minimum value
						best = Math.min(best, minimax(state, depth + 1, !isMax));
						best = best + depth;
						// Undo the move
						state[i][j] = '_';
					}
				}
			}
			return best;
		}
	}

	// This will return the best possible move for the player
	public static Move findBestMove(char state[][]) {
//		int bestVal = -1000;
		Move bestMove = new Move();
		bestMove.row = -1;
		bestMove.column = -1;
		bestMove.value = -1000;

		// Traverse all cells, evalutae minimax function for
		// all empty cells. And return the cell with optimal
		// value.
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// Check if celll is empty
				if (state[i][j] == '_') {
					// Make the move
					state[i][j] = player1;

					// compute evaluation function for this
					// move.
					int moveVal = minimax(state, 0, false);

					// Undo the move
					state[i][j] = '_';

					// If the value of the current move is
					// more than the best value, then update
					// best/
					if (moveVal > bestMove.value) {
						bestMove.row = i;
						bestMove.column = j;
						bestMove.value = moveVal;
						// bestVal = moveVal;
					}
				}
			}
		}

		System.out.println("The value of the best Move is : " + bestMove.value);

		return bestMove;
	}

	public static void startGame(char state[][]) {
		Scanner s = new Scanner(System.in);
		while (areMovesLeft(state)) {

			System.out.println("Enter row and column to place 'O'");
			int row = s.nextInt();
			int column = s.nextInt();

			if (row < 0 || column < 0 || row > 2 || column > 2 || state[row][column] != '_') {
				System.out.println("Enter appropriate values!!");
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						System.out.print("|" + state[i][j] + "| ");
					}
					System.out.println();
				}
				System.out.println();
				continue;
			}

			state[row][column] = 'O';
			if (reward(state) == -10) {
				System.out.println("Player 2 wins!");
				break;
			} else if (!areMovesLeft(state)) {
				System.out.println("Its a tie !!");
				break;
			}

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.print("|" + state[i][j] + "| ");
				}
				System.out.println();
			}
			System.out.println();

			Move bestMove = findBestMove(state);
			state[bestMove.row][bestMove.column] = 'X';
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.print("|" + state[i][j] + "| ");
				}
				System.out.println();
			}
			System.out.println();

			if (reward(state) == 10) {
				System.out.println("Player 2 wins!");
				break;
			} else if (!areMovesLeft(state)) {
				System.out.println("Its a tie !!");
				break;
			}
		}

	}

	public static void main(String[] args) {

		// char state[][] =
		// {
		// { '_', '_', 'X' },
		// { 'O', 'X', 'O' },
		// { '_', '_', 'O' }
		// };

		Scanner s = new Scanner(System.in);
		
		while (true) {
		
			char state[][] = { { '_', '_', '_' }, { '_', '_', '_' }, { '_', '_', '_' } };
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.print("|" + state[i][j] + "| ");
				}
				System.out.println();
			}

		
			startGame(state);
			System.out.println("Want to play again? ('Y'/'N'): ");
//			s.nextLine();
			char c = s.nextLine().charAt(0);
			if (c == 'N' || c == 'n')
				break;
		}
		System.out.println("<< Game Complete >>");
		System.out.println("Great Game!!!");
		
	}

}

class Move {
	int row, column, value;
}
