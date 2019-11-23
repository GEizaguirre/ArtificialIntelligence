public class Token {
    private Integer nright, nleft;

    public Token(Integer nright, Integer nleft) {
        this.nright = nright;
        this.nleft = nleft;
    }

    public Integer getNright() {
        return nright;
    }

    public void setNright(Integer nright) {
        this.nright = nright;
    }

    public Integer getNleft() {
        return nleft;
    }

    public void setNleft(Integer nleft) {
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

        if (nleft<nright) return ("<"+nleft+">--<"+nright+">");
        else return ("<"+nright+">--<"+nleft+">");
    }
}
