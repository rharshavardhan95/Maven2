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
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilTest
{

    @Test
    public void two_lists_one_with_duplicates()
    {
        List<String> s1 = new ArrayList<>();
        s1.add( "1" );
        s1.add( "2" );
        s1.add( "2" );
        s1.add( "3" );
        List<String> s2 = new ArrayList<>();
        s2.add( "3" );
        s2.add( "4" );
        Collection<String> result = Util.symmetricDifference( s1, s2 );

        assertThat( result ).containsExactly( "1", "2", "2", "4" );
    }

    @Test
    public void two_different_sets_with_commons_elements()
    {
        Set<String> s1 = new HashSet<>();
        s1.add( "1" );
        s1.add( "2" );
        s1.add( "3" );
        s1.add( "4" );
        s1.add( "5" );
        Set<String> s2 = new HashSet<>();
        s2.add( "2" );
        s2.add( "4" );
        s2.add( "6" );
        Collection<String> result = Util.symmetricDifference( s1, s2 );

        assertThat( result ).containsExactly( "1", "3", "5", "6" );
    }

    @Test
    public void second_set_only()
    {
        Set<String> s1 = new HashSet<>();
        s1.add( "1" );
        s1.add( "2" );
        s1.add( "3" );
        s1.add( "4" );
        s1.add( "5" );
        Set<String> s2 = new HashSet<>();
        s2.add( "3" );
        s2.add( "5" );
        Collection<String> result = Util.symmetricDifference( s1, s2 );

        assertThat( result ).containsExactly( "1", "2", "4" );
    }

    @Test
    public void list_with_duplicate()
    {
        List<String> s1 = new ArrayList<>();
        s1.add( "1" );
        s1.add( "2" );
        s1.add( "2" );

        Collection<String> result = Util.symmetricDifference( s1, new LinkedHashSet<>( s1 ) );

        assertThat( result ).containsExactly( "2" );
    }

    @Test
    public void list_with_tripple_entries()
    {
        List<String> s1 = new ArrayList<>();
        s1.add( "1" );
        s1.add( "2" );
        s1.add( "2" );
        s1.add( "2" );

        Collection<String> result = Util.symmetricDifference( s1, new LinkedHashSet<>( s1 ) );

        assertThat( result ).containsExactly( "2", "2" );
    }
}
