#!/bin/sh
java -Xmx5G -cp "config:lib/*:recover-fx-${pom.version}.jar" fr.lsmbo.msda.spectra.comp.Main $@