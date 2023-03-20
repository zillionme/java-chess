package chess.domain.game;

import boardStrategy.EmptyBoardStrategy;
import chess.boardstrategy.InitialBoardStrategy;
import chess.domain.board.Column;
import chess.domain.board.Position;
import chess.domain.board.Rank;
import chess.domain.piece.Color;
import chess.domain.piece.PieceType;
import chess.domain.piece.type.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static chess.domain.piece.PieceType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings({"NonAsciiCharacters","SpellCheckingInspection"})
class ChessBoardTest {

    private static final EmptyBoardStrategy emptyBoardStrategy = new EmptyBoardStrategy();
    private static final InitialBoardStrategy initialBoardStrategy = new InitialBoardStrategy();
    private ChessBoard chessBoard;

    @BeforeEach
    void setup() {
        chessBoard = new ChessBoard();
    }

    @Test
    void 체스보드가_초기화_되었으면_true를_반환한다() {
        chessBoard.initialize(initialBoardStrategy.generate());
        assertThat(chessBoard.isInitialized())
                .isTrue();
    }

    @Test
    void 체스보드가_초기화_되지않았으면_false를_반환한다() {
        assertThat(chessBoard.isInitialized())
                .isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"EIGHT, BLACK", "ONE, WHITE"})
    void 초기화된_체스보드의_Rank8과_Rank1에_예상한_기물들이_초기화되어_있다(Rank rank, Color color) {
        chessBoard.initialize(initialBoardStrategy.generate());
        Map<Position, Piece> initializedChessBoard = chessBoard.getChessBoard();
        List<PieceType> firstRowPieces = List.of(ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK);

        for(int i = 0 ; i < 8 ; i++) {
            Piece piece = initializedChessBoard.get(Position.of(Column.findColumnByIndex(i+1), rank));
            assertThat(piece.getPieceType())
                    .isEqualTo(firstRowPieces.get(i));
            assertThat(piece.getColor())
                    .isEqualTo(color);
        }
    }

    @ParameterizedTest
    @CsvSource(value = {"SEVEN, BLACK", "TWO, WHITE"})
    void 초기화된_체스보드의_Rank7과_Rank2에_예상한_Pawn이_초기화되어_있다(Rank rank, Color color) {
        chessBoard.initialize(initialBoardStrategy.generate());
        Map<Position, Piece> initializedChessBoard = chessBoard.getChessBoard();
        PieceType secondRowPiece = PAWN;

        for(Column column :Column.getOrderedColumns()) {
            Piece piece = initializedChessBoard.get(Position.of(column, rank));
            assertThat(piece.getPieceType())
                    .isEqualTo(secondRowPiece);
            assertThat(piece.getColor())
                    .isEqualTo(color);
        }
    }


    @Test
    void 출발위치와_도착위치가_같으면_예외() {
        chessBoard.initialize(initialBoardStrategy.generate());
        Position startPosition = Position.of(Column.D, Rank.TWO);
        Position endPosition = Position.of(Column.D, Rank.TWO);

        assertThatThrownBy(()->chessBoard.move(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("제자리로는 이동할 수 없습니다");
    }
}