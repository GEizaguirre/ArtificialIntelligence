import static java.lang.Math.pow;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.sqrt;

enum HEURISTIC { DIS, RAS, DVAL, DVALD, RVAL, RVALD, YDIS, VDIF, VAL, YDDIF, LDIS };

public class Heuristics {
    public static float defineDrawLine(int x1, int y1, int x2, int y2) {

        float numPoints = 0;
        // Useful values.
        int dy = Math.abs(y2 - y1);
        int dx = Math.abs(x2 - x1);
        int ddy = 2 * dy;
        int ddx = 2 * dx;
        int nx, ny;
        // Direction increments depend on the sense of the line.
        int incx = x1 < x2 ? 1 : -1; // increment direction
        int incy = y1 < y2 ? 1 : -1;

        // Decision element.
        int pk;

        // Control of horizontal lines.
        if ( dy == 0 ){
            while (true){
                numPoints++;
                if (x1==x2) break;
                x1+=incx;
            }
        }
        else {

            // Control of vertical lines.
            if ( dx == 0) {
                while (true){
                    numPoints++;
                    if (y1==y2) break;
                    y1+=incy;
                }
            }
            // Rest of lines.
            else {
                // Pendent.
                float pend = ((float)dy / (float)dx);

                if ( pend == 1){
                    while (true){
                        numPoints++;
                        if (x1==x2) break;
                        y1+=incy;
                        x1+=incx;
                    }
                }
                else {
                    // Procedure depends on the pendent value.
                    if (pend > 1) {

                        pk = ddx;

                        numPoints++;

                        while (y1 != y2) {

                            y1 += incy;
                            nx = pk > dy ? (x1 + incx) : x1;
                            pk = pk + ddx - ddy * Math.abs(nx - x1);
                            x1 = nx;
                            numPoints++;
                        }

                    } else {

                        pk = ddy;

                        numPoints++;

                        while (x1 != x2) {

                            x1 += incx;
                            ny = pk > dx ? (y1 + incy) : y1;
                            pk = pk + ddy - ddx * Math.abs(ny - y1);
                            y1 = ny;
                            numPoints++;
                        }
                    }
                }
            }
        }
        return numPoints;
    }

    public static float distance (int x1, int y1, int x2, int y2){
        return (float) sqrt( pow( y2 - y1, 2 ) + pow( x2-x1, 2 ) );
    }

    public static float valDis (int x1, int y1, int x2, int y2){
        return (float) sqrt( pow( y2 - y1, 2 ) + pow( x2-x1, 2 ) ) * Graph.getValue(x1, y1);
    }

    public static float valDisDif (int x1, int y1, int x2, int y2){
        return (float) sqrt( pow( y2 - y1, 2 ) + pow( x2-x1, 2 ) ) * abs( Graph.getValue(x1, y1) - Graph.getValue(x2, y2) ) ;
    }

    public static float valRasDif (int x1, int y1, int x2, int y2){
        return defineDrawLine(x1, y1, x2, y2) * abs ( Graph.getValue(x1, y1) - Graph.getValue(x2, y2) );
    }

    public static float valRas (int x1, int y1, int x2, int y2){
        return defineDrawLine(x1, y1, x2, y2) * Graph.getValue(x1, y1);
    }

    public static float yDis (int y1, int y2){
        return abs(y2 - y1);
    }

    public static float vDif (int x1, int y1, int x2, int y2){
        return abs(Graph.getValue(x1, y1)-Graph.getValue(x2, y2));
    }

    public static float val (int x1, int y1){
        return abs(Graph.getValue(x1, y1));
    }

    public static float yDisDif (int x1, int y1, int x2, int y2){
        return abs(Graph.getValue(x1, y1)-Graph.getValue(x2, y2))*yDis(y2, y1);
    }

    public static float lDis (int x1, int y1, int x2, int y2){
        float nnode = 0;
        int incrx = (x2 > x1) ? 1 : -1;
        int incry = (y2 > y1) ? 1 : -1;
        for (; (y1 != y2 && y1 != Graph.getMapSize()-1); y1 += incry){
            nnode ++;
        }
        for (; (x1 != x2 && x1 != Graph.getMapSize()-1); x1 += incrx){
            nnode ++;
        }
        for (; (y1 != y2 && y1 != Graph.getMapSize()-1); y1 += incry){
            nnode ++;
        }
        return nnode;
    }
}
