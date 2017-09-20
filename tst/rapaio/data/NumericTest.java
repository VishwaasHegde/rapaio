/*
 * Apache License
 * Version 2.0, January 2004
 * http://www.apache.org/licenses/
 *
 *    Copyright 2013 Aurelian Tutuianu
 *    Copyright 2014 Aurelian Tutuianu
 *    Copyright 2015 Aurelian Tutuianu
 *    Copyright 2016 Aurelian Tutuianu
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package rapaio.data;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * User: <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a>
 */
public class NumericTest {

    @Test
    public void smokeTest() {
        Var v = NumericVar.empty();
        boolean flag = v.type().isNumeric();
        assertEquals(true, flag);
        assertEquals(false, v.type().isNominal());

        assertEquals(0, v.rowCount());

        try {
            NumericVar.empty(-1);
            assertTrue("should raise an exception", false);
        } catch (Throwable ignored) {
        }

        assertEquals("Numeric[name:?, rowCount:1]", NumericVar.empty(1).toString());
    }

    @Test
    public void testGetterSetter() {
        Var v = NumericVar.empty(10);
        for (int i = 0; i < 10; i++) {
            v.setValue(i, Math.log(10 + i));
        }

        for (int i = 0; i < 10; i++) {
            assertEquals(Math.log(10 + i), v.value(i), 1e-10);
            assertEquals((int) Math.rint(Math.log(10 + i)), v.index(i));
        }

        for (int i = 0; i < 10; i++) {
            v.setIndex(i, i * i);
        }

        for (int i = 0; i < 10; i++) {
            assertEquals(i * i, v.index(i));
            assertEquals(i * i, v.value(i), 1e-10);
        }

        for (int i = 0; i < v.rowCount(); i++) {
            assertEquals(String.valueOf(v.value(i)), v.label(i));
        }
        try {
            v.setLabel(0, "test");
            assertTrue("should raise an exception", false);
        } catch (Throwable ignored) {
        }

        try {
            v.addLabel("x");
            assertTrue("should raise an exception", false);
        } catch (Throwable ignored) {
        }

        try {
            v.levels();
            assertTrue("should raise an exception", false);
        } catch (Throwable ignored) {
        }

        try {
            v.setLevels();
            assertTrue("should raise an exception", false);
        } catch (Throwable ignored) {
        }
    }

    @Test
    public void testOneNumeric() {
        Var one = NumericVar.scalar(Math.PI);

        assertEquals(1, one.rowCount());
        assertEquals(Math.PI, one.value(0), 1e-10);

        one = NumericVar.scalar(Math.E);
        assertEquals(1, one.rowCount());
        assertEquals(Math.E, one.value(0), 1e-10);
    }

    @Test
    public void testWithName() {
        NumericVar x = NumericVar.copy(1, 2, 3, 5).withName("X");
        assertEquals("X", x.name());

        Var y = MappedVar.byRows(x, 1, 2);
        assertEquals("X", y.name());
        y.withName("y");
        assertEquals("y", y.name());

        assertEquals(2.0, y.value(0), 10e-10);
        assertEquals(3.0, y.value(1), 10e-10);
    }

    @Test
    public void testBuilders() {
        List<Integer> intList = new ArrayList<>();
        List<Double> doubleList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            intList.add(i + 1);
            doubleList.add(i + 1.0);
        }

        NumericVar x1 = NumericVar.copy(1, 2, 3);
        NumericVar x2 = NumericVar.copy(1.0, 2.0, 3.0);
        NumericVar x3 = NumericVar.copy(intList);
        NumericVar x4 = NumericVar.copy(doubleList);

        for (int i = 0; i < 3; i++) {
            assertEquals(x1.value(i), x2.value(i), 10e-10);
            assertEquals(x3.value(i), x4.value(i), 10e-10);
            assertEquals(x1.value(i), x3.value(i), 10e-10);
        }

        double[] y1v = new double[3];
        y1v[0] = 10;
        y1v[1] = 20;
        y1v[2] = 30;
        NumericVar y1 = NumericVar.wrap(y1v);
        y1v[1] = Double.NaN;
        y1v[2] = 100;
        for (int i = 0; i < 3; i++) {
            assertEquals(y1v[i], y1.value(i), 10e-10);
        }

        NumericVar y2 = NumericVar.copy(x2);
        for (int i = 0; i < 3; i++) {
            assertEquals(x1.value(i), y2.value(i), 10e-10);
        }

        NumericVar y3 = NumericVar.copy(1, 2, 3, 4, 5);
        Var y4 = MappedVar.byRows(y3, 3, 1, 2);
        Var y5 = NumericVar.copy(y4);

        for (int i = 0; i < 3; i++) {
            assertEquals(y4.value(i), y5.value(i), 10e-10);
        }

        NumericVar z1 = NumericVar.fill(10);
        NumericVar z2 = NumericVar.fill(10, Math.PI);

        for (int i = 0; i < 10; i++) {
            assertEquals(0, z1.value(i), 10e-10);
            assertEquals(Math.PI, z2.value(i), 10e-10);
        }
    }

    @Test
    public void testOtherValues() {
        NumericVar x = NumericVar.copy(1, 2, 3, 4).withName("x");

        x.addIndex(10);
        assertEquals(10, x.value(x.rowCount() - 1), 10e-10);

        NumericVar b = NumericVar.empty();
        b.addBinary(true);
        b.addBinary(false);

        assertEquals(true, b.binary(0));
        assertEquals(false, b.binary(1));

        assertEquals(1, b.value(0), 10e-10);
        assertEquals(0, b.value(1), 10e-10);

        b.setBinary(1, true);
        assertEquals(1, b.value(1), 10e-10);
        assertEquals(true, b.binary(1));

        NumericVar s = NumericVar.empty();
        s.addStamp(1);
        s.addStamp(-100000000000L);
        assertEquals(1L, s.stamp(0));
        assertEquals(-100000000000d, s.stamp(1), 10e-10);

        s.setStamp(1, 15);
        assertEquals(15, s.stamp(1));


        NumericVar mis = NumericVar.empty();
        mis.addMissing();
        mis.addValue(1);
        mis.addMissing();
        mis.addValue(2);
        mis.setMissing(3);

        assertTrue(mis.isMissing(0));
        assertTrue(mis.isMissing(2));
        assertTrue(mis.isMissing(3));
        assertFalse(mis.isMissing(1));
    }

    @Test
    public void testClearRemove() {
        NumericVar x = NumericVar.copy(1, 2, 3);
        x.remove(1);

        assertEquals(1, x.index(0));
        assertEquals(3, x.index(1));

        NumericVar y = x.solidCopy();

        x.clear();

        assertEquals(0, x.rowCount());

        assertEquals(2, y.rowCount());
        assertEquals(1, y.index(0));
        assertEquals(3, y.index(1));
    }
}
