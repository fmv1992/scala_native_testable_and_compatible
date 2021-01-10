# Set variables.
SHELL := /bin/bash
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

export PROJECT_NAME ?= $(notdir $(ROOT_DIR))

# Find all scala files.
SBT_FILES := $(shell find $(PROJECT_NAME) -iname 'build.sbt' | parallel --pipe --max-args 1 -- parallel --shellquote --shellquote)
SCALA_FILES := $(shell find $(PROJECT_NAME) -iname '*.scala' | parallel --pipe --max-args 1 -- parallel --shellquote --shellquote)

export _JAVA_OPTIONS ?= -Xms2048m -Xmx4096m

FINAL_MAKEFILE := ./scala_native_testable_and_compatible/src/main/g8/$$if(verbatim)$$makefile$$endif$$

all: test format clean templates

format:
	@echo $(SBT_FILES) $(SCALA_FILES) | parallel --verbose -- eval scalafmt --config './scala_native_testable_and_compatible/src/main/g8/$$name__snake$$/.scalafmt.conf'

# Delete larger folders first.
clean:
	find . -iname 'target' -print0 | xargs -0 rm -rf
	find . -path '*/project/*' -type d -prune -print0 | xargs -0 rm -rf
	find . -iname '*.class' -print0 | xargs -0 rm -rf
	find . -iname '.bsp' -print0 | xargs -0 rm -rf
	find . -iname '.metals' -print0 | xargs -0 rm -rf
	find . -iname '.bloop' -print0 | xargs -0 rm -rf
	find . -iname '*.hnir' -print0 | xargs -0 rm -rf
	find . -iname '*NullaryOverride*' -print0 | xargs -0 rm -rf
	find . -type d -empty -delete

test:
	cd $$(mktemp -d) \
        && cp -rf $(ROOT_DIR)/scala_native_testable_and_compatible/src/main/g8 . \
        && { $(HOME)/.conscript/bin/g8 "file://$$(pwd)/g8" --name=$(PROJECT_NAME)_test || true ; } \
        && cd $(PROJECT_NAME)_test/$(PROJECT_NAME)_test \
        && find . -type f | sort -u \
        && sbt '+ test'

templates: $(FINAL_MAKEFILE)

$(FINAL_MAKEFILE): scala_native_testable_and_compatible/_makefile
	sed -E 's/\$$/\\$$/g' < '$<' > '$@'

# Docker actions. --- {{{

docker_build:
	docker build \
        --file ./dockerfile \
        --tag $(PROJECT_NAME) \
        --build-arg project_name=$(PROJECT_NAME) \
        -- . \
        1>&2

docker_run: docker_build
	docker run \
        --interactive \
        --rm \
        --tty \
        --entrypoint '' \
        $(PROJECT_NAME) \
        $(if $(DOCKER_CMD),$(DOCKER_CMD),bash)

docker_test: docker_build
	DOCKER_CMD='make test' make docker_run

# --- }}}

.FORCE:

# .EXPORT_ALL_VARIABLES:

# vim: set noexpandtab foldmethod=marker fileformat=unix filetype=make nowrap foldtext=foldtext():
