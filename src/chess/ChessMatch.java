package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;

    public ChessMatch(){
        board=new Board(8,8);
        turn=1;
        currentPlayer=Color.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    private void setTurn(int turn) {
        this.turn = turn;
    }

    private void setCurrentPlayer(Color currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ChessPiece[][] getPiece(){
        ChessPiece[][] mat=new ChessPiece[board.getRows()][board.getColumns()];
        for(int i = 0; i< board.getRows(); i++){
            for(int j = 0; j< board.getColumns(); j++){
                mat[i][j]=(ChessPiece) board.piece(i,j);
            }
        }
        return mat;
    }
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source=sourcePosition.toPosition();
        Position target=targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source,target);
        Piece capturedPiece= makeMove(source,target);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private void validateTargetPosition(Position source, Position target) {
        if(!board.piece(source).possibleMove(target))
            throw new ChessException("A peça escolhida não pode ser movida para o destino");
    }
    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position=sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    private Piece makeMove(Position source, Position target) {
        Piece p=board.removePiece(source);
        Piece capturedPiece=board.removePiece(target);
        board.placePiece(p,target);
        return capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if(!board.thereIsAPiece(position)){
            throw new ChessException("Não existe peça na posição de origem");
        }
        if(currentPlayer!=((ChessPiece)board.piece(position)).getColor()){
            throw new ChessException("Não pode mover peça do adversario.");
        }
        if(!board.piece(position).isThereAnyPossibleMove()){
            throw new ChessException("Não existe movimento possivel para esta peça.");
        }
    }

    private void nextTurn(){
        turn++;
        currentPlayer=currentPlayer==Color.WHITE?Color.BLACK:Color.WHITE;
    }

    private void placeNewPiece(char column,int row, ChessPiece chessPiece){
        board.placePiece(chessPiece,new ChessPosition(column,row).toPosition());
    }
    private void initialSetup(){
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}
