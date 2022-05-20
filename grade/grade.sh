#! /usr/bin/env bash

thisdir=$(dirname $0)
codedir=$thisdir/../

echo $thisdir
echo "code dir $codedir"

jjfile=$codedir"src/Parse.jj"
jjtfile=$codedir"src/Parse.jjt"
parsejjfile=$codedir"build/parse/java/Parse.jj"

lexdir=$codedir"src/lexer/"
parsedir=$codedir"src/parser/"
resultdir=$codedir"src/result/"

lexjavadir=$codedir"build/lex/java/"
parsejavadir=$codedir"build/parse/java/"

gradedir=$codedir"grade/"

javaccdir=$codedir"lib/javacc.jar"

testsbasicdir=$codedir"tests/basic/"
testsmixeddir=$codedir"tests/mixed/"

lexbasicdir=$codedir"build/lex/output/basic/"
lexmixeddir=$codedir"build/lex/output/mixed/"

parsebasicdir=$codedir"build/parse/output/basic/"
parsemixeddir=$codedir"build/parse/output/mixed/"

resultbasicdir=$codedir"build/result/output/basic/"
resultmixddir=$codedir"build/result/output/mixed/"

scorefile=$gradedir"score"

n_tests=$((0))
n_right=$((0))

prepare_dir() {
    mkdir -p $lexjavadir $parsejavadir $gradedir $lexbasicdir $lexmixeddir $parsebasicdir $parsemixeddir $resultbasicdir $resultmixddir
}

compile_basic_output() {
    f=$1
    cmd1="java -cp $lexdir Parse $testsbasicdir$f.sample  > $lexbasicdir$f.out"
    cmd2="java -cp $parsedir Compiler $testsbasicdir$f.sample  > $parsebasicdir$f.out"
    cmd3="java -cp $resultdir Sample $testsbasicdir$f.sample  > $resultbasicdir$f.out"  
    echo $cmd1
    eval $cmd1
    echo $cmd2
    eval $cmd2
    #echo $cmd3
    #eval $cmd3

}

compile_mixed_output() {
    f=$1
    cmd1="java -cp $lexdir Parse $testsmixeddir$f.txt  > $lexmixeddir$f.out"
    cmd2="java -cp $parsedir Compiler $testsmixeddir$f.txt  > $parsemixeddir$f.out"  
    cmd3="java -cp $resultdir Sample $testsmixeddir$f.txt  > $resultmixddir$f.out"
    echo $cmd1
    eval $cmd1
    echo $cmd2
    eval $cmd2
    #echo $cmd3
    #eval $cmd3
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
    auto_test_all	
}

auto_grade | tee $scorefile

