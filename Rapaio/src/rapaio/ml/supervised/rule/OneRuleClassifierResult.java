/*
 * Copyright 2013 Aurelian Tutuianu <padreati@yahoo.com>
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

package rapaio.ml.supervised.rule;

import rapaio.data.Frame;
import rapaio.data.Vector;
import rapaio.ml.supervised.ClassifierResult;

/**
 * @author <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a>
 */
public class OneRuleClassifierResult implements ClassifierResult {

    private final Frame test;
    private final Vector pred;

    public OneRuleClassifierResult(Frame test, Vector pred) {
        this.test = test;
        this.pred = pred;
    }

    @Override
    public Frame getTestFrame() {
        return test;
    }

    @Override
    public Vector getClassification() {
        return pred;
    }

    @Override
    public Frame getProbabilities() {
        return null;
    }

}
