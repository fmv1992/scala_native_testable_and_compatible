[![Build Status](https://travis-ci.org/fmv1992/scala_native_testable_and_compatible.svg?branch=dev)](https://travis-ci.org/fmv1992/scala_native_testable_and_compatible)

# `scala_native_testable_and_compatible`

A [giter8](http://www.foundweekends.org/giter8/index.html) project that aims to provide the latest support for:

*   [`Scala Native`](https://scala-native.readthedocs.io/en/latest/).

*   [`ScalaTest`](https://www.scalatest.org/).

*   [`sbt-crossproject`](https://github.com/portable-scala/sbt-crossproject).

*   The latest version of Scala (`2.x`).

And that also uses:

*   [`scala-collection-compat`](https://github.com/scala/scala-collection-compat).

*   [`Scalafix`](https://github.com/scalacenter/scalafix).

*   [`Scalafmt`](https://scalameta.org/scalafmt/).

## Using

*   From a local clone:

    ```
    # `cd` to `scala_native_testable_and_compatible`.
    cd ./scala_native_testable_and_compatible
    rm -rf ./fmv
    g8 "file://${PWD}/src/main/g8" --name=fmv
    cd ./fmv
    make --file makefile format test
    ```

*   From the remote:

    ```
    cd $(mktemp -d)
    sbt new fmv1992/scala_native_testable_and_compatible.g8 \
        --branch dev \
        --directory scala_native_testable_and_compatible/src/main/g8 \
        --name=sntc_test
    # This still creates a `./project/` and `./target/` as garbage/by products.
    cd ./sntc_test
    make --file makefile format test
    ```

## Projects using this template

*   [one](https://github.com/fmv1992/one/).

## TODO

*   Test that `make format test clean` has the exact same files when directory is cloned.

*   Automate `Scala` versioning. Currently we are using `2.13.3` and `2.13.4`.

*   Add support to docker:

    *   Give support to a readymade `dockerfile`.

    *   Improve existing `makefile` to add support to docker operations.

*   Add `nativelink` standard operation.
