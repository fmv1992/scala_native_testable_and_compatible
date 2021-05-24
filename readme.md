[![Build Status](https://travis-ci.org/fmv1992/scala_native_testable_and_compatible.svg?branch=dev)](https://travis-ci.org/fmv1992/scala_native_testable_and_compatible)

# `scala_native_testable_and_compatible`

A [giter8](http://www.foundweekends.org/giter8/index.html) project that aims to provide the latest support for:

*   [`Scala Native`](https://scala-native.readthedocs.io/en/latest/).

*   [`ScalaTest`](https://www.scalatest.org/).

*   [`sbt-crossproject`](https://github.com/portable-scala/sbt-crossproject).

*   The latest version of Scala.

And that also uses:

*   [`scala-collection-compat`](https://github.com/scala/scala-collection-compat).

*   [`Scalafix`](https://github.com/scalacenter/scalafix).

*   [`Scalafmt`](https://scalameta.org/scalafmt/).

## Testing

```
cd ./scala_native_testable_and_compatible.g8/scala_native_testable_and_compatible
g8 "file://${PWD}/src/main/g8" --name=fmv
make format test
```

## Using

Due to not conforming to the [`src layout`](http://www.foundweekends.org/giter8/template.html#src+layout), I recommend the following:

```
tempfolder=$(mktemp -d)
git -C ${tempfolder} clone --branch dev --depth 1 https://github.com/fmv1992/scala_native_testable_and_compatible.g8
g8 "file://${tempfolder}/scala_native_testable_and_compatible.g8/scala_native_testable_and_compatible/src/main/g8"
rm -rf "${tempfolder}"
```

## Projects using this template

*   [one](https://github.com/fmv1992/one/).

## TODO

*   Automate `Scala` versioning. Currently we are using `2.13.3` and `2.13.4`.

*   Add support to docker:

    *   Give support to a readymade `dockerfile`.

    *   Improve existing `makefile` to add support to docker operations.

*   Add `nativelink` standard operation.
