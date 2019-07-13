#!/bin/bash
# This script is used for removing and inserting the str below on the first line

str='package com.example.perfectpitchaccuratepractice;'

pretend() {
  for file in $(ls *.java); do
    if head $file | grep -q "$str"; then
      echo skipping $file
    else
      echo will edit $file
      sed  "1 i$str" $file
    fi
  done
}

add() {
  for file in $(ls *.java); do
    if head $file | grep -q "$str"; then
      echo skipping $file
    else
      echo adding line to $file
      sed -i "1 i$str" $file
    fi
  done
}

delete() {
  for file in $(ls *.java); do
    if head $file | grep -q "$str"; then
      # delete first line 
      echo deleting first line of $file
      sed -i "1d" $file
    else
      echo skipping $file
    fi
  done
}

case $1 in 
  pre|p) pretend;;
  add|a) add;;
  del|d) delete;;
esac
