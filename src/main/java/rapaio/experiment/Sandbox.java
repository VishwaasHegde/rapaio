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

package rapaio.experiment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a> on 10/26/15.
 */
public class Sandbox {
    public static void main(String[] args) throws IOException, URISyntaxException {

        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i + 5;
        }
        String s = IntStream.iterate(99, operand -> operand - 1).limit(100).boxed().map(i -> String.valueOf(arr[i])).collect(Collectors.joining(" "));
        System.out.println(s);
    }
}
