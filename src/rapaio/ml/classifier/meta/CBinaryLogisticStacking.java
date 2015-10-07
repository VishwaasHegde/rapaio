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

package rapaio.ml.classifier.meta;

import rapaio.data.*;
import rapaio.ml.classifier.linear.BinaryLogistic;
import rapaio.ml.classifier.AbstractClassifier;
import rapaio.ml.classifier.CFit;
import rapaio.ml.classifier.Classifier;
import rapaio.ml.common.Capabilities;
import rapaio.sys.WS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a> on 9/30/15.
 */
public class CBinaryLogisticStacking extends AbstractClassifier {

    private static final long serialVersionUID = -9087871586729573030L;

    private List<Classifier> weaks = new ArrayList<>();
    private BinaryLogistic log = new BinaryLogistic();
    private double tol = 1e-5;
    private int maxRuns = 1_000_000;

    public CBinaryLogisticStacking withLearners(Classifier... learners) {
        weaks.clear();
        Collections.addAll(weaks, learners);
        return this;
    }

    public CBinaryLogisticStacking withTol(double tol) {
        this.tol = tol;
        return this;
    }

    public CBinaryLogisticStacking withMaxRuns(int maxRuns) {
        this.maxRuns = maxRuns;
        return this;
    }

    @Override
    public CBinaryLogisticStacking withDebug(boolean debug) {
        return (CBinaryLogisticStacking) super.withDebug(debug);
    }

    @Override
    public Classifier newInstance() {
        return new CBinaryLogisticStacking();
    }

    @Override
    public String name() {
        return "CBinaryLogisticStacking";
    }

    @Override
    public String fullName() {
        return null;
    }

    @Override
    public Capabilities capabilities() {
        return new Capabilities()
                .withAllowMissingTargetValues(false)
                .withAllowMissingInputValues(false)
                .withLearnType(Capabilities.LearnType.BINARY_CLASSIFIER)
                .withInputTypes(VarType.BINARY, VarType.INDEX, VarType.NUMERIC)
                .withTargetTypes(VarType.NOMINAL)
                .withInputCount(1, 100_000)
                .withTargetCount(1, 1);
    }

    @Override
    public Classifier learn(Frame df, Var weights, String... targetVars) {
        if (debug()) WS.println("learn method called.");
        List<Var> vars = new ArrayList<>();
        int pos = 0;
        if (debug()) WS.println("check learners for learning.... ");
        weaks.parallelStream().map(weak -> {
            if (!weak.isLearned()) {
                if (debug()) WS.println("started learning for weak learner ...");
                weak.learn(df, weights, targetVars);
            }
            if (debug()) WS.println("started fitting weak learner...");
            return weak.fit(df).firstDensity().var(1);
        }).collect(toList()).forEach(var -> vars.add(var.solidCopy().withName("V" + vars.size())));

        List<Var> quadratic = vars.stream()
                .map(v -> v.solidCopy().stream().transValue(x -> x * x).toMappedVar().withName(v.name() + "^2").solidCopy())
                .collect(toList());
        vars.addAll(quadratic);

        List<String> targets = new VarRange(targetVars).parseVarNames(df);
        vars.add(df.var(targets.get(0)).solidCopy());

        if (debug()) WS.println("started learning for binary logistic...");
        log.withTol(tol);
        log.withMaxRuns(maxRuns);
        log.learn(SolidFrame.newWrapOf(vars), weights, targetVars);

        if (debug()) WS.println("end learn method call");
        return this;
    }

    @Override
    public CFit fit(Frame df, boolean withClasses, boolean withDistributions) {
        if (debug()) WS.println("fit method called.");
        List<Var> vars = new ArrayList<>();

        weaks.parallelStream().map(weak -> {
            if (debug()) WS.println("started fitting weak learner ...");
            return weak.fit(df).firstDensity().var(1);
        }).collect(toList()).forEach(var -> vars.add(var.solidCopy().withName("V" + vars.size())));

        List<Var> quadratic = vars.stream()
                .map(v -> v.solidCopy().stream().transValue(x -> x * x).toMappedVar().withName(v.name() + "^2").solidCopy())
                .collect(toList());
        vars.addAll(quadratic);

        if (debug()) WS.println("started fitting binary logistic regressor.. ");
        CFit fit = log.fit(SolidFrame.newWrapOf(vars));

        if (debug()) WS.println("end fit method call");
        return fit;
    }
}