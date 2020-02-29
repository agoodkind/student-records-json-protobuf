#!/bin/bash
curdir=$(pwd)
classpath="$curdir/lib/protobuf-java-3.9.2.jar:$curdir/lib/gson-2.8.6.jar"
javarun="java -classpath $classpath:$curdir/out com.agoodkind.studentRecords.Main"
if [ $1 = '-c' ]; then
  eval javac -d "$curdir/out" -classpath "$classpath" "$curdir/src/com/agoodkind/studentRecords/*.java"
elif [ $1 = '-s' ]; then
  if [ $2 = '-p' ]; then
    ###Serialize Protobuf
    eval "$javarun -s -p $3"
  elif [ $2 = '-j' ]; then
    ##Serialize JSON
    eval "$javarun -s -j $3"
  fi
elif [ $1 = '-d' ]; then
  if [ $2 = '-j' ]; then
    ##Deserialize JSON
    eval "$javarun -d -j $3"
  elif [ $2 = '-p' ]; then
    ##Deserialize Protobuf
    eval "$javarun -d -p $3"
  fi
elif [ $1 = '-t' ]; then
  if [ $2 = '-j' ]; then
    ##Metric measurment JSON
    Size_input_text=$(ls -nl $3 | awk '{print $5}')
    declare result=($(eval "$javarun -t -j $3"))
    Serial_time=${result[0]}
    Deserial_time=${result[1]}
    Total_time=${result[2]}
    echo Total time is $Total_time ms
    Size_output_json=$(ls -nl result.json | awk '{print $5}')
    Ss=$(echo "scale=4;$Size_input_text/1000/$Serial_time" | bc -l)
    echo Speed of JSON Serialization $Ss kbps
    Sd=$(echo "scale=4;$Size_output_json/1000/$Deserial_time" | bc -l)
    echo Speed of JSON Deserialization $Sd kbps
  elif [ $2 = '-p' ]; then
    ##Metric measurment protobuf
    Size_input_text=$(ls -nl $3 | awk '{print $5}')
    declare result=($(eval "$javarun -t -p $3"))
    Serial_time=${result[0]}
    Deserial_time=${result[1]}
    Total_time=${result[2]}
    echo Total time is $Total_time ms
    Size_output_proto=$(ls -nl result_protobuf | awk '{print $5}')
    Ss=$(echo "scale=4;$Size_input_text/1000/$Serial_time" | bc -l)
    echo Speed of PROTOBUF Serialization $Ss kbps
    Sd=$(echo "scale=4;$Size_output_proto/1000/$Deserial_time" | bc -l)
    echo Speed of PROTOBUF Deserialization $Sd kbps
  fi
fi
