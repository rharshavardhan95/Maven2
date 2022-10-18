package org.apache.maven.plugins.dependency;

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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public final class DirectoryUtil
{
    public static void deleteDirectories( File directory )
    {
        Path path = Paths.get( directory.getAbsolutePath() );
        deleteDirectories( path );
    }

    public static void deleteDirectories( Path directory )
    {
        if ( !Files.exists( directory ) )
        {
            return;
        }

        try ( Stream<Path> dirs = Files.walk( directory ) )
        {
            dirs.filter( Files::exists )
                    .sorted( Comparator.reverseOrder() )
                    .map( Path::toFile )
                    .forEach( File::delete );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }

    }

}
