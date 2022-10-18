package org.apache.maven.plugins.dependency.analyze;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

class Util
{

    static <O> Collection<O> symmetricDifference( Collection<O> elementsOne,
                                                  Collection<O> elementsTwo )
    {
        Set<O> hashSet = new HashSet<>( elementsOne );
        hashSet.addAll( elementsTwo );

        Map<O, Integer> cardinalityOne =
                elementsOne.stream().collect( groupingBy( identity(), summingInt( e -> 1 ) ) );
        Map<O, Integer> cardinalityTwo =
                elementsTwo.stream().collect( groupingBy( identity(), summingInt( e -> 1 ) ) );

        return hashSet.stream().flatMap( item ->
        {
            int cardOne = cardinalityOne.getOrDefault( item, 0 );
            int cardTwo = cardinalityTwo.getOrDefault( item, 0 );
            int max = Math.max( cardOne, cardTwo );
            int min = Math.min( cardOne, cardTwo );
            return Collections.nCopies( max - min, item ).stream();
        } ).collect( collectingAndThen( toList(), Collections::unmodifiableList ) );
    }

}
