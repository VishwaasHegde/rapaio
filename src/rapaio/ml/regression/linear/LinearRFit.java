/*
 * Apache License
 * Version 2.0, January 2004
 * http://www.apache.org/licenses/
 *
 *    Copyright 2013 Aurelian Tutuianu
 *    Copyright 2014 Aurelian Tutuianu
 *    Copyright 2015 Aurelian Tutuianu
 *    Copyright 2016 Aurelian Tutuianu
 *    Copyright 2017 Aurelian Tutuianu
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

package rapaio.ml.regression.linear;

import rapaio.core.distributions.StudentT;
import rapaio.data.Frame;
import rapaio.data.NumericVar;
import rapaio.math.MTools;
import rapaio.math.linear.RM;
import rapaio.math.linear.RV;
import rapaio.math.linear.dense.QRDecomposition;
import rapaio.math.linear.dense.SolidRM;
import rapaio.ml.regression.RFit;
import rapaio.printer.Summary;
import rapaio.printer.format.TextTable;
import rapaio.sys.WS;

/**
 * Created by <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a> at 12/1/14.
 */
public class LinearRFit extends RFit {

    private LinearRegression lm;

    public LinearRFit(LinearRegression model, Frame df, boolean withResiduals) {
        super(model, df, withResiduals);
        this.lm = model;
    }

    @Override
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append(lm.getHeaderSummary());
        sb.append("\n");

        for (int i = 0; i < lm.targetNames().length; i++) {
            String targetName = lm.targetName(i);
            sb.append("Target <<< ").append(targetName).append(" >>>\n\n");

            if (!withResiduals) {
                sb.append("> Coefficients: \n");
                RV coeff = lm.getCoefficients(i);

                TextTable tt = TextTable
                        .newEmpty(coeff.count() + 1, 2)
                        .withHeaderRows(1);
                tt.set(0, 0, "Name", 0);
                tt.set(0, 1, "Estimate", 0);
                for (int j = 0; j < coeff.count(); j++) {
                    tt.set(j + 1, 0, lm.inputName(j), -1);
                    tt.set(j + 1, 1, WS.formatFlex(coeff.get(j)), -1);
                }
                sb.append(tt.getSummary());
            } else {
                NumericVar res = residuals.get(targetName);

                int degrees = res.getRowCount() - model.inputNames().length;
                double var = rss.get(targetName) / degrees;
                double rs = rsquare.get(targetName);
                RV coeff = lm.getCoefficients(i);
                double rsa = (rs * (res.getRowCount() - 1) - coeff.count() + 1) / degrees;
                // TODO
                int fdegree1 = model.inputNames().length - 1;
                double fvalue = (ess.get(targetName) * degrees) / (rss.get(targetName) * (fdegree1));
                double fpvalue = MTools.fdist(fvalue, fdegree1, degrees);

                RM X = SolidRM.copy(df.mapVars(model.inputNames()));
                RM m_beta_hat = QRDecomposition.from(X.t().dot(X)).solve(SolidRM.identity(X.getColCount()));

                double[] beta_std_error = new double[model.inputNames().length];
                double[] beta_t_value = new double[model.inputNames().length];
                double[] beta_p_value = new double[model.inputNames().length];
                String[] beta_significance = new String[model.inputNames().length];
                for (int j = 0; j < model.inputNames().length; j++) {
                    beta_std_error[j] = Math.sqrt(m_beta_hat.get(j, j) * var);
                    beta_t_value[j] = coeff.get(j) / beta_std_error[j];
                    double pValue = new StudentT(degrees).cdf(-Math.abs(beta_t_value[j])) * 2;
                    beta_p_value[j] = pValue;
                    String signif = " ";
                    if (pValue <= 0.1)
                        signif = ".";
                    if (pValue <= 0.05)
                        signif = "*";
                    if (pValue <= 0.01)
                        signif = "**";
                    if (pValue <= 0.001)
                        signif = "***";
                    beta_significance[j] = signif;
                }

                sb.append("> Residuals: \n");
                sb.append(Summary.getHorizontalSummary5(res));
                sb.append("\n");

                sb.append("> Coefficients: \n");

                TextTable tt = TextTable.newEmpty(coeff.count() + 1, 6).withHeaderRows(1).withHeaderCols(1);

                tt.set(0, 0, "Name", 1);
                tt.set(0, 1, "Estimate", 1);
                tt.set(0, 2, "Std. error", 1);
                tt.set(0, 3, "t value", 1);
                tt.set(0, 4, "P(>|t|)", 1);
                tt.set(0, 5, "", 1);
                for (int j = 0; j < coeff.count(); j++) {
                    tt.set(j + 1, 0, model.inputName(j), -1);
                    tt.set(j + 1, 1, WS.formatMedium(coeff.get(j)), 1);
                    tt.set(j + 1, 2, WS.formatMedium(beta_std_error[j]), 1);
                    tt.set(j + 1, 3, WS.formatMedium(beta_t_value[j]), 1);
                    tt.set(j + 1, 4, WS.formatPValue(beta_p_value[j]), 1);
                    tt.set(j + 1, 5, beta_significance[j], -1);
                }
                sb.append(tt.getSummary());
                sb.append("--------\n");
                sb.append("Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1\n\n");


                sb.append(String.format("Residual standard error: %s on %d degrees of freedom\n",
                        WS.formatFlex(Math.sqrt(var)),
                        degrees));
                sb.append(String.format("Multiple R-squared:  %s, Adjusted R-squared:  %s\n",
                        WS.formatFlex(rs), WS.formatFlex(rsa)));
                sb.append(String.format("F-statistic: %s on %d and %d DF,  p-value: %s\n",
                        WS.formatFlexShort(fvalue),
                        fdegree1,
                        degrees,
                        WS.formatPValue(fpvalue)));
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}