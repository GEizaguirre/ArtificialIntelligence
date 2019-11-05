public class Token {
    private short nright, nleft;

    public Token(short nright, short nleft) {
        this.nright = nright;
        this.nleft = nleft;
    }

    public short getNright() {
        return nright;
    }

    public void setNright(short nright) {
        this.nright = nright;
    }

    public short getNleft() {
        return nleft;
    }

    public void setNleft(short nleft) {
        this.nleft = nleft;
    }

    public boolean equals(Object o) {
        if(!(o instanceof Token)) return false;
        Token t = (Token)o;
        return this.nright == t.getNright() && this.nleft == t.nleft;

    }

    public int hashCode() {
        int hash = nleft + nright;
        return hash;
    }

    public String graphicFormat () {

        return ("<"+nleft+">--<"+nright+">");
    }
}
