package rapaio.graphics.base;

import org.junit.Test;
import rapaio.data.NumVar;
import rapaio.graphics.Plotter;
import rapaio.sys.WS;

import java.time.LocalTime;

/**
 * Created by <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a> on 9/11/17.
 */
public class XWilkinsonTest {

    @Test
    public void sandboxTest() {
        XWilkinson x = XWilkinson.base10(XWilkinson.DEEFAULT_EPS);
        XWilkinson.Labels l = x.searchBounded(1.1e-100, 3e-100, 10);
        WS.println(l.toString());

        NumVar xx = NumVar.seq(l.getList().size());
        NumVar yy = NumVar.from(l.getList().size(), row -> l.getList().get(row));

//        WS.setPrinter(new IdeaPrinter());
        WS.draw(Plotter.points(yy, xx));
    }


    @Test
    public void baseTest() {
        XWilkinson x = XWilkinson.base10(XWilkinson.DEEFAULT_EPS);

        WS.println(x.searchBounded(1e-20, 3e-20, 10).toString());

        // First examples taken from the paper pg 6, Fig 4
        x.loose = true;
        System.out.println(x.search(-98.0, 18.0, 3).toString());
        x.loose = false;
        System.out.println(x.search(-98.0, 18.0, 3).toString());

        System.out.println();

        x.loose = true;
        System.out.println(x.search(-1.0, 200.0, 3).toString());
        x.loose = false;
        System.out.println(x.search(-1.0, 200.0, 3).toString());

        System.out.println();

        x.loose = true;
        System.out.println(x.search(119.0, 178.0, 3).toString());
        x.loose = false;
        System.out.println(x.search(119.0, 178.0, 3).toString());

        System.out.println();

        x.loose = true;
        System.out.println(x.search(-31.0, 27.0, 4).toString());
        x.loose = false;
        System.out.println(x.search(-31.0, 27.0, 3).toString());

        System.out.println();

        x.loose = true;
        System.out.println(x.search(-55.45, -49.99, 2).toString());
        x.loose = false;
        System.out.println(x.search(-55.45, -49.99, 3).toString());

        System.out.println();
        x.loose = false;
        System.out.println(x.search(0, 100, 2).toString());
        System.out.println(x.search(0, 100, 3).toString());
        System.out.println(x.search(0, 100, 4).toString());
        System.out.println(x.search(0, 100, 5).toString());
        System.out.println(x.search(0, 100, 6).toString());
        System.out.println(x.search(0, 100, 7).toString());
        System.out.println(x.search(0, 100, 8).toString());
        System.out.println(x.search(0, 100, 9).toString());
        System.out.println(x.search(0, 100, 10).toString());

        System.out.println("Some additional tests: Testing with base2");
        x = XWilkinson.base2(XWilkinson.DEEFAULT_EPS);
        System.out.println(x.search(0, 32, 8).toString());

        System.out.println("Quick experiment with minutes: Check the logic");
        x = XWilkinson.forMinutes(XWilkinson.DEEFAULT_EPS);
        System.out.println(x.search(0, 240, 16));
        System.out.println(x.search(0, 240, 9));

        System.out.println("Quick experiment with minutes: Convert values to HH:mm");
        LocalTime start = LocalTime.now();
        LocalTime end = start.plusMinutes(245); // add 4 hrs 5 mins (245 mins) to the start

        int dmin = start.toSecondOfDay() / 60;
        int dmax = end.toSecondOfDay() / 60;
        if (dmin > dmax) {
            // if adding 4 hrs exceeds the midnight simply swap the values this is just an
            // example...
            int swap = dmin;
            dmin = dmax;
            dmax = swap;
        }
        System.out.println("dmin: " + dmin + " dmax: " + dmax);
        XWilkinson.Labels labels = x.search(dmin, dmax, 15);
        System.out.println("labels");
        for (double time = labels.getMin(); time < labels.getMax(); time += labels.getStep()) {
            LocalTime lt = LocalTime.ofSecondOfDay(Double.valueOf(time).intValue() * 60);
            System.out.println(lt);
        }
    }
}
