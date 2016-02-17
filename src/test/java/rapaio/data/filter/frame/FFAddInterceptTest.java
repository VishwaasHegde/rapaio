/*
 * Apache License
 * Version 2.0, January 2004
 * http://www.apache.org/licenses/
 *
 *    Copyright 2013 Aurelian Tutuianu
 *    Copyright 2014 Aurelian Tutuianu
 *    Copyright 2015 Aurelian Tutuianu
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

package rapaio.data.filter.frame;

import org.junit.Assert;
import org.junit.Test;
import rapaio.data.Frame;
import rapaio.data.Numeric;
import rapaio.data.SolidFrame;

/**
 * Created by <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a> on 2/10/16.
 */
public class FFAddInterceptTest {

    @Test
    public void testInterceptValues() {
        Frame before = SolidFrame.wrapOf(Numeric.fill(100, 1).withName("a"));
        Frame after = new FFAddIntercept().newInstance().filter(before);

        Assert.assertTrue(after.varCount()==2);
        Assert.assertEquals(FFAddIntercept.INTERCEPT, after.varNames()[0]);

        Frame again = new FFAddIntercept().filter(after);
        Assert.assertTrue(after.equals(again));
    }
}