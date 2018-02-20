/**
 * Our implementation for Pair data structures.
 * L and R can be any type that we wish (using generics in creating time).
 */

public class Pair<L,R> {

    private L left;
    private R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() { return left; }
    public R getRight() { return right; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairy = (Pair) o;
        return this.left.equals(pairy.getLeft()) &&
                this.right.equals(pairy.getRight());
    }

    public void set(L newX, R newY){
        this.left = newX;
        this.right = newY;
    }

    public void printPair() {
        System.out.println("<" + this.left + "," + this.right + ">");
    }

}