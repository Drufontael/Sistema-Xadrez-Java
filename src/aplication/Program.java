package aplication;

import chess.ChessMatch;
import chess.ChessPosition;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while(true){
            UI.printBoard(chessMatch.getPiece());
            System.out.println();
            System.out.print("Origem: ");
            ChessPosition source=UI.readChessPosition(sc);
            System.out.println();
            System.out.print("Destino: ");
            ChessPosition target=UI.readChessPosition(sc);
            chessMatch.performChessMove(source,target);
        }
    }

}
