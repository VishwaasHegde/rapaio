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
 */

package rapaio.stream;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtil {

    public static <E> Stream<List<E>> partition(Stream<E> in, int size) {
        return StreamSupport.stream(new PartitioningSpliterator<>(in.spliterator(), size), false);
    }

    public static <E> Stream<List<E>> partition(Stream<E> in, int size, int batchSize) {
        return StreamSupport.stream(
                new FixedBatchSpliterator<>(new PartitioningSpliterator<>(in.spliterator(), size), batchSize), false);
    }
}