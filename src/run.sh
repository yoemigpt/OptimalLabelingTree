#!/bin/bash

### Shell script to run the OptimalTreeLabeling on multiple test files.

dir_tests="$1"

texfile=""
if [ ! -z "$2" ]; then
    texfile="$2log.tex"
fi


echo "Ecole polytechnique"
echo "INF421 - Design and analysis of algorithms"
echo "Optimal tree labeling"
echo

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

logfile="log.md"

echo "Evaluation of execution time on tests."
echo "Results can be found in $logfile."

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
    t=`(/usr/bin/time -f "%U" make --no-print-directory run FILE=$dir_tests/$test > $dir_tests/${test%.in}.out) 2>&1 | tail -n 1`
  else
    t=`(time -p make --no-print-directory run FILE=$dir_tests/$test > $dir_tests/${test%.in}.out) 2>&1 | egrep user | cut -f 2 -d " "`
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
