This tutorial is a step-by-step guide for unit test of [TIBCO BusinessEvents](https://docs.tibco.com/products/tibco-businessevents-5-2-0) (BE) applications.  It builds 2 projects from scratch to demonstrate steps for adding unit tests to a BusinessEvents sample project:
 - FraudDetectionCache, which is a sample from BusinessEvents 5.2.  This tutorial will add unit tests for the rules and rule functions to this project.
 - FraudDetectionTest, which is a Maven project for driving the unit tests using JUnit.

You may follow this tutorial to build the 2 projects from scratch, or you may skip to the last section to configure and execute the pre-built projects of this tutorial.

The following 3 projects contain source code and instructions for the test utilities used by this tutorial:
 - [DataTypeDemo](https://github.com/yxuco/DataTypeDemo), which contains more samples of unit tests and assertions for BusinessEvents applications.  The project library, `testservice.projlib`, is extracted from this project.
 - [BE Assert](https://github.com/yxuco/beassert), which contains source code of assertion functions for BE unit tests.  The catalog function jar `beassert-1.0.jar` is packaged from this project.
 - [BE Unit](https://github.com/yxuco/beunit), which contains utility functions to drive the unit tests using JUnit.  The test driver, `FraudDetectionTest` is based on this project.

## Introduction

As business agility has put more and more pressure on IT organization to deliver application updates continuously, automated unit testing has become a crucial requirement for all applications.  Rule-based systems like TIBCO BusinessEvents are no exception.  Even though it is challenging to test individual rules and functions in isolation, developers must be able to write unit tests to assess the correctness of individual rules and functions due to the following reasons:
 - Unit test provides more confidence to developers when they update or add functionality to applications, and thus a full set of unit tests encourages code refactoring.
 - Unit test improves the code coverage, especially for rule-based systems, it is harder to improve code coverage by adding functional tests.
 - Unit test helps documenting the code, and improves application implementation by ensuring the testability of the code.

This tutorial shows that unit test of individual rules and runctions is not only necessary, but also achievable for TIBCO BusinessEvents applications. 

## Clone the tutorial utility

This tutorial requires installation of Git and Maven.  The project [BE Unit](https://github.com/yxuco/beunit) describes steps for setting up the development environment.

Clone the tutorial code and utilities from GitHub using the following command:

    git clone https://github.com/yxuco/betest_tutorial.git

This will download the tutorial to a folder `betest_tutorial`.  It contains 2 projects, `FraudDetectionCache` and `FraudDetectionTest`, which will be rebuilt from scratch in the following sections.  The `lib` folder contains jars and BE project library used by this tutorial.  If you are curious, you may build the jar and project library from source code in [BE Assert](https://github.com/yxuco/beassert) and [DataTypeDemo](https://github.com/yxuco/DataTypeDemo).

## Create and configure FraudDetectionCache
In this section, we import the BE sample, `FraudDetectionCache`, and configure it for unit testing.

#### Import FraudDetectionCache into BusinessEvents studio

 - Launch BusinessEvents Studio, pull down menu **File -> Import…**
 - In **Import** dialog, select **Existing TIBCO BusinessEvents Studio Project**, click **Next >**
 - **Browse** `Exixting project root directory`, and select $BE_HOME/examples/standard/FraudDetectionCache/FraudDetectionCache
 - Check `Copy project into workspace` if it is not already checked, click **Finish**
 - In Studio Explorer, right click root of the imported project **FraudDetectionCache**, in popup menu, select **Build Enterprise Archive…**
 - Browse `File Location`, and specify `fdcache.ear` under the folder `betest-tutorial`.  Do not worry to override the `ear` file from GitHub.

The project should build successfully, and generate an `ear` file.

#### Configure FraudDetectionCache for unit test

 - In Studio Explorer, right click root of the project **FraudDetectionCache**, in popup menu, select **Properties**
 - Select **Build Path**, in **Project Libraries** panel, click **Add Library…**
 - Select file `betest_tutorial/lib/testservice.projlib` from the downloaded Git repository, Click **Open**
 - Click **Java Libraries** tab, and then **Add Library…**, select and add file `betest_tutorial/lib/beassert-1.0.jar` as a **Third Party** library
 - Click **OK** to exit the build path configuration

These steps added the test-service component and the assert catalog functions to the project.  It uses 2 **Global Variables** that we need to define next.
 - Highlight the project root **FraudDetectionCache** in the Studio Explorer, then pull down menu **Project -> Edit Global Variables**
 - Add Group **TestService**
 - Under **TestService** group, add variable **Host** = localhost
 - Under **Testservice** group, add variable **Port** = 8989

This port number will be used by the test driver `FraudDetectionTest` to send test requests via HTTP.

Open `fdcache.cdd`, modify the configuration as follows
 - In **Agent Classes** panel, highlight **Inference-class (Inference)**, turn on **Concurrent RTC**
 - Open folder **Input Destination Collections**, and add the following 2 destinations
 - **TestFunction_01**, URI=`/testservice/test/function`, Preprocessor=`/testservice/RuleFunctions/processFunctionRequest`
 - **TestTrigger_01**, URI=`/testservice/TestScheduler/TestTriggerChannel/PreprocTestTrigger`, Preprocessor=`/testservice/TestSchedule/processPreprocTestTrigger`
 - In **Processing Units** panel, highlight **default** PU, turn on **Enable Cache Storage**  (This is convenient for development although not recommended for production.)

This completes all necessary configurations.  You can rebuild the `ear` to make sure that the configuration did not introduce any error.

## Write unit test cases

## Execute tests at BE engine startup

## Create and configure FraudDetectionTest

## Drive unit tests using FraudDetectionTest

## Configure and run pre-built tests