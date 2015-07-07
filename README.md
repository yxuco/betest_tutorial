This tutorial is a step-by-step guide for unit test of TIBCO BusinessEvents applications.  It builds 2 projects from scratch to demonstrate steps of adding unit test cases to a BusinessEvents sample project:
 - FraudDetectionCache, which is a sample from BusinessEvents 5.2.  This tutorial will add unit test cases for the rules and rule functions to this project.
 - FraudDetectionTest, which is a Maven project to drive the unit test cases using JUnit.

You may follow this tutorial to build these 2 projects from scratch, or you may skip to the last section to configure and execute the resulting projects of this tutorial.

The following 3 projects contain source code and instructions for the test utilities used by this tutorial:
 - [DataTypeDemo](https://github.com/yxuco/DataTypeDemo), which contains more samples of unit tests and assertions for BusinessEvents applications.  The project library, `testservice.projlib`, is extracted from this project.
 - [BE Assert](https://github.com/yxuco/beassert), which contains source code of assertion functions for BE unit tests.  The catalog function jar `beassert-1.0.jar` is packaged from this project.
 - [BE Unit](https://github.com/yxuco/beunit), which contains utility functions to drive the unit tests using JUnit.  The test driver, `FraudDetectionTest` is based on this project.

## Introduction

## Clone the tutorial utility

## Create and configure FraudDetectionCache

## Write unit test cases

## Execute tests at BE engine startup

## Create and configure FraudDetectionTest

## Drive unit tests using FraudDetectionTest

## Configure and run prebuilt tests