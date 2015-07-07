This tutorial is a step-by-step guide for unit test of [TIBCO BusinessEvents](https://docs.tibco.com/products/tibco-businessevents-5-2-0) (BE) applications.  It builds 2 projects from scratch to demonstrate steps for adding unit tests to a BusinessEvents sample project:
 - FraudDetectionCache, which is a sample from BusinessEvents 5.2.  This tutorial will add unit tests for the rules and rule functions to this project.
 - FraudDetectionTest, which is a Maven project for driving the unit tests using JUnit.

You may follow this tutorial to build the 2 projects from scratch, or you may skip to the last section to configure and execute the pre-built projects of this tutorial.

The following 3 projects contain source code and instructions for the test utilities used by this tutorial:
 - [DataTypeDemo](https://github.com/yxuco/DataTypeDemo), which contains more samples of unit tests and assertions for BusinessEvents applications.  The project library, `testservice.projlib`, is extracted from this project.
 - [BE Assert](https://github.com/yxuco/beassert), which contains source code of assertion functions for BE unit tests.  The catalog function jar `beassert-1.0.jar` is packaged from this project.
 - [BE Unit](https://github.com/yxuco/beunit), which contains utility functions to drive the unit tests using JUnit.  The test driver, `FraudDetectionTest` is based on this project.

## Introduction

As business agility has put more and more pressure on IT organization to deliver application updates continuously, automated unit testing has become a crucial requirement for all applications.  Rule-based systems like TIBCO BusinessEvents are no exception.  Even though it is challenging to test individual rules and functions in isolation, developers must be able to write unit tests to assess the correctness of individual rules and functions due to the following:
 - Unit test provides more confidence to developers when they update or add functionality to the application, and thus a full set of unit tests encourages code refactoring.
 - Unit test improves the code coverage, especially for rule-based systems, it is harder to improve code coverage by adding functional tests.
 - Unit test helps documenting the code, and improves the implementation by ensuring the testability of the code.

This tutorial shows that unit test of individual rules and runctions is not only necessary, but also achievable for TIBCO BusinessEvents applications. 

## Clone the tutorial utility

## Create and configure FraudDetectionCache

## Write unit test cases

## Execute tests at BE engine startup

## Create and configure FraudDetectionTest

## Drive unit tests using FraudDetectionTest

## Configure and run pre-built tests