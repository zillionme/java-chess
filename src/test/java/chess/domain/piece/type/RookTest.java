package chess.domain.piece.type;

import chess.domain.board.Column;
import chess.domain.board.Position;
import chess.domain.board.Rank;
import chess.domain.piece.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SuppressWarnings({"NonAsciiCharacters","SpellCheckingInspection"})
class RookTest {

    private final Rook rook = new Rook(Color.BLACK);

    @ParameterizedTest
    @CsvSource(value = {"C, FIVE" , "C, THREE", "E, FIVE","E,THREE"})
    void Rook이_갈수없는_방향으로_이동하려고_하면_예외(Column endColumn, Rank endRank) {
        Position start = Position.of(Column.D, Rank.FOUR);
        Position end = Position.of(endColumn, endRank);
        Color colorOfDestination = Color.NONE;

        assertThatThrownBy(() -> rook.isMovable(start, end, colorOfDestination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rook이 이동할 수 있는 방향이 아닙니다");

    }

    @Test
    void 도착점에_기물의_색깔이_아군인_경우_예외() {
        Position start = Position.of(Column.D, Rank.FOUR);
        Position end = Position.of(Column.D, Rank.FIVE);
        Color colorOfDestination = Color.BLACK;

        assertThatThrownBy(() -> rook.isMovable(start, end, colorOfDestination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rook은 도착점에 아군이 있으면 이동할 수 없습니다");

    }

    @Test
    void 모든_이동_조건을_충족하면_true를_반환한다() {
        Position start = Position.of(Column.D, Rank.FOUR);
        Position end = Position.of(Column.D, Rank.FIVE);
        Color colorOfDestination = Color.NONE;

        assertThat(rook.isMovable(start, end, colorOfDestination))
                .isTrue();

    }

}