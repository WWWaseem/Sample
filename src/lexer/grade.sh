#! /usr/bin/env bash

thisdir=$(dirname $0)
codedir=$thisdir/../

echo $thisdir
echo "code dir $codedir"

jjfile=$codedir"src/Parse.jj"
jjtfile=$codedir"src/Parse.jjt"
parsejjfile=$codedir"build/parse/java/Parse.jj"
srcdir=$codedir"src/"
lexjavadir=$codedir"build/lex/java/"
lexclassdir=$codedir"build/lex/class/"
parsejavadir=$codedir"build/parse/java/"
parseclassdir=$codedir"build/parse/class/"
gradedir=$codedir"grade/"
javaccdir=$codedir"lib/javacc.jar"
testsbasicdir=$codedir"tests/basic/"
testsmixeddir=$codedir"tests/mixed/"
lexbasicdir=$codedir"build/lex/output/basic/"
lexmixeddir=$codedir"build/lex/output/mixed/"
parsebasicdir=$codedir"build/parse/output/basic/"
parsemixeddir=$codedir"build/parse/output/mixed/"
scorefile=$gradedir"score"

n_tests=$((0))
n_right=$((0))

prepare_dir() {
    mkdir -p $lexjavadir $lexclassdir $parsejavadir $parseclassdir $gradedir $lexbasicdir $lexmixeddir $parsebasicdir $parsemixeddir
}

compile_jj() {
    cmd="jjtree -OUTPUT_DIRECTORY=$parsejavadir $jjtfile"
    echo $cmd
    eval $cmd
}

compile_java() {
    cmd1="javacc -OUTPUT_DIRECTORY=$parsejavadir $parsejjfile"
    echo $cmd1
    eval $cmd1
}

compile_class() {
    cmd1="javac  -d $parseclassdir  $parsejavadir*.java "
    echo $cmd1
    eval $cmd1
}

compile_basic_output() {
    f=$1
    cmd1="java -cp $srcdir Parse $testsbasicdir$f.sample  > $lexbasicdir$f.out"
    cmd2="java -cp $parseclassdir Compiler $testsbasicdir$f.sample  > $parsebasicdir$f.out"  
    echo $cmd1
    eval $cmd1
    echo $cmd2
    eval $cmd2

}

compile_mixed_output() {
    f=$1
    cmd1="java -cp $srcdir Parse $testsmixeddir$f.txt  > $lexmixeddir$f.out"
    cmd2="java -cp $parseclassdir Compiler $testsmixeddir$f.txt  > $parsemixeddir$f.out"  
    echo $cmd1
    eval $cmd1
    echo $cmd2
    eval $cmd2
}

auto_test_all() {
    for f in `ls $testsbasicdir`
    do
	f1="${f%.*}"
	echo -e "\nNow processing basic:$f1"
	compile_basic_output $f1
    done
    for f in `ls $testsmixeddir`
    do
	f1="${f%.*}"
	echo -e "\nNow processing mixed:$f1"
	compile_mixed_output $f1
    done
}

auto_grade() {
    rm -rf build
    prepare_dir
    compile_jj
    compile_java
    compile_class
    auto_test_all	
}

auto_grade | tee $scorefile

