#!/bin/bash

if [ -d "doc-book" ]; then
  git checkout dev-book
  rm src/doc-book -r
  git checkout dev doc-book
  mv doc-book src
  git add --all
  git commit -m "Update doc-book"
  git push
fi
