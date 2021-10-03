# Set variables.
SHELL := /bin/bash
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

export PROJECT_NAME ?= $(notdir $(ROOT_DIR))

# Find all scala files.
SBT_FILES := $(shell find $(PROJECT_NAME) -iname 'build.sbt')
SCALA_FILES := $(shell find $(PROJECT_NAME) -iname '*.scala')

export _JAVA_OPTIONS ?= -Xms2048m -Xmx4096m

all: test format clean

format:
	scalafmt --config ./$(PROJECT_NAME)/.scalafmt.conf $(SCALA_FILES) $(SBT_FILES)
	cd ./$(PROJECT_NAME) \
        && sed -E 's/ /;scalafixAll /g' <<<'"dependency:fix.scala213.ConstructorProcedureSyntax@com.sandinh:scala-rewrites:0.1.10-sd" "dependency:fix.scala213.ParensAroundLambda@com.sandinh:scala-rewrites:0.1.10-sd" "dependency:fix.scala213.NullaryOverride@com.sandinh:scala-rewrites:0.1.10-sd" "dependency:fix.scala213.MultiArgInfix@com.sandinh:scala-rewrites:0.1.10-sd" "dependency:fix.scala213.Any2StringAdd@com.sandinh:scala-rewrites:0.1.10-sd" "dependency:fix.scala213.ExplicitNonNullaryApply@com.sandinh:scala-rewrites:0.1.10-sd" "dependency:fix.scala213.ExplicitNullaryEtaExpansion@com.sandinh:scala-rewrites:0.1.10-sd"' \
            | sed -E 's/^/scalafixAll /g' \
            | xargs --verbose -I % -0 -- sbt '++2.13.3;project crossProjJVM;%'

clean:
	find . -iname 'target' -print0 | xargs -0 rm -rf
	find . -path '*/project/*' -type d -prune -print0 | xargs -0 rm -rf
	find . -iname '*.class' -print0 | xargs -0 rm -rf
	find . -iname '.bsp' -print0 | xargs -0 rm -rf
	find . -iname '.metals' -print0 | xargs -0 rm -rf
	find . -iname '.bloop' -print0 | xargs -0 rm -rf
	find . -iname '*.hnir' -print0 | xargs -0 rm -rf
	find . -type d -empty -delete

test:
	cd ./$(PROJECT_NAME) \
        && sbt '+ test'

.FORCE:

# vim: set noexpandtab foldmethod=marker fileformat=unix filetype=make nowrap foldtext=foldtext():
