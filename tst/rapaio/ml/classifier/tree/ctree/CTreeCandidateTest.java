package rapaio.ml.classifier.tree.ctree;

import org.junit.Assert;
import org.junit.Test;
import rapaio.data.Frame;
import rapaio.data.NumVar;
import rapaio.data.SolidFrame;
import rapaio.ml.classifier.tree.CTreeCandidate;
import rapaio.ml.common.predicate.RowPredicate;

import static org.junit.Assert.*;

/**
 * Created by <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a> on 11/21/17.
 */
public class CTreeCandidateTest {

    @Test
    public void basic() {
        CTreeCandidate c = new CTreeCandidate(0.1, "t1");
        c.addGroup(RowPredicate.numLessEqual("x", 1));
        c.addGroup(RowPredicate.numGreater("x", 1));

        Frame df = SolidFrame.byVars(NumVar.wrap(0).withName("x"));

        assertTrue(c.getGroupPredicates().get(0).test(0, df));
        assertFalse(c.getGroupPredicates().get(1).test(0, df));

        assertEquals(0.1, c.getScore(), 1e-10);
        assertEquals("t1", c.getTestName());

        CTreeCandidate b = new CTreeCandidate(0.2, "t2");

        assertEquals(1, c.compareTo(b));
        assertEquals(-1, b.compareTo(c));
    }
}
