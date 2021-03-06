package rapaio.ml.classifier.boost;

import org.junit.Test;
import rapaio.core.SamplingTools;
import rapaio.data.*;
import rapaio.data.sample.RowSampler;
import rapaio.datasets.Datasets;
import rapaio.io.Csv;
import rapaio.ml.classifier.CFit;
import rapaio.ml.classifier.Classifier;
import rapaio.ml.classifier.ensemble.CForest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a> on 1/4/18.
 */
public class GBTClassifierTest {

//    @Test
    public void simpleTest() throws IOException, URISyntaxException {

        Frame df = Datasets.loadCoverType();
        df.printSummary();

        List<Frame> frames = SamplingTools.randomSampleStratifiedSplit(df, "Cover_Type", 0.01);

        Frame train = frames.get(0);
        Frame test = frames.get(1);

        train.printSummary();

        List<Classifier> c = new ArrayList<>();
//        c.add(CForest.newRF().withRuns(2));
//        c.add(CForest.newRF().withRuns(10));

        c.add(new GBTClassifier().withRuns(2));
        c.add(new GBTClassifier().withRuns(10));
        c.add(new GBTClassifier().withRuns(50));
        c.add(new GBTClassifier().withRuns(100));


        for (Classifier cf : c) {

            cf.train(train, "Cover_Type");
            CFit fit = cf.fit(test, true, true);

            fit.printSummary();
        }
    }
}
