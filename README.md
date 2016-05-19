rapaio
===========
Statistics, data mining and machine learning toolbox in Java.

[![build status](https://travis-ci.org/padreati/rapaio.svg?branch=master)](https://travis-ci.org/padreati/rapaio)
[![codecov.io](https://codecov.io/github/padreati/rapaio/coverage.svg?branch=master)](https://codecov.io/github/padreati/rapaio?branch=master)

![codecov.io](https://codecov.io/github/padreati/rapaio/branch.svg?branch=master)

**Rapaio Manual** is now published on [gitbooks.com](https://www.gitbook.com/book/aureliantutuianu/rapaio-manual/details).
Using the previous link you can select the format of manual. To read it directly online you can use this direct link:
[Rapaio Manual - read on-line](https://aureliantutuianu.gitbooks.io/rapaio-manual/content/).

**NEW**: The manual contains a [Tutorial on Kaggle's Titanic Competition](https://aureliantutuianu.gitbooks.io/rapaio-manual/content/kaggle-titanic.html)!

Implemented Features
====================
For each feature there are some notes regarding the development stage. 
If there are no notes it means the feature is considered to be fully implemented and well tested.

**Core Statistics**

* Special Math functions
* Maximum, Minimum, Mode (only for nominal values), Sum, Mean, Variance, Quantiles
* Online Statistics: minimum, maximum, count, mean, variance, standard deviation, skewness, kurtosis

**Correlations**

* Pearson product-moment coefficient
* Spearman's rank correlation coefficient

**Distributions**

* Normal/Gaussian
* Student t

**Sampling**

* SamplingTool
    * generates discrete integer samples with/without replacement, weighted/non-weighted
    * offers utility methods for bootstraps, simple random, stratified sampling
* Samplers used in machine learning algorithms

**Hypothesis Testing**

* Kolmogorov Smirnoff one/two sample test
* Pearson Chi-Square goodness of fit / independence test

**Frame Filters**

* FFJitter - add jitter to data according with a noise distribution
* FFAddIntercept - add an intercept variable to a given data set
* FFMapVars - select some variables according with a VRange pattern
* FFRemoveVars - removes some variables according with a VRange pattern
* FFStandardize - standardize variables from a given data frame
* FFRandomProjection - project a data frame onto random projections
    
**Var filters**

* VFCumulativeSum - builds a numeric vector with a cumulative sum
* VFJitter - adds noise to a given numeric vector according with a noise distribution
* VFRefSort - sorts a variable according with a given set of row comparators
* VFShuffle - shuffles values from a variable
* VFSort - sorts a variable according with default comparator
* VFStandardize - standardize values from a given numeric variable
* VFToIndex - transforms a variable into an index type using a lambda
* VFToNumeric - transforms a variable into numeric using a lambda
* VFTransformPower - transform a variable withg power transform
* VFUpdate - updates a variable using a lambda on VSpot
* VFUpdateIndex - updates a variable using a lambda on index value
* VFUpdateLabel - updates a variable using a lambda on label value
* VFUpdateValue - updates a variable using a lambda on double value

**Analysis**

* Principal Components Analysis
* Fischer Linear Discriminant Analysis

**Classification**

* Bayesian: NaiveBayes (GaussianPdf, EmpiricalPdf, MultinomialPmf)
* Linear: BinaryLogistic
* Rule: OneRule
* Decision Trees - CTree: DecisionStump, ID3, C45, CART
  * purity: entropy, infogain, gain ration, gini index
  * weight on instances
  * split: numeric binary, nominal binary, nominal full
  * missing value handling: ignore, random, majority, weighted
  * reduced-error pruning
  * variable importance: frequency, gain and permutation based
* Ensemble: CForest - Bagging, Random Forests
* Boosting: AdaBoost.SAMME
* SVM: BinarySMO (Platt)

**Regression**

* Simple: ConstantRegression
* Simple: L1Regression
* Simple: L2Regression

**Clusterization**

* KMeans clustering

**Graphics**

* QQ Plot
* Box Plot
* Histogram
* 2d Histogram
* Plot function line
* Plot vertical/horizontal/ab line
* Plot lines
* Plot points
* Density line KDE
* ROC Curve


Experminental Stage Features
============================

**Core Statistics**

* Root Mean Squared Error
* Median Absolute Error
* Receiver Operator Characteristic - ROC curves and ROC Area
* Confusion Matrix


**Distributions**

* Continuous/Discrete Uniform
* Bernoulli
* Binomial
* ChiSquare
* Gamma
* Empirical KDE (gaussian, epanechnikov, cosine, tricube, biweight, triweight, triangular, uniform)

**Classification**

* Boosting: GBT (Gradient Boosting Trees) Classifier
* Ensemble: SplitClassifier

**Regression**

* Simple: RandomValueRegressor
* Tree: CART (no pruning)
* Tree: C45 (no pruning)
* Tree: DecisionStump
* Boost: GBT (Gradient Boosting Tree) Regressor
* NNet: MultiLayer Perceptron Regressor
* Function: OLSRegressor (one target, only numerical attributes, no summary)

**Matrices and vectors**

* Numeric vector operations
* Basic matrix operations and matrix decompositions

**Graphics**

All the graphics components are in usable state. However the graphics customization needs
further improvements in order to make the utilization easier.

* Plot legend
* BarChart
