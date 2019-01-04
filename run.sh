#!/bin/bash
##########################################################################
# Ecole polytechnique
# INF421 - Design and analysis of algorithms
# december 2018
#
# Optimal tree labeling problem
#
# run script
#
# Instructions:
# 1. Put this script file with the input files in a different directory
#    (ex: tests).
# 2. Go to the location of the principal directory
# 3. Run the script (ex: ../tests/run.sh).
##########################################################################

# Get the path of the directory containing this script and the input files
pushd `dirname $0`
repertoire_tests=`pwd`
# Cartes à tester
cartes="uqam stdamas sthilaire montreal"
popd

echo "UQAM | Département d'informatique"
echo "INF3105 | Structures de données et algorithmes"
echo "Évaluation du TP3"
echo
