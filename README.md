# Tweet Analyze

## Project Details

The main goal of the _TweetRetrieval_ is calculate the Term Frequency (TF) and Inverse Document Frequency (IDF).

_n_ is the word count of the document.

_N_ is total word count of the document.

_m_ is total count of the word in the corpus.

_D_ is the count of documents in corpus.

_TF*IDF = n/N * log(D/m)_
 
### TASK 1

In Task-1, the word frequency in the documents is calculated. It takes inputs from documents under the input folder. 
It prints the output to the task-1 under the output folder. 

### TASK 2
 
In Task-2, the total word count of a document is calculated using Task-1 output.

The _WordCount_ class created so that data from the previous task can be stored. Thanks to the statically generated Java Map Object, it is transferred to the list during the map operation, then the obtaining result and the data in the list will be the next output.

### TASK 3
In Task-3, the total count of word in the corpus is calculated using Task-2 output. 
  
### TASK 4
In Task-4, the TF*IDF is calculated using Task-3 output. 

#### Useful links

You can check for hadoop with IntelliJ IDEA

[Using Hadoop with IntelliJ IDEA](https://intellij-support.jetbrains.com/hc/en-us/community/posts/206250189-Using-IntelliJ-to-develop-Hadoop-jobs)

[Windows Hadoop Installation](https://exitcondition.com/install-hadoop-windows/)
