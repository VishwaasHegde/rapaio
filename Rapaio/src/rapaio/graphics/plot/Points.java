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

package rapaio.graphics.plot;

import rapaio.data.Vector;
import rapaio.graphics.Plot;
import rapaio.graphics.base.Range;
import rapaio.graphics.colors.ColorPalette;
import rapaio.graphics.pch.PchPalette;

import java.awt.*;

/**
 * @author tutuianu
 */
public class Points extends PlotComponent {

    private final Vector x;
    private final Vector y;

    public Points(Plot parent, Vector x, Vector y) {
        super(parent);
        this.x = x;
        this.y = y;

        if (plot.getBottomLabel() == null) {
            plot.setBottomLabel(x.getName());
        }
        if (plot.getLeftLabel() == null) {
            plot.setLeftLabel(y.getName());
        }
    }

    @Override
    public Range getComponentDataRange() {
        if (x.getRowCount() == 0) {
            return null;
        }
        Range range = new Range();
        for (int i = 0; i < x.getRowCount(); i++) {
            if (x.isMissing(i) || y.isMissing(i)) {
                continue;
            }
            range.union(x.getValue(i), y.getValue(i));
        }
        return range;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setBackground(ColorPalette.STANDARD.getColor(255));

        for (int i = 0; i < x.getRowCount(); i++) {
            if (x.isMissing(i) || y.isMissing(i)) {
                continue;
            }
            g2d.setColor(opt().getColor(i));
            int xx = (int) (plot.xscale(x.getValue(i)));
            int yy = (int) (plot.yscale(y.getValue(i)));
            PchPalette.STANDARD.draw(g2d, xx, yy, opt().getSize(i), opt().getPch(i));
        }
    }
}
