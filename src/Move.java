
public class Move {
    private Integer score;
    private Integer index;

    Move() {

    }

    Move(int score) {
        this.score = score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Move{" +
                "score=" + score +
                ", index=" + index +
                '}';
    }
}