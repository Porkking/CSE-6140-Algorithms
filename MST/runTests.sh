#!/bin/bash


graphFiles=`ls ./data/ | grep .gr`

for graph in ${graphFiles}
do
	filename=`echo ${graph} | cut -d'.' -f1`
	echo ${graph} ${filename}
	java RunExperiments.java ./data/${graph} ./data/${filename}.extra ./results/${filename}.out


done
