#! /bin/bash

if [ $# = 0 ] ; then
    echo "Usage: $0 <#objects> (<capacity>)?"
    exit 1
fi
nbobj=$1
if [ $# = 1 ] ; then
  capa_arg=`expr 20 \* $nbobj`
  echo "capacity = $capa_arg (default value: 20 times #objects)"

else if [ $# = 2 ] ; then
       capa_arg=$2
       echo "capacity = $2 (as requested)"

     else 
       echo "Usage: $0 <#objects> (<capacity>)?"
       exit 1
     fi
fi

# generate the sizes (but the last one)
vals="val pb$nbobj = Problem(Seq("
for n in `seq 2 $nbobj`; do 
  r=`date +%N|sed -e 's/0/1/g' -e 's/^...\(..\).*$/\1/'`; 
  vals="$vals$r, "; 
done

# Deal with the last one separately to not add an extra comma
r=`date +%N|sed 's/^...\(..\).*$/\1/'`
vals="$vals$r), $capa_arg)"

echo $vals