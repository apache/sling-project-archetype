#!/usr/bin/env bash

#    Licensed to the Apache Software Foundation (ASF) under one
#    or more contributor license agreements.  See the NOTICE file
#    distributed with this work for additional information
#    regarding copyright ownership.  The ASF licenses this file
#    to you under the Apache License, Version 2.0 (the
#    "License"); you may not use this file except in compliance
#    with the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
#    Unless required by applicable law or agreed to in writing,
#    software distributed under the License is distributed on an
#    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
#    KIND, either express or implied.  See the License for the
#    specific language governing permissions and limitations
#    under the License.

dir=`pwd`
echo "Build the Sling Project Archetype in folder: $dir"

# Parameters:
# 1. Path to test project folder
# 2. Is this a package or all project: true or false
# 3. Type of project: s: separated, m: merged, d: ditched
function doProject {
    if [ "$2" == "true" ]; then
       profile="autoInstallPackage"
    else
       profile="autoInstallAll"
    fi
    if [ "$3" == "s" ]; then
       type="Separated"
    fi
    if [ "$3" == "m" ]; then
       type="Merged"
    fi
    if [ "$3" == "d" ]; then
       type="Ditched"
    fi
    echo
    echo
    echo "--------------------------------------------------"
    echo "   Build and Deploy Test Project"
    echo
    echo "   Path: $1"
    echo "   Is Package: $2"
    echo "   Type: $type"
    echo "--------------------------------------------------"
    echo
    echo

    if [ ! -d $1 ]; then
        echo ">>>> Folder $1 does not exist <<<<"
        quit
    fi

    cd $1
    pwd
    ls -l
    mvn clean install -P $profile
}

# Build and Install Archetype
echo
echo
echo "------------------------------------------"
echo "       Build and Install Archetype"
echo "------------------------------------------"
echo
echo
mvn clean install

doProject $dir/target/test-classes/projects/all/project/sample-test-all-sep false s
doProject $dir/target/test-classes/projects/allDeleted/project/sample-test-all-del false d
doProject $dir/target/test-classes/projects/allMerged/project/sample-test-all-mer false m

doProject $dir/target/test-classes/projects/notAll/project/sample-test-ui-sep true s
doProject $dir/target/test-classes/projects/notAllDeleted/project/sample-test-ui-del true d
doProject $dir/target/test-classes/projects/notAllMerged/project/sample-test-ui-mer true m

echo
echo
echo "------------------------------------------"
echo "         Done"
echo "------------------------------------------"
echo
echo

cd $dir
