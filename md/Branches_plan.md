# Terminology
- origin (a name for remote repository)
- commit point (I made up, means the pointer to the snapshot of the content you commited, of course, after staging) 

# Main Branches (remotely on github)
- origin/master
- origin/Deliverable-1
- origin/Deliverable-2
- ...

# Local Branches
- do whatever you want

# An example
```
$ git pull # sync
$ git checkout Deliverable-1 # in Deliverable-1 local branch, at commit point A
$ git branch test-branch # create a test1 branch locally, at commit point A
$ git checkout test-branch # at test-branch locally, at commit point A
$ # make some changes in test1
$ git add all; git commit -ma "Test Done" # A new local commit point created say B
$ git checkout Deliverable-1 # at Deliverable-1 local branch, at commit point A
$ git merge test-branch  # merge Deliverable-1 from test-branch
$ # solve conflicts
$ # merge sucessful
$ git 
```

