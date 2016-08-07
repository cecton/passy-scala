#!/bin/sh
docker run --rm -it -v "$PWD":/app -w /app frolvlad/alpine-scala \
	bash -c "scalac passy.scala && jar xf /usr/share/scala/lib/scala-library.jar scala && jar -cfm passy.jar MANIFEST.MF Passy.class Passy\\\$.class scala"
