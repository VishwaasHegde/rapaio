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

package rapaio.ml.regressor.tree;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by <a href="mailto:padreati@yahoo.com>Aurelian Tutuianu</a> on 11/24/14.
 */
@Deprecated
public interface RTreeTestFunction extends Serializable {

    String name();

    double computeTestValue(double... variances);

    ////////////////////
    // implementations

    RTreeTestFunction VARIANCE_SUM = new RTreeTestFunction() {

        private static final long serialVersionUID = 5119966657929147020L;

        @Override
        public String name() {
            return "VARIANCE_SUM";
        }

        @Override
        public double computeTestValue(double... variances) {
            return Arrays.stream(variances).sum();
        }
    };

    RTreeTestFunction STD_SUM = new RTreeTestFunction() {

        private static final long serialVersionUID = 4399865122423562865L;

        @Override
        public String name() {
            return "STD_SUM";
        }

        @Override
        public double computeTestValue(double... variances) {
            return Arrays.stream(variances).map(Math::sqrt).sum();
        }
    };
}