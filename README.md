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
 - **Browse** `Existing project root directory`, and select `$BE_HOME/examples/standard/FraudDetectionCache/FraudDetectionCache`
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

This port will be used by the test driver `FraudDetectionTest` to send test requests via HTTP.

Open `fdcache.cdd`, modify the runtime configuration as follows
 - In **Agent Classes** panel, highlight **Inference-class (Inference)**, turn on **Concurrent RTC**
 - Open folder **Input Destination Collections**, and add the following 2 destinations
 - **TestFunction_01**, URI=`/testservice/test/function`, Preprocessor=`/testservice/RuleFunctions/processFunctionRequest`
 - **TestTrigger_01**, URI=`/testservice/TestScheduler/TestTriggerChannel/PreprocTestTrigger`, Preprocessor=`/testservice/TestSchedule/processPreprocTestTrigger`
 - In **Processing Units** panel, highlight **default** PU, turn on **Enable Cache Storage**  (This is convenient for development although not recommended for production.)

This completes all necessary configurations.  You can rebuild the `ear` to make sure that the configuration did not introduce any error.

## Write unit tests

Our goal is to write unit tests for all rules and rule functions in a BE application.  However, compared to functional or object oriented programming, rule-based program is the hardest to unit test.
 - For functions, the best testable functions are pure functions that do not have any side effects, but rule-based systems are mostly based on side effects, and so most rule functions are **not** pure functions, and thus harder to write unit tests.
 - For rules, they are not only based on side effects by updating objects in the working memory, but also they can execute in a nondeterministic sequence.

Besides, BusinessEvents applications have a few more properties that must be considered carefully in unit tests.
 - Concepts and Events are 2 different types of data structures.  Concepts are typically mutable data that are persisted and shared globally across multiple threads and JVMs.  Events are usually immutable data that are transient and not shared by multiple threads, although they can be sent between different threads.
 - Rule-functions can run in 2 different contexts, i.e., preprocessor or rule context.  A rule-function for preprocessor context behaves much like a pure function because it cannot update Concepts/Scorecards to create side-effects, although it can update other shared data structures, i.e., in-memory  mutable collections (List, Set, or Map).
 - Rules can also be executed in the 2 different contexts.  However, when Cache-Only and Concurrent-RTC are used, we can use preprocessor context to isolate the test of rules.

In this tutorial, all tests are executed in preprocessor context.  Examples for rule context and more BE data types can be found in [DataTypeDemo](https://github.com/yxuco/DataTypeDemo).

As a covention, we add all unit test functions under a separate folder `/Test`.  We explain only one of the tests for a rule `ApplyDebit`, whose action is to reduce the balance of a matching account by a debit amount.  The test is implemented as a rule-function:

````
void rulefunction Test.RuleTests.testApplyDebit {
    attribute {
        validity = ACTION;
    }
    scope {
    }
    body {
        Object logger = Log.getLogger("Test.RuleTests.testApplyDebit");
        Log.log(logger, "info", "Start Test.RuleTests.testApplyDebit");

        String acctId = "ApplyDebit";
        cleanupAccount(acctId);
		
        // create account for the test
        Concepts.Account.Account(
            acctId /*extId String */,
            20000 /*Balance double */,
            0 /*Debits double */,
            "Normal" /*Status String */,
            2000 /*AvgMonthlyBalance double */);
        Engine.executeRules();
        
        // load account into working memory
        Account acct = Cluster.DataGrid.CacheLoadConceptByExtId(acctId, true);
        Debit evt = Events.Debit.Debit(
            null /*extId String */,
            null /*payload String */,
            acctId /*AccountId String */,
            2000 /*Amount double */);
        
        // execute all rules for debit event, but only the ApplyDebit rule will be triggered
        Event.assertEvent(evt);
        Engine.executeRules();
        
        // reload updated account to verify the update
        acct = Cluster.DataGrid.CacheLoadConceptByExtId(acctId, true);
        assertNotNull(String.format("Account %s exists", acctId), acct);
        assertWithinRange("Account balance is updated", 20000 - 2000, acct.Balance, 0.001);
        assertWithinRange("Account debit is recorded", 2000, acct.Debits, 0.001);
        assertThat("Account status is set", acct.Status, equalTo("Normal"));
       
        Log.log(logger, "info", "Completed Test.RuleTests.testApplyDebit");
    }
}
````

This test implements a common pattern to verify rules in preprocessor context.
 - Test function uses no input parameter (i.e., `scope`), and returns nothing (i.e., `void`).
 - `cleanAccount()` deletes any harmful objects from the cache, so the same test can run multiple times with the same result.
 - It then creates a new account for the test.  The first call of `Engine.executeRules()` commits the new account to the cache.
 - It then loads the new account into working memory using `CacheLoadConceptByExtId()`.
 - It then creates and asserts a `Debit` event into working memory, and fires the `ApplyDebit` rule by the second call to `Engine.executeRules()`.
 - It then reload the updated account from cache by calling `CacheLoadConceptByExtId()` again.
 - 4 assert statements verify that the account is corrected updated by the `ApplyDebit` rule.
 - A runtime exception would be thrown if any assert statement fails; the exception would be caught and counted by the test service.

More advanced assertion are supported by the catalog functions in `beassert-1.0-jar`.  Refer to the unit tests in [DataTypeDemo](https://github.com/yxuco/DataTypeDemo) for more examples.

## Execute tests at BE engine startup

During development, the easiest way to launch unit tests is at BE engine startup.  However, tests for rules can only be launched after the engine is fully started. So, the test service implemented a scheduler to launch rule tests 3 seconds after the engine statup.

2 helper functions are used to launch unit tests at engine startup:
 - `allStartupTests`, which lists all tests to be executed during engine startup.  You can add test names to this function for tests that can be launched during startup, which includes all tests of functions in preprocessor context.
 - `allScheduledTests`, which lists all tests to be scheduled after the engine startup.  You can add test names to this functions for tests that cannot be launched during startup, which indluces tests for rules and functions in rule context.

Open `fdcache.cdd`, and add these to functions to **Startup Functions** of the **Inference-class** in the **Agent Classes** tab.

Rebuild `fdcache.ear`.

Highlight project root **FraudDetectionCache**, pull down menu **Run -> Run Configurations…**.  Set a configuration for `BusinessEvents Application` with the following parameter:
 - **CDD File:** full path to `fdcache.cdd` in the project workspace
 - **Processing Unit:** `default`
 - **Working Directory:** the `betest_tutorial` directory where you put the `fdcache.ear`
 - **EAR File:** full path to the `fdcache.ear`
 - **ClassPath:** Open the ClassPath tab, add `junit-4.12.jar` and `hamcrest-core-1.3.jar` to the **Third Party** library.  You can find these 2 jars from your local Maven repository, or from the downloaded `betest_tutorial/lib` folder.

This launches the BE engine in the BusinessEvents Studio.  Test completed messages should be printed in the Console log.

## Create and configure FraudDetectionTest

Although it is quick to launch unit tests at engine startup during development, it is not convenient to scan log files for failed tests, nor is it good to integrate with CI tools such as [Jenkins](https://jenkins-ci.org/).

This section will build a test driver based on JUnit, and so you will be able to visualize the test results in Eclipse, or Jenkins, or any other tools that are integrated with JUnit.

## Drive unit tests using FraudDetectionTest

## Configure and run pre-built tests

## The author

Yueming is a Sr. Architect working at [TIBCO](http://www.tibco.com/) Architecture Service Group.