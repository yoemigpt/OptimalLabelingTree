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
texfile=""
if [ ! -z "$2" ]; then
    texfile="$2log.tex"
fi


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

underlinux=$?

# Set limits of time
ulimit -t 30
echo "-----"

# Detecting the CPU
if [ -e /proc/cpuinfo ] ; then
    cpuinfo=`grep "model name" /proc/cpuinfo | sort -u | cut -d ":" -f 2`
else
    cpuinfo="?"
fi

function Run {
  #statements
  logfile="log.md"
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

  echo "# Report : " > ${logfile}

  echo "Machine : " `hostname` "."
  echo "* Machine : " `hostname` "."  >> $logfile
  echo "CPU :$cpuinfo"
  echo "* CPU :$cpuinfo"  >> $logfile
  echo "Limit of `ulimit -t` seconds per test."
  echo "* Limit of `ulimit -t` seconds per test."  >> $logfile
  echo
  echo >> $logfile
  if [ ! -z "$texfile" ]; then
    echo "\\begin{itemize}" > ${texfile}
    echo "\\item Machine : " `hostname` "."  >> ${texfile}
    echo "\\item CPU : $cpuinfo"  >> ${texfile}
    echo "\\item Limit of `ulimit -t` seconds per test."  >> ${texfile}
    echo "\\end{itemize}" >> $texfile
    echo >> $texfile
    echo "\\begin{center}" >> $texfile
    echo "\\begin{tabular}{|c|c|c|}">> $texfile
    echo "\\hline" >> $texfile
    echo "input file & weight & CPU \\\\" >> $texfile
    echo "\\hline" >> $texfile
  fi

  echo -e "| input file |weight | CPU |" >> $logfile
  echo -e "|:-----:|:-----:|:-----:|" >> $logfile
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
    echo -e "| ${test%.in} | ${w} | ${t} |" >> $logfile
    echo -e "${test%.in}\t${w}\t${t}"
    if [ ! -z "$texfile" ]; then
      echo "${test%.in} & ${w} & ${t} \\\\ " >> $texfile
      echo "\\hline" >> $texfile
    fi
  done
  if [ ! -z "$texfile" ]; then
    echo "\\end{tabular}" >> $texfile
    echo "\\end{center}" >> $texfile
  fi

}

Run
