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
# 1. Go to the location of the principal directory
# 3. Run the script with argument the path of the tests directory (ex: ./run.sh tests).
##########################################################################

# Get the path of the directory containing this script and the input files

dir_tests=$1

echo "Ecole polytechnique"
echo "INF421 - Design and analysis of algorithms"
echo "Optimal tree labeling problem"
echo

if [ `pwd` -ef $dir_tests ];
then
    echo "This script must be in a directory different from the principal directory"
    echo "This script was stopped"
    exit -2;
fi

dir_bin=`pwd`

# Check if time exists under Linux
echo "Checking /usr/bin/time..."
/usr/bin/time -f %U echo 2>&1 > /dev/null
underlinux=$?

# Set limits of time
ulimit -t 30
echo "-----"

date=`date +%Y%m%d_%H%M%S`

# Detecting the CPU
if [ -e /proc/cpuinfo ] ; then
    cpuinfo=`grep "model name" /proc/cpuinfo | sort -u | cut -d ":" -f 2`
else
    cpuinfo="?"
fi

function Run {
  #statements
  logfile="log-${date}.txt"

  echo -e "\nCompilation ..."
  echo -e "\nCompilation ..." >> ${logfile}

  if [ ! -e Makefile ]; then
      echo "ERREUR : Makefile does not exist!"
      echo "ERREUR : Makefile does not exist!" >> ${logfile}
      return;
  fi

  make clean >> ${logfile}
  echo -ne
  make 2>&1 >> ${logfile}
  echo -ne

  echo "Evaluation of execution time on tests."
  echo "Results can be found in $logfile."

  echo "Repport : " > ${logfile}

  echo "Machine : " `hostname` "."
  echo "#Machine : " `hostname` "."  > $logfile
  echo "CPU :$cpuinfo"
  echo "#CPU :$cpuinfo"  >> $logfile
  echo "Start date : $date."
  echo "#Start date : $date."  >> $logfile
  echo "Limit of `ulimit -t` seconds per test."
  echo "#Limit of `ulimit -t` seconds per test."  >> $logfile
  echo
  echo "#" >> $logfile



  echo -e "#input file\tweight\tCPU" >> $logfile
  echo -e  "input file\tweight\tCPU"



  # List all in files.
  tests="`cd $dir_tests && ls *.in`"
  for test in $tests
  do
    if [ $underlinux -eq 0 ]; then
      t=`(/usr/bin/time -f "%U" make run < $dir_tests/$test > $dir_tests/${test%.in}.out) 2>&1 | tail -n 1`
    else
      t=`(time -p make run < $dir_tests/$test > $dir_tests/${test%.in}.out) 2>&1 | egrep user | cut -f 2 -d " "`
    fi
    w=`cat ${dir_tests}/${test%.in}.out`
    echo -e "${test%.in}\t${w}\t${t}"
    echo -e "${test%.in}\t${w}\t${t}" >> $logfile

  done

}

Run
