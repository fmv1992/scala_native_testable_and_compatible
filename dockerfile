# Inspired by `fmv1992_scala_utilities:b42a60f:dockerfile:1`.

FROM ubuntu:18.04@sha256:b58746c8a89938b8c9f5b77de3b8cf1fe78210c696ab03a1442e235eea65d84f

ARG project_name
ENV PROJECT_NAME $project_name

RUN apt-get update

# Install java.
RUN apt-get -y install openjdk-8-jdk
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

# Install support programs.
RUN apt-get install --yes git make wget zip
# Install Scala Native dependencies.
RUN apt-get install --yes clang libgc-dev
RUN rm -rf /var/lib/apt/lists/*

# Install sbt.
RUN mkdir -p /home/user/bin
WORKDIR /home/user/bin
RUN wget -O sbt.zip -- https://github.com/sbt/sbt/releases/download/v1.4.1/sbt-1.4.1.zip
RUN unzip sbt.zip
RUN rm sbt.zip
ENV PATH $PATH:/home/user/bin/sbt/bin

# Install coursier & scalafmt.
RUN wget --quiet --output-document /tmp/cs https://git.io/coursier-cli-"$(uname | tr LD ld)" && chmod +x /tmp/cs && mv /tmp/cs /bin/cs
RUN cs install scalafmt
WORKDIR /root/
ENV PATH "${PATH}:/root/.local/share/coursier/bin"
RUN command -V scalafmt

# Install giter8.
RUN cs install giter8

WORKDIR /home/user/
RUN mkdir ./${PROJECT_NAME}
COPY ./${PROJECT_NAME} ./${PROJECT_NAME}
RUN find ${PROJECT_NAME} -type d -print0 | xargs -0 rmdir --parents || true
RUN find ${PROJECT_NAME} | sort -u
RUN cd ./${PROJECT_NAME} && sbt update
RUN rm -rf ./${PROJECT_NAME}
RUN apt-get update && apt-get install --yes parallel
COPY . .
RUN make test

CMD bash
ENTRYPOINT bash

# vim: set filetype=dockerfile fileformat=unix nowrap:
