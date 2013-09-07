/*
 * Copyright 2013 Aurelian Tutuianu
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
 */

package rapaio.data;

import java.util.*;

/**
 * @author Aurelian Tutuianu
 */
public class NominalVector extends AbstractVector {

    private static final String missingValue = "?";
    private static final int missingIndex = 0;
    private final String[] terms;
    private final int[] indexes;

    public NominalVector(String name, int size, String[] dict) {
        this(name, size, Arrays.asList(dict));
    }

    public NominalVector(String name, int size, Collection<String> dict) {
        super(name);
        TreeSet<String> copy = new TreeSet<>(dict);
        copy.remove(missingValue);
        terms = new String[copy.size() + 1];
        Iterator<String> it = copy.iterator();
        int pos = 0;
        terms[pos++] = missingValue;
        while (it.hasNext()) {
            terms[pos++] = it.next();
        }
        this.indexes = new int[size];
    }

    @Override
    public boolean isNumeric() {
        return false;
    }

    @Override
    public boolean isNominal() {
        return true;
    }

    @Override
    public int getRowCount() {
        return indexes.length;
    }

    @Override
    public int getRowId(int row) {
        return row;
    }

    @Override
    public int getIndex(int row) {
        return indexes[row];
    }

    @Override
    public void setIndex(int row, int value) {
        indexes[row] = value;
    }

    @Override
    public double getValue(int row) {
        return indexes[row];
    }

    @Override
    public void setValue(int row, double value) {
    }

    @Override
    public String getLabel(int row) {
        return terms[indexes[row]];
    }

    @Override
    public void setLabel(int row, String value) {
        if (value.equals(missingValue)) {
            indexes[row] = missingIndex;
            return;
        }
        int idx = Arrays.binarySearch(terms, 1, terms.length, value);
        if (idx < 0) {
            throw new IllegalArgumentException("Can't set a getLabel that is not defined.");
        }
        indexes[row] = idx;
    }

    @Override
    public String[] dictionary() {
        return Arrays.copyOf(terms, terms.length);
    }

    @Override
    public boolean isMissing(int row) {
        return missingIndex == getIndex(row);
    }

    @Override
    public void setMissing(int row) {
        setIndex(row, missingIndex);
    }

    @Override
    public Comparator<Integer> getComparator(final boolean asc) {
        final int sign = asc ? 1 : -1;

        return new Comparator<Integer>() {
            @Override
            public int compare(Integer row1, Integer row2) {
                if (isMissing(row1) && isMissing(row2)) {
                    return 0;
                }
                if (isMissing(row1)) {
                    return -sign;
                }
                if (isMissing(row2)) {
                    return sign;
                }
                if (getIndex(row1) == getIndex(row2)) {
                    return 0;
                }
                return sign * (getIndex(row1) < getIndex(row2) ? -1 : 1);
            }
        };
    }
}
